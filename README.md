# chaoscraft-mod

## Getting Started:

## Clone repo:
```
git clone https://github.com/schematical/chaoscraft-mod.git
```

## Run Gradle:
I recommend using IntelliJ as an IDE but the ultra quick and dirty start is to open up the terminal and type:
```
gradlew runClient
```
This will start minecraft and you are ready to go!

### Get Authed:
Before you can get authenticated you need credentials, these will be provided by me in the discord server.
Afterwards you can run this command in an open world in MC to get authenticated.
```
/chaoscraft-auth {username} {password}
```
You will only have to do this once, because it will save your credentials locally.
### Get Started:
To actually run the mod you need to execute this command within MC.
```
/chaoscraft-start {trainingRoomOwnerUsername} {trainingRoomNamespace}
```
Currently the majority of people are using this namespace:
```
/chaoscraft-start schemetical test-3
```

### Additional Helpful Notes for Beginners(Thank You 0xFFFF):
https://pastebin.com/GwwRkDSM


### If you get stuck and keep getting 400 or 500 errors:
Try running the following:
```
/chaoscraft-repair
```
This will attempt to repair your build.

## Other stuff:
### CurseForge:
Eventually the mod may live at the following:

https://minecraft.curseforge.com/projects/chaoscraftai





## Matt's SideNotes:
AWS APIGateway Build Commands (I now have a script for this).
```
mvn clean package -Pstandalone-jar
cp target/ChaosNet-1.0-SNAPSHOT.jar  ~/IdeaProjects/chaoscraft/libs/ChaosNet-1.0-SNAPSHOT.jar
```

### Get Time of day:

```
World#getWorldTime() % 24000
```

###Stop Mobs from Spawning:
```
/gamerule doMobSpawning false
```

```
/kill @e[type=!player,name=!rick]
```

```
/kill @e[type=slime]
```

```
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

```
/tp @e[name=adam] @p
```



shift click on a item in creative tab it grabs a max stack for you
```ctrl+q``` to drop a stack

