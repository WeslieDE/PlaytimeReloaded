package li.inc.PlaytimeReloaded.DataStore.DB;

import li.inc.PlaytimeReloaded.DataStore.Config;
import org.bukkit.plugin.Plugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MySQL
{
    private Plugin m_plugin;
    private Config m_config;

    private MySQLTools m_MySqlTools;

    public MySQL(Plugin _plugin, Config _config)
    {
        m_plugin = _plugin;
        m_config = _config;

        try
        {
            m_MySqlTools = new MySQLTools(m_config.getMysqlHost(), m_config.getMysqlPort(), m_config.getMysqlDB(), m_config.getMysqlUsername(), m_config.getMysqlPassword());
            m_MySqlTools.saveMySQLUpdate("CREATE TABLE IF NOT EXISTS Playtime(playeruuid varchar(36), playtime int(6), PRIMARY KEY (playeruuid))", new String[]{});
        }catch(Exception _e)
        {
            _plugin.getLogger().info("!!!!!!!!!!!! ERROR: CANT CONNECT TO MySQL Server !!!!!!!!!!!!");
        }
    }

    public void update(UUID _playerUUID, int _newTime)
    {
        m_MySqlTools.saveMySQLUpdate("REPLACE INTO `Playtime` (`playeruuid`, `playtime`) VALUES ('" + _playerUUID.toString() + "', '" + _newTime + "')", new String[]{});


    }

    public int getPlayerTime(UUID _playerUUID)
    {
        ResultSet _rs = m_MySqlTools.saveMySQLQuarry("SELECT * FROM playtime WHERE `playeruuid` = '" + _playerUUID.toString() + "';", new String[]{});

        if(_rs != null)
        {
            try
            {
                while(_rs.next())
                {
                    try
                    {
                        return _rs.getInt("playtime");
                    }catch(Exception _e)
                    {
                        return 0;
                    }
                }
            } catch (SQLException e) {
                return 0;
            }
        }else{
            return 0;
        }

        return 0;
    }
}
