package li.inc.PlaytimeReloaded;

import li.inc.PlaytimeReloaded.DataStore.Config;
import li.inc.PlaytimeReloaded.DataStore.DB.MySQL;
import li.inc.PlaytimeReloaded.DataStore.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Christopher on 12.09.2016.
 */
public class Playtime extends JavaPlugin implements Listener
{
    private Config m_config;
    private MySQL m_mysql;

    @Override
    public void onEnable()
    {
        //Load config from file into Config class.
        m_config = new Config(this);

        //Load the text from the lang config.
        m_mysql = new MySQL(this, m_config);


        getServer().getPluginManager().registerEvents(this, this);

        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                refreshPlaytime();
            }}, 0, 1200);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("playtime"))
        {
            if(args.length == 0)
            {
                //Player will seine eigene Playtime wissen.
                if (sender instanceof Player)
                {
                    Player _player = (Player)sender;

                    if(_player.hasPermission("playtime.use"))
                    {
                        _player.sendMessage(getChatMessage(m_config.getTextYourCurrentPlaytime(), _player.getName(), m_mysql.getPlayerTime(_player.getUniqueId()), 0));
                    }else{
                        _player.sendMessage(getChatMessage(m_config.getTextNoPermission(), _player.getName(), 0, 0));
                    }
                }else{
                    this.getLogger().info("Ey you bread! You cant display the playtime from the console!");
                }
            }else{
                //Player will andere Spielzeit wissen.
                UUID _searchPlayer = getPlayerUUID(args[0]);

                if(_searchPlayer != null)
                {
                    //Spieler wurde gefunden.
                    if (sender instanceof Player)
                    {
                        Player _player = (Player)sender;
                        if(_player.hasPermission("playtime.use.others"))
                        {
                            _player.sendMessage(getChatMessage(m_config.getTextYourCurrentPlaytime(), args[0], m_mysql.getPlayerTime(_searchPlayer), 0));
                        }else{
                            _player.sendMessage(getChatMessage(m_config.getTextNoPermission(), args[0], 0, 0));
                        }
                    }else{
                        this.getLogger().info(getChatMessage(m_config.getTextPlayerPlaytimeIs(), args[0], m_mysql.getPlayerTime(_searchPlayer), 0));
                    }
                }else{
                    //Spieler wurde nicht gefunden / Hat noch nicht auf dem Server gespielt.
                    if (sender instanceof Player)
                    {
                        Player _player = (Player)sender;
                        _player.sendMessage(getChatMessage(m_config.getTextPlayerNotFound(), "", 0, 0));
                    }else{
                        this.getLogger().info(getChatMessage(m_config.getTextPlayerNotFound(), "", 0, 0));
                    }
                }
            }
        }

        return true;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        if(event.getPlayer().hasPermission("playtime.use.others"))
        {
            event.getPlayer().sendMessage(getChatMessage(m_config.getTextYourCurrentPlaytime(), event.getPlayer().getName(), m_mysql.getPlayerTime(event.getPlayer().getUniqueId()), 0));
        }

    }

    private void refreshPlaytime()
    {
        for(Player _dieserSpieler : getServer().getOnlinePlayers())
        {
            int _spielerPlaytime = m_mysql.getPlayerTime(_dieserSpieler.getUniqueId());

            _spielerPlaytime = _spielerPlaytime + 1;

            m_mysql.update(_dieserSpieler.getUniqueId(), _spielerPlaytime);
        }
    }

    private UUID getPlayerUUID(String _playerName)
    {
        for(Player _dieserSpieler : getServer().getOnlinePlayers())
        {
            if(_dieserSpieler.getName() == _playerName)
            {
                return _dieserSpieler.getUniqueId();
            }
        }

        try
        {
            UUIDFetcher _uuidFetcher = new UUIDFetcher(Arrays.asList(_playerName));
            Map<String, UUID> _fetcherResponse = _uuidFetcher.call();
            UUID _playerUUID = _fetcherResponse.get(_playerName);

            OfflinePlayer _offlinePlayer = Bukkit.getOfflinePlayer(_playerUUID);

            if(_offlinePlayer != null)
            {
                if(!_offlinePlayer.hasPlayedBefore())
                    return null;

                return _offlinePlayer.getUniqueId();
            }else{
                return null;
            }
        }catch(Exception _e)
        {
            return null;
        }
    }

    private long[] calculateTime(long seconds)
    {
        long sec = seconds % 60;
        long minutes = seconds % 3600 / 60;
        long hours = seconds % 86400 / 3600;
        long days = seconds / 86400;

        System.out.println("Day " + days + " Hour " + hours + " Minute " + minutes + " Seconds " + sec);

        return new long[]{days, hours, minutes, sec};
    }

    private String getTimeString(int _timeInMin)
    {
        String _ausgabe = "";

        long seconds = _timeInMin * 60;

        if(seconds == 0)
        {
            long[] _time = calculateTime(seconds);

            if(_time[0] > 0)
                _ausgabe += _time[0] + " " + m_config.getTextDateDays() + " ";

            if(_time[1] > 0)
                _ausgabe += _time[1] + " " + m_config.getTextDateHours() + " ";

            if(_time[2] > 0)
                _ausgabe += _time[2] + " " + m_config.getTextDateMinutes();
        }else{
            _ausgabe += "0 " + m_config.getTextDateMinutes();
        }

        return _ausgabe;
    }

    private String getChatMessage(String _plainText, String _player, int _time, int _rang)
    {
        String returnText = m_config.getTextPrefix() + _plainText;

        returnText = returnText.replace("%s", _player);

        returnText = returnText.replace("%t", "" + getTimeString(_time));
        returnText = returnText.replace("%r", "" + _rang);

        returnText = ChatColor.translateAlternateColorCodes('&', returnText);
        return returnText;
    }
}
