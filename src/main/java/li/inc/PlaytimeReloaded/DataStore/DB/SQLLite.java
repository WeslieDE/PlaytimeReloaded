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

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SQLLite implements IDB
{
    private Plugin m_plugin;
    private Config m_config;

    Connection m_connection = null;

    public void init(Plugin _plugin, Config _config)
    {
        m_plugin = _plugin;
        m_config = _config;

        try
        {
            m_connection = DriverManager.getConnection("jdbc:sqlite:" + m_plugin.getDataFolder().getAbsolutePath() + "/playtime.db");

            Statement _statement = m_connection.createStatement();

            _statement.executeUpdate("CREATE TABLE IF NOT EXISTS playtime(playeruuid varchar(36), playtime int(6))");
            _statement.close();

        }catch (SQLException _ex) {
        _ex.printStackTrace();
        }
    }

    public void update(UUID _playerUUID, int _newTime)
    {
        try
        {
            if(m_connection != null)
            {
                Statement _statement = m_connection.createStatement();
                _statement.executeUpdate("DELETE FROM playtime WHERE playeruuid = '" + _playerUUID.toString() + "'");
                _statement.executeUpdate("INSERT INTO playtime (`playeruuid`, `playtime`) VALUES ('" + _playerUUID.toString() + "', '" + _newTime + "')");

                _statement.close();
            }
        }catch (SQLException _ex) {
            _ex.printStackTrace();
        }
    }

    public List<String[]> getTopPlayers(int _count)
    {
        List<String[]> _returnList = new ArrayList<String[]>();

        try
        {
            if(m_connection != null)
            {
                Statement _statement = m_connection.createStatement();
                ResultSet _results = _statement.executeQuery("SELECT * FROM playtime ORDER BY playtime DESC LIMIT " + _count);

                while (_results.next())
                {
                    try
                    {
                        _returnList.add(new String[]{UUIDCache.get(UUID.fromString(_results.getString("playeruuid"))), _results.getString("playtime")});
                    }catch(Exception _e)
                    {
                        return new ArrayList<String[]>();
                    }
                }

                _results.close();
                _statement.close();
            }
        }catch (SQLException _ex) {
            _ex.printStackTrace();
        }

        return _returnList;
    }

    public int getPlayerTime(UUID _playerUUID)
    {
        try
        {
            if(m_connection != null)
            {
                Statement _statement = m_connection.createStatement();
                ResultSet _results = _statement.executeQuery("SELECT * FROM playtime WHERE `playeruuid` = '" + _playerUUID.toString() + "'");

                while (_results.next())
                {
                    try
                    {
                        return _results.getInt("playtime");
                    }catch(Exception _e)
                    {
                        return 0;
                    }
                }

                _results.close();
                _statement.close();
            }
        }catch (SQLException _ex) {
            _ex.printStackTrace();
        }

        return 0;
    }
}
