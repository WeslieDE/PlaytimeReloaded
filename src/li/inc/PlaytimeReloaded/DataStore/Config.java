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

public class Config 
{
	//MySQL
	private String m_MySqlHost = "127.0.0.1";
	public String getMysqlHost()
	{
		return m_MySqlHost;
	}

	private int m_MySqlPort = 3306;
	public int getMysqlPort()
	{
		return m_MySqlPort;
	}

	private String m_MySqlDB = "";
	public String getMysqlDB()
	{
		return m_MySqlDB;
	}

	private String m_mySqlUserName = "USER";
	public String getMysqlUsername()
	{
		return m_mySqlUserName;
	}

	private String m_mySqlPassword = "PASSWORD";
	public String getMysqlPassword()
	{
		return m_mySqlPassword;
	}

	//Texte
    private String m_textPrefix = "&6[&aPlayTime&6] &a";
    public String getTextPrefix()
    {
        return m_textPrefix;
    }

	private String m_textYourCurrentPlaytime = "Your current playtime is &6%t";
	public String getTextYourCurrentPlaytime()
	{
		return m_textYourCurrentPlaytime;
	}

    private String m_textPlayerPlaytimeIs = "The playtime from player &6%s&a is &6%t";
    public String getTextPlayerPlaytimeIs()
    {
        return m_textPlayerPlaytimeIs;
    }

    private String m_textPlayerNotFound = "&cThis player dont have play on this server.";
    public String getTextPlayerNotFound()
    {
        return m_textPlayerNotFound;
    }

	private String m_textTopPlayerListHead = "This is the Top 5 player list.";
	public String getTextTopPlayerListHead()
	{
		return m_textTopPlayerListHead;
	}

	private String m_textPlayerEntry = "%r : %s - %t";
	public String getTextPlayerEntry()
	{
		return m_textPlayerEntry;
	}

    private String m_textNoPermission = "&cYou dont have permission to do this!";
    public String getTextNoPermission()
    {
        return m_textNoPermission;
    }

    private String m_textDateDays = "&aDays&6";
    public String getTextDateDays()
    {
        return m_textDateDays;
    }

    private String m_textDateHours = "&aHours&6";
    public String getTextDateHours()
    {
        return m_textDateHours;
    }

    private String m_textDateMinutes = "&aMinutes&a";
    public String getTextDateMinutes()
    {
        return m_textDateMinutes;
    }

	private void setDefaultConfigValues(FileConfiguration _config)
	{
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
	}
	
	private void getConfigValues(FileConfiguration _config)
	{
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
	}
	
	private void saveConfigValues(FileConfiguration _config)
	{
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

		m_plugin.saveConfig();
	}

	private Plugin m_plugin;
	public Config(Plugin _plugin)
	{
		m_plugin = _plugin;
		setDefaultConfigValues(m_plugin.getConfig());
		getConfigValues(m_plugin.getConfig());
		saveConfigValues(m_plugin.getConfig());
	}
	
	public void reload()
	{
		m_plugin.reloadConfig();
		getConfigValues(m_plugin.getConfig());
	}
}
