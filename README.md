# playgo-challenges

## Build

### Standalone

`playgo-challenges` uses `game-engine.challenge-gen` to work.

At the moment `game-engine.challenge-gen` is not available on Smartcommunitylab Nexus, so you have to install locally manually

1. Clone the project

`git clone https://github.com/smartcommunitylab/game-engine.challenge-gen.git`

2. Move to the right branch

`cd game-engine.challenge-gen && git checkout gamification-sdk`

3. Install on your local maven repo

`mvn clean install -DskipTests`

To create the `playgo-challenges` full standalone version run the command

`mvn clean package -P full-client-jar -DskipTests`

You can find the standalone jar `challenges-jar-with-dependencies.jar` into `target` directory

### Library

TBD

## Usage

### Standalone usage

You can run playgo-challenges in standalone mode using following command:

```bash
java -jar challenges-jar-with-dependencies.jar Application --config <CONFIG_PATH> --url <GAMIFICATION_ENGINE_URL> --username <USERNAME> --password <PASSWORD> [--assign <SUPPORTED_VALUES>]
```

```
--config: the path of config file
--url: url to gamification engine
--username: valid gamification engine username
--password: valid gamification engine password
--assign: (optional, default: all) comma separated string of assignment types to process
          supported values: standardSingle, standardGroup, specialSingle
```
You can find an example in [bin/assign-challenges.sh][assign_script]
### Library

TBD

[assign_script]: ./bin/assign-challenges.sh
