# playgo-challenges

## Build

### Standalone

`playgo-challenges` uses `game-engine.challenge-gen` to work.

At the moment `game-engine.challenge-gen` is not available on Smartcommunitylab Nexus, so you have to install locally manually

1. Clone the project

`git clone https://github.com/smartcommunitylab/game-engine.challenge-gen.git`

2. Move to the right branch

`cd game-engine.challenge-gen && git checkout gamification-sdk`

3. Install on locally maven repo

`mvn clean install -DskipTests`

To create the `playgo-challenges` full standalone version run the command

`mvn clean package -P full-client-jar -DskipTests`

You can find the standalone jar into `target directory`

### Library

TBD

## Usage

### Standalone usage

You can run playgo-challenges in standalone mode using following command:

```bash
java -jar challenges-jar-with-dependencies.jar Application --config <CONFIG_PATH> --url <GAMIFICATION_ENGINE_URL> --username <USERNAME> --password <PASSWORD>
```

```
--config: the path of config file
--url: url to gamification engine
--username: valid gamification engine username
--password: valid gamification engine password
```

### Library

TBD


