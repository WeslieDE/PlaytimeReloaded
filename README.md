# PlaytimeReloaded
### A Minecraft Bukkit/Spigot  Plugin to save and display the playtime from all players.
Its need a mysql db to save all player data. Supports color codes in every messages/commands. Have full UUID support. Works on multiple servers (server networks). Posible to run command then a player reached a playtime.

## Command List

- /playtime [player]
    Show you own playtime or the playtime from [player]

- /toptime [numbers]
    Show the top [numbers] player with the best playtime.
	Default is 5

## Permissions

- playtime.login
The player get a playtime message at every login.

- playtime.use
The player can use the /playtime command.

- playtime.use.others
The player can see the playtime from other players.

- playtime.top
Can see the top 5 playtime list.

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



