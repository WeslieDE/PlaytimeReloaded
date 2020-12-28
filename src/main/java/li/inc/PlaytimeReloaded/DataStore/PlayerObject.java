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

import java.util.UUID;

public class PlayerObject {
    private UUID m_playerUUID;
    private String m_playerName = "";

    public PlayerObject(UUID _playerUUID, String _playerName) {
        m_playerUUID = _playerUUID;
        m_playerName = _playerName;
    }

    public UUID getUUID() {
        return m_playerUUID;
    }

    public String getName() {
        return m_playerName;
    }
}
