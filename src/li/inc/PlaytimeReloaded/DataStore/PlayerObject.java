package li.inc.PlaytimeReloaded.DataStore;

import java.util.UUID;

public class PlayerObject
{
    private UUID m_playerUUID;
    private String m_playerName = "";

    public PlayerObject(UUID _playerUUID, String _playerName)
    {
        m_playerUUID = _playerUUID;
        m_playerName = _playerName;
    }

    public UUID getUUID()
    {
        return m_playerUUID;
    }

    public String getName()
    {
        return m_playerName;
    }
}
