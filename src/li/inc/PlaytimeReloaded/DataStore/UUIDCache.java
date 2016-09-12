package li.inc.PlaytimeReloaded.DataStore;


import java.util.*;

public class UUIDCache
{
    private static List<PlayerObject> m_players = new ArrayList<PlayerObject>();

    public static void update(String _playerName, UUID _playerUUID)
    {
        for (PlayerObject _dieserPlayer : m_players)
        {
            if(_dieserPlayer.getUUID() == _playerUUID)
            {
                return;
            }
        }

        m_players.add(new PlayerObject(_playerUUID, _playerName));
    }

    public static UUID get(String _playerName)
    {
        for (PlayerObject _dieserPlayer : m_players)
        {
            if(_dieserPlayer.getName() == _playerName)
            {
                return _dieserPlayer.getUUID();
            }
        }

        try
        {
            UUIDFetcher _uuidFetcher = new UUIDFetcher(Arrays.asList(_playerName));
            Map<String, UUID> _fetcherResponse = _uuidFetcher.call();
            UUID _playerUUID = _fetcherResponse.get(_playerName);

            update(_playerName, _playerUUID);

            return _playerUUID;
        }catch(Exception _e)
        {
            return null;
        }
    }
}
