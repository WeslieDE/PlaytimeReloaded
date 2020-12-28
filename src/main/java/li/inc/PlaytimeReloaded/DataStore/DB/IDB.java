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
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.UUID;

public interface IDB
{
    public void init(Plugin _plugin, Config _config);
    public void update(UUID _playerUUID, int _newTime);
    public List<String[]> getTopPlayers(int _count);
    public int getPlayerTime(UUID _playerUUID);
    public void close();
}
