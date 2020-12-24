# PlaytimeReloaded
### A Spigot/paper plugin to track playtime of all players on the server.
Track and save the playtime of all players in a SQLite or MySQL database. Supports command execution, then a player reach a defined playtime.

## Command List

- /playtime [player] 
    Show your own playtime or the playtime from other players.

- /toptime [numbers]
    Shows the top [numbers] of players with with the highest playtime.

## Permissions

- playtime.login
	Shows the playtime at login.

- playtime.use
	The player can use the /playtime command.

- playtime.use.others
	The player can see the playtime from other players.

- playtime.top
	Can see playtime list.

## Config

```javascript
mysql:
  host: 127.0.0.1
  port: 3306
  db: test
  user: test
  pass: test
text:
  Date:
    Days: '&aDays&6'
    Hours: '&aHours&6'
    Minutes: '&aMinutes&a'
  Prefix: '&6[&aPlayTime&6] &a'
  YourCurrentPlaytime: Your current playtime is &6%t
  PlayerPlaytimeIs: The playtime from player &6%s&a is &6%t
  PlayerNotFound: '&cThis player dont have play on this server.'
  NoPermission: '&cYou dont have permission to do this!'
  TopPlayerTitel: This is the Top 5 player list.
  TopPlayerEntry: '%r : %s - %t'
timeCommand:
- 8;say &6[&aPlayTime&6] &6%%player%% &ahas &6%%time%% &aminutes &aplayed.
```



