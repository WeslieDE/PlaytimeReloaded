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

package li.inc.PlaytimeReloaded.DataStore;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class Config {
    //DBType
    private String m_DBType = "SQLLite";
    //MySQL
    private String m_MySqlHost = "127.0.0.1";
    private int m_MySqlPort = 3306;
    private String m_MySqlDB = "";
    private String m_mySqlUserName = "USER";
    private String m_mySqlPassword = "PASSWORD";
    //Texte
    private String m_textPrefix = "&6[&aPlayTime&6] &a";
    private String m_textYourCurrentPlaytime = "Your current playtime is &6%t";
    private String m_textPlayerPlaytimeIs = "The playtime from player &6%s&a is &6%t";
    private String m_textPlayerNotFound = "&cThis player dont have play on this server.";
    private String m_textTopPlayerListHead = "This is the Top %r player list.";
    private String m_textPlayerEntry = "%r : %s - %t";
    private String m_textNoPermission = "&cYou dont have permission to do this!";
    private String m_textDateDays = "&aDays&6";
    private String m_textDateHours = "&aHours&6";
    private String m_textDateMinutes = "&aMinutes&a";
    private List<TimeCommand> m_timeCommands = new ArrayList<TimeCommand>();
    private Plugin m_plugin;

    public Config(Plugin _plugin) {
        m_plugin = _plugin;
        setDefaultConfigValues(m_plugin.getConfig());
        getConfigValues(m_plugin.getConfig());
        saveConfigValues(m_plugin.getConfig());
    }

    public String getDBType() {
        return m_DBType;
    }

    public String getMysqlHost() {
        return m_MySqlHost;
    }

    public int getMysqlPort() {
        return m_MySqlPort;
    }

    public String getMysqlDB() {
        return m_MySqlDB;
    }

    public String getMysqlUsername() {
        return m_mySqlUserName;
    }

    public String getMysqlPassword() {
        return m_mySqlPassword;
    }

    public String getTextPrefix() {
        return m_textPrefix;
    }

    public String getTextYourCurrentPlaytime() {
        return m_textYourCurrentPlaytime;
    }

    public String getTextPlayerPlaytimeIs() {
        return m_textPlayerPlaytimeIs;
    }

    public String getTextPlayerNotFound() {
        return m_textPlayerNotFound;
    }

    public String getTextTopPlayerListHead() {
        return m_textTopPlayerListHead;
    }

    public String getTextPlayerEntry() {
        return m_textPlayerEntry;
    }

    public String getTextNoPermission() {
        return m_textNoPermission;
    }

    public String getTextDateDays() {
        return m_textDateDays;
    }

    public String getTextDateHours() {
        return m_textDateHours;
    }

    public String getTextDateMinutes() {
        return m_textDateMinutes;
    }

    public List<TimeCommand> getTimeCommandList() {
        return m_timeCommands;
    }

    private void setDefaultConfigValues(FileConfiguration _config) {
        _config.addDefault("DB.Type", m_DBType);

        _config.addDefault("mysql.host", m_MySqlHost);
        _config.addDefault("mysql.port", m_MySqlPort);
        _config.addDefault("mysql.db", m_MySqlDB);
        _config.addDefault("mysql.user", m_mySqlUserName);
        _config.addDefault("mysql.pass", m_mySqlPassword);

        _config.addDefault("text.Prefix", m_textPrefix);

        _config.addDefault("text.YourCurrentPlaytime", m_textYourCurrentPlaytime);
        _config.addDefault("text.PlayerPlaytimeIs", m_textPlayerPlaytimeIs);
        _config.addDefault("text.PlayerNotFound", m_textPlayerNotFound);
        _config.addDefault("text.NoPermission", m_textNoPermission);

        _config.addDefault("text.Date.Days", m_textDateDays);
        _config.addDefault("text.Date.Hours", m_textDateHours);
        _config.addDefault("text.Date.Minutes", m_textDateMinutes);

        _config.addDefault("text.TopPlayerTitel", m_textTopPlayerListHead);
        _config.addDefault("text.TopPlayerEntry", m_textPlayerEntry);

        _config.addDefault("timeCommand", m_timeCommands);
    }

    private void getConfigValues(FileConfiguration _config) {
        m_DBType = _config.getString("DB.Type");

        m_MySqlHost = _config.getString("mysql.host");
        m_MySqlPort = _config.getInt("mysql.port");
        m_MySqlDB = _config.getString("mysql.db");
        m_mySqlUserName = _config.getString("mysql.user");
        m_mySqlPassword = _config.getString("mysql.pass");

        m_textPrefix = _config.getString("text.Prefix");

        m_textYourCurrentPlaytime = _config.getString("text.YourCurrentPlaytime");
        m_textPlayerPlaytimeIs = _config.getString("text.PlayerPlaytimeIs");
        m_textPlayerNotFound = _config.getString("text.PlayerNotFound");
        m_textNoPermission = _config.getString("text.NoPermission");

        m_textDateDays = _config.getString("text.Date.Days");
        m_textDateHours = _config.getString("text.Date.Hours");
        m_textDateMinutes = _config.getString("text.Date.Minutes");

        m_textTopPlayerListHead = _config.getString("text.TopPlayerTitel");
        m_textPlayerEntry = _config.getString("text.TopPlayerEntry");

        for (String _l : _config.getStringList("timeCommand")) {
            String[] _ld = _l.split(";");

            if (_ld.length == 2) {
                m_timeCommands.add(new TimeCommand(Integer.parseInt(_ld[0]), _ld[1]));
            }
        }
    }

    private void saveConfigValues(FileConfiguration _config) {
        _config.set("DB.Type", m_DBType);

        _config.set("mysql.host", m_MySqlHost);
        _config.set("mysql.port", m_MySqlPort);
        _config.set("mysql.db", m_MySqlDB);
        _config.set("mysql.user", m_mySqlUserName);
        _config.set("mysql.pass", m_mySqlPassword);

        _config.set("text.Prefix", m_textPrefix);

        _config.set("text.YourCurrentPlaytime", m_textYourCurrentPlaytime);
        _config.set("text.PlayerPlaytimeIs", m_textPlayerPlaytimeIs);
        _config.set("text.PlayerNotFound", m_textPlayerNotFound);
        _config.set("text.NoPermission", m_textNoPermission);

        _config.set("text.Date.Days", m_textDateDays);
        _config.set("text.Date.Hours", m_textDateHours);
        _config.set("text.Date.Minutes", m_textDateMinutes);

        _config.set("text.TopPlayerTitel", m_textTopPlayerListHead);
        _config.set("text.TopPlayerEntry", m_textPlayerEntry);

        List<String> _timeCommands = new ArrayList<String>();
        for (TimeCommand _tc : m_timeCommands) {
            _timeCommands.add(_tc.getTime() + ";" + _tc.getCommand());
        }

        _config.set("timeCommand", _timeCommands);

        m_plugin.saveConfig();
    }

    public void reload() {
        m_plugin.reloadConfig();
        getConfigValues(m_plugin.getConfig());
    }
}
