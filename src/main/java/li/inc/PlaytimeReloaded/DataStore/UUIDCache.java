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


import org.bukkit.Bukkit;

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
            update(_playerName, null);
            return null;
        }
    }

    public static String get(UUID _playerUUID)
    {
        for (PlayerObject _dieserPlayer : m_players)
        {
            if(_dieserPlayer.getUUID() == _playerUUID)
            {
                return _dieserPlayer.getName();
            }
        }

        String _playerName = Bukkit.getPlayer(_playerUUID).getName();
        update(_playerName, _playerUUID);

        return _playerName;
    }
}
