package li.inc.PlaytimeReloaded.DataStore.DB;

import java.sql.*;


public class MySQLTools 
{
	Connection m__MySQLConnection;
	
	String m_host;
	int m_port;
	String m_dbname;
	String m_username;
	String m_passwort;
	
	//Public Funktionen
	
	public MySQLTools(String _host, int _port, String _dbname, String _username, String _passwort)
	{
		m_host = _host;
		m_port = _port;
		m_dbname = _dbname;
		m_username = _username;
		m_passwort = _passwort;
		
		m__MySQLConnection = createSQLConnection(m_host, m_port, m_dbname, m_username, m_passwort);
	}
	
	public Connection getConnection()
	{
		return m__MySQLConnection;
	}
	
	
	//Private Funktionen
	
	private Connection createSQLConnection(String _host, int _port, String _dbname, String _username, String _passwort)
	{
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
	
	private void pingConnection()
	{ 
		try {
			if(m__MySQLConnection.isClosed())
			{
				m__MySQLConnection = createSQLConnection(m_host, m_port, m_dbname, m_username, m_passwort);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void saveMySQLUpdate(String _quarry, String[] _values)
	{
		pingConnection();

		System.out.println(_quarry);

		try
		{
			PreparedStatement _ps = m__MySQLConnection.prepareStatement(_quarry);
			
			int index = 0;
			for( String _s: _values )
			{
				String thisValue = (String)_s;
				_ps.setString(++index, thisValue);
			}

			_ps.executeUpdate();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public ResultSet saveMySQLQuarry(String _quarry, String[] _values)
	{
		pingConnection();

		System.out.println(_quarry);

		try
		{
			PreparedStatement _ps = m__MySQLConnection.prepareStatement(_quarry);

			int index = 0;
			for( String _s: _values )
			{
				String thisValue = (String)_s;
				_ps.setString(++index, thisValue);
			}
			
			return _ps.executeQuery();
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
