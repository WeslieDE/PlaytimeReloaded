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

public class TimeCommand
{
    private int m_timeInMin = 0;
    private String m_command = "";

    public int getTime()
    {
        return m_timeInMin;
    }
    public String getCommand()
    {
        return m_command;
    }

    public TimeCommand(int _time, String _command)
    {
        m_timeInMin = _time;
        m_command = _command;
    }
}
