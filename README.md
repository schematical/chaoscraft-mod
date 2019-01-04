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

mvn clean package -Pstandalone-jar
cp target/ChaosNet-1.0-SNAPSHOT.jar  ~/IdeaProjects/chaoscraft/libs/ChaosNet-1.0-SNAPSHOT.jar




##Matts Notes:
###Stop Mobs from Spawning:
```
/gamerule doMobSpawning false
/kill @e[type=!player,name=!rick]
```

```
/kill @e[type=slime]
/kill @e[type=!player]
```
