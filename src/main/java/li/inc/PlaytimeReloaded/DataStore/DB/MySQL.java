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

package li.inc.PlaytimeReloaded.DataStore.DB;

import li.inc.PlaytimeReloaded.DataStore.Config;
import li.inc.PlaytimeReloaded.DataStore.UUIDCache;
import org.bukkit.plugin.Plugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MySQL implements IDB
{
    private Plugin m_plugin;
    private Config m_config;

    private MySQLTools m_MySqlTools;

    public void init(Plugin _plugin, Config _config)
    {
        m_plugin = _plugin;
        m_config = _config;

        try
        {
            m_MySqlTools = new MySQLTools(m_config.getMysqlHost(), m_config.getMysqlPort(), m_config.getMysqlDB(), m_config.getMysqlUsername(), m_config.getMysqlPassword());
            m_MySqlTools.saveMySQLUpdate("CREATE TABLE IF NOT EXISTS playtime(playeruuid varchar(36), playtime int(6), PRIMARY KEY (playeruuid))", new String[]{});
        }catch(Exception _e)
        {
            _plugin.getLogger().info("!!!!!!!!!!!! ERROR: CANT CONNECT TO MySQL Server !!!!!!!!!!!!");
        }
    }

    public void update(UUID _playerUUID, int _newTime)
    {
        m_MySqlTools.saveMySQLUpdate("REPLACE INTO playtime (`playeruuid`, `playtime`) VALUES ('" + _playerUUID.toString() + "', '" + _newTime + "')", new String[]{});
    }

    public List<String[]> getTopPlayers(int _count)
    {
        ResultSet _rs = m_MySqlTools.saveMySQLQuarry("SELECT * FROM playtime ORDER BY playtime DESC LIMIT " + _count, new String[]{});
        List<String[]> _returnList = new ArrayList<String[]>();

        if(_rs != null)
        {
            try
            {
                while(_rs.next())
                {
                    try
                    {
                        _returnList.add(new String[]{UUIDCache.get(UUID.fromString(_rs.getString("playeruuid"))), _rs.getString("playtime")});
                    }catch(Exception _e)
                    {
                        return new ArrayList<String[]>();
                    }
                }
            } catch (SQLException e) {
                return new ArrayList<String[]>();
            }
        }

        return _returnList;
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
