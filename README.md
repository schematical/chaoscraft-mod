# chaoscraft-mod

## Getting Started:
### Get Authed:
```
/chaoscraft-auth {username} {password}
```

### Get Started:
```
/chaoscraft-start {trainingRoomOwnerUsername} {trainingRoomNamespace}
```

### Additional Helpful Notes for Beginners(Thank You 0xFFFF):
https://pastebin.com/GwwRkDSM



## Matt's SideNotes:
AWS APIGateway Build Commands(I Now have a script for this).
```
mvn clean package -Pstandalone-jar
cp target/ChaosNet-1.0-SNAPSHOT.jar  ~/IdeaProjects/chaoscraft/libs/ChaosNet-1.0-SNAPSHOT.jar


### Get Time of day:

World#getWorldTime() % 24000

###Stop Mobs from Spawning:
```
/gamerule doMobSpawning false
/kill @e[type=!player,name=!rick]
```

```
/kill @e[type=slime]
/kill @e[type=!player]
```

```
/time set day
```

```
/effect @p night_vision 9999 0 true
```

```
/tp @e[type=chaoscraft:rick] @p
```


/tp @e[type=chaoscraft:adam-0] @p
