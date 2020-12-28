/*
* Copyright (c) 2016
* See CONTRIBUTORS.TXT for a full list of copyright holders.
*
* This file is part of the spigot Minecraft server plugin 'PlaytimeReloaded'.
*
* PlaytimeReloaded is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* PlaytimeReloaded is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with PlaytimeReloaded.  If not, see <http://www.gnu.org/licenses/>.
*/

package li.inc.PlaytimeReloaded;

import li.inc.PlaytimeReloaded.DataStore.Config;
import li.inc.PlaytimeReloaded.DataStore.DB.DB;
import li.inc.PlaytimeReloaded.DataStore.DB.MySQL;
import li.inc.PlaytimeReloaded.DataStore.TimeCommand;
import li.inc.PlaytimeReloaded.DataStore.UUIDCache;
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
import java.util.*;


public class Playtime extends JavaPlugin implements Listener
{
    private Config m_config = null;
    private DB m_db = null;

    @Override
    public void onEnable()
    {
        //Load config from file into Config class.
        m_config = new Config(this);

        //Load the text from the lang config.
        if(m_db == null)
            m_db = new DB(this, m_config);

        getServer().getPluginManager().registerEvents(this, this);

        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                refreshPlaytime();
            }}, 0, 1200);
    }

    @Override
    public void onDisable()
    {
        //Load the text from the lang config.
        if(m_db != null)
        {
            m_db.close();
            m_db = null;
        }
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if (cmd.getName().equalsIgnoreCase("toptime"))
        {
            int _count = 5;

            if(args.length == 1)
            {
                if(isInteger(args[0]))
                {
                    _count = Integer.parseInt(args[0]);
                }
            }

            if (sender instanceof Player)
            {
                Player _player = (Player)sender;

                if(_player.hasPermission("playtime.top"))
                {
                    _player.sendMessage(getChatMessage(m_config.getTextTopPlayerListHead(), _player.getName(), 0, _count, true));

                    List<String[]> _topPlayers = m_db.getTopPlayers(_count);
                    int _rang = 1;
                    for (String[] _playerData: _topPlayers)
                    {
                        if(_playerData.length >= 2)
                        {
                            _player.sendMessage(getChatMessage(m_config.getTextPlayerEntry(), _playerData[0], Integer.parseInt(_playerData[1]), _rang++, true));
                        }
                    }
                }else{
                    _player.sendMessage(getChatMessage(m_config.getTextNoPermission(), _player.getName(), 0, 0, true));
                }
            }else{
                this.getLogger().info(getChatMessage(m_config.getTextTopPlayerListHead(), "", 0, _count, false));

                List<String[]> _topPlayers = m_db.getTopPlayers(_count);
                int _rang = 1;
                for (String[] _playerData: _topPlayers)
                {
                    if(_playerData.length >= 2)
                    {
                        this.getLogger().info(getChatMessage(m_config.getTextPlayerEntry(), _playerData[0], Integer.parseInt(_playerData[1]), _rang++, false));
                    }
                }
            }
        }

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
                        _player.sendMessage(getChatMessage(m_config.getTextYourCurrentPlaytime(), _player.getName(), m_db.getPlayerTime(_player.getUniqueId()), 0, true));
                    }else{
                        _player.sendMessage(getChatMessage(m_config.getTextNoPermission(), _player.getName(), 0, 0, true));
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
                            _player.sendMessage(getChatMessage(m_config.getTextPlayerPlaytimeIs(), args[0], m_db.getPlayerTime(_searchPlayer), 0, true));
                        }else{
                            _player.sendMessage(getChatMessage(m_config.getTextNoPermission(), args[0], 0, 0, true));
                        }
                    }else{
                        this.getLogger().info(getChatMessage(m_config.getTextPlayerPlaytimeIs(), args[0], m_db.getPlayerTime(_searchPlayer), 0, false));
                    }
                }else{
                    //Spieler wurde nicht gefunden / Hat noch nicht auf dem Server gespielt.
                    if (sender instanceof Player)
                    {
                        Player _player = (Player)sender;
                        _player.sendMessage(getChatMessage(m_config.getTextPlayerNotFound(), "", 0, 0, true));
                    }else{
                        this.getLogger().info(getChatMessage(m_config.getTextPlayerNotFound(), "", 0, 0, false));
                    }
                }
            }
        }

        return true;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        if(event.getPlayer().hasPermission("playtime.use") && event.getPlayer().hasPermission("playtime.login"))
        {
            event.getPlayer().sendMessage(getChatMessage(m_config.getTextYourCurrentPlaytime(), event.getPlayer().getName(), m_db.getPlayerTime(event.getPlayer().getUniqueId()), 0, true));
        }

        UUIDCache.update(event.getPlayer().getName(), event.getPlayer().getUniqueId());
        addPlayTime(event.getPlayer().getUniqueId(), 0);
    }

    private void refreshPlaytime()
    {
        for(Player _dieserSpieler : getServer().getOnlinePlayers())
        {
            addPlayTime(_dieserSpieler.getUniqueId(), 1);
        }
    }

    private void addPlayTime(UUID _playerUUID, int _time)
    {
        int _spielerPlaytime = m_db.getPlayerTime(_playerUUID);

        _spielerPlaytime = _spielerPlaytime + _time;

        m_db.update(_playerUUID, _spielerPlaytime);

        checkPlaytimeCommand(_playerUUID, _spielerPlaytime);
    }

    private UUID getPlayerUUID(String _playerName)
    {
        Player _returnPlayer = Bukkit.getPlayerExact(_playerName);
        if(_returnPlayer != null)
            return _returnPlayer.getUniqueId();

        for(Player _dieserSpieler : getServer().getOnlinePlayers())
        {
            if(_dieserSpieler.getName() == _playerName)
            {
                return _dieserSpieler.getUniqueId();
            }
        }

        UUID _playerUUID = UUIDCache.get(_playerName);

        if(_playerUUID == null)
            return null;

        OfflinePlayer _offlinePlayer = Bukkit.getOfflinePlayer(_playerUUID);

        if(_offlinePlayer != null)
        {
            if(!_offlinePlayer.hasPlayedBefore())
                return null;

            return _offlinePlayer.getUniqueId();
        }else{
                return null;
        }
    }

    private long[] calculateTime(long seconds)
    {
        long sec = seconds % 60;
        long minutes = seconds % 3600 / 60;
        long hours = seconds % 86400 / 3600;
        long days = seconds / 86400;

        return new long[]{days, hours, minutes, sec};
    }

    private String getTimeString(int _timeInMin)
    {
        String _ausgabe = "";

        long seconds = _timeInMin * 60;

        if(seconds != 0)
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

    private String getChatMessage(String _plainText, String _player, int _time, int _rang, boolean _color)
    {
        String returnText = m_config.getTextPrefix() + _plainText;

        returnText = returnText.replace("%s", _player);

        returnText = returnText.replace("%t", "" + getTimeString(_time));
        returnText = returnText.replace("%r", "" + _rang);

        returnText = ChatColor.translateAlternateColorCodes('&', returnText);

        if(_color == false)
            returnText = ChatColor.stripColor(returnText);

        return returnText;
    }

    private void checkPlaytimeCommand(UUID _player, int _time)
    {
        List<TimeCommand> _timeCommands = m_config.getTimeCommandList();

        for (TimeCommand _tc: _timeCommands)
        {
            if(_tc.getTime() == _time)
            {
                String _commandWithParas = _tc.getCommand();

                _commandWithParas = _commandWithParas.replace("%%player%%", UUIDCache.get(_player));
                _commandWithParas = _commandWithParas.replace("%%time%%", "" + _time);

                _commandWithParas = ChatColor.translateAlternateColorCodes('&', _commandWithParas);

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), _commandWithParas);
            }
        }
    }
}
