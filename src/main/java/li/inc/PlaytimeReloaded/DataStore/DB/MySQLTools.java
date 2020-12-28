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

import java.sql.*;


public class MySQLTools {
    Connection m_MySQLConnection;

    String m_host;
    int m_port;
    String m_dbname;
    String m_username;
    String m_passwort;

    //Public Funktionen

    public MySQLTools(String _host, int _port, String _dbname, String _username, String _passwort) {
        m_host = _host;
        m_port = _port;
        m_dbname = _dbname;
        m_username = _username;
        m_passwort = _passwort;

        m_MySQLConnection = createSQLConnection(m_host, m_port, m_dbname, m_username, m_passwort);
    }

    public Connection getConnection() {
        return m_MySQLConnection;
    }


    //Private Funktionen

    private Connection createSQLConnection(String _host, int _port, String _dbname, String _username, String _passwort) {
        Connection _MySQLConnection;
        try {
            _MySQLConnection = DriverManager.getConnection("jdbc:mysql://" + _host + ":" + _port + "/" + _dbname, _username, _passwort);
            _MySQLConnection.setAutoCommit(true);

            return _MySQLConnection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void pingConnection() {
        try {
            if (m_MySQLConnection.isClosed() || !m_MySQLConnection.isValid(3)) {
                m_MySQLConnection.close();
                m_MySQLConnection = createSQLConnection(m_host, m_port, m_dbname, m_username, m_passwort);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveMySQLUpdate(String _quarry, String[] _values) {
        pingConnection();

        try {
            PreparedStatement _ps = m_MySQLConnection.prepareStatement(_quarry);

            int index = 0;
            for (String _s : _values) {
                String thisValue = (String) _s;
                _ps.setString(++index, thisValue);
            }

            _ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet saveMySQLQuarry(String _quarry, String[] _values) {
        pingConnection();

        try {
            PreparedStatement _ps = m_MySQLConnection.prepareStatement(_quarry);

            int index = 0;
            for (String _s : _values) {
                String thisValue = (String) _s;
                _ps.setString(++index, thisValue);
            }

            return _ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
