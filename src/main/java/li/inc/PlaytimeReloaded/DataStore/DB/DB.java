package li.inc.PlaytimeReloaded.DataStore.DB;

import li.inc.PlaytimeReloaded.DataStore.Config;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.UUID;

public class DB
{
    private IDB m_DBClass = null;

    public DB(Plugin _plugin, Config _config)
    {
        if(_config.getDBType().trim().equalsIgnoreCase("mysql"))
            m_DBClass = new MySQL();

        if(_config.getDBType().trim().equalsIgnoreCase("sqllite"))
            m_DBClass = new SQLLite();

        if(m_DBClass == null)
            m_DBClass = new SQLLite();

        m_DBClass.init(_plugin, _config);
    }

    public void update(UUID _playerUUID, int _newTime)
    {
        m_DBClass.update(_playerUUID, _newTime);
    }

    public List<String[]> getTopPlayers(int _count){ return m_DBClass.getTopPlayers(_count); }

    public int getPlayerTime(UUID _playerUUID)
    {
        return m_DBClass.getPlayerTime(_playerUUID);
    }

    public void close()
    {
        m_DBClass.close();
    }
}
