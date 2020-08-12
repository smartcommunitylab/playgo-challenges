# playgo-challenges

## Requirements

* Jdk 1.8+

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
java -jar challenges-jar-with-dependencies.jar Application \
--config <CONFIG_PATH> \
--url <GAMIFICATION_ENGINE_URL> \
--username <USERNAME> \
--password <PASSWORD>\
[--assign <SUPPORTED_VALUES>] \
[--execDate <DATE>]
```

```
--config: the path of YAML config file
--url: url to gamification engine
--username: valid gamification engine username
--password: valid gamification engine password
--assign: (optional, default: all) comma separated string of assignment types to process
          supported values: standardSingle, standardGroup, specialSingle
--execDate: (optional, default: now) date as YYYY-MM-dd. This option is useful for test and debug, it
            permit to simulate an execution in the given date. 
            Challenge start and end date and all other time sensitive costraints will be relative to this date.
```
You can find an example in [bin/assign-challenges.sh][assign_script]
### Library

TBD

## Configuration file

Configuration file is a YAML file, you can find a realistic example [here][config_sample]
For every game you can define parameters for three assignement scenarios `standardSingleChallenges`, `standardGroupChallenges` and `specialSingleChallenges`

### StandardSingleChallenges

```
    standardSingleChallenges:
      levelStrategies:  # assignment strategy for player level
        - level:
            type:  # level name
            index: # level index
          strategy: # name of the strategy implemented by challenge-gen
      settings:
        start: # begin date of the challenge season for the game, format as: YYYY-MM-DD HH:MM [Europe/Rome timezone] 
        duration: # duration of the challenges, format X[d,w,m] X -> number, d: days, w: weeks, m:months ex: 7d for seven days
        suspensions: # a list of time periods in which assignment is suspended
        - from: # initial date of suspesion period [included date], format as YYYY-MM-DD HH:MM [Europe/Rome timezone]
          to: # end date of suspension period [excluded date], format as YYYY-MM-DD HH:MM [Europe/Rome timezone]
        modeConcepts: # list of pointConcept challenge-gen has to consider, ex. Walk_Km
          -
        hide: # True to assign a hidden challenge, False otherwise
      playerSet: # a list of playerIds target of assignment, leave empty to assign to all
        - 
      reward: # parameters to calculate the reward amount
        scoreName: # pointConcept name
        type: # reward strategy used by challgenge-gen: fixed, bonus, booster 
        value: # value bound to type field
        maxValue: # optional, max reward value assignable
```

### StandardGroupChallenges

TBD

### SpecialSingleChallenges

TBD

[assign_script]: ./bin/assign-challenges.sh
[config_sample]: src/test/resources/config-simulation.yml]
