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

#### How to deploy 

To deploy a new version of challenges on the Smartcommunitylab Nexus you have to configure a valid user into your `~/.m2/settings.xml`

##### Deploy Snapshot

```bash
mvn clean install  deploy:deploy-file  \
-Dmaven.test.skip=true \
-Dpackaging=jar \
-DrepositoryId=SmartCommunityLab-snapshots \
-DpomFile=pom.xml \
-Durl=http://repository.smartcommunitylab.it/content/repositories/snapshots \
-Dfile=target/challenges.jar
```

##### Deploy Release

```bash
mvn clean install  deploy:deploy-file  \
-Dmaven.test.skip=true \
-Dpackaging=jar \
-DrepositoryId=SmartCommunityLab-releases \
-DpomFile=pom.xml \
-Durl=http://repository.smartcommunitylab.it/content/repositories/releases \
-Dfile=target/challenges.jar
```

## Usage

### Standalone usage

You can run playgo-challenges in standalone mode using following command:

```bash
java -jar challenges-jar-with-dependencies.jar Application \
--config <CONFIG_PATH> \
--url <GAMIFICATION_ENGINE_URL> \
--username <USERNAME> \
--password <PASSWORD>\
--api_user <USERNAME> \
--api_pass <PASSWORD>\
[--task <SUPPORTED_VALUES>] \
[--execDate <DATE>]
[--assign true]
```

```
--config: the path of YAML config file
--url: url to gamification engine
--username: valid gamification engine username
--password: valid gamification engine password
--api_user: valid api username
--api_pass: valid api password
--task: (optional, default: all) comma separated string of assignment types to process
          supported values: standardSingle, standardGroup, specialSingle
--execDate: (optional, default: now) date as YYYY-MM-dd. This option is useful for test and debug, it
            permit to simulate an execution in the given date. 
            Challenge start and end date and all other time sensitive costraints will be relative to this date.
--assign: (optional, default: none) assigns the generated challenges at the end of the task
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

```
    standardGroupChallenges:
      settings:
        start: # begin date of the challenge season for the game, format as: YYYY-MM-DD HH:MM [Europe/Rome timezone] 
        duration: # duration of the challenges, format X[d,w,m] X -> number, d: days, w: weeks, m:months ex: 7d for seven days
        model: # group challenge model, valid values: groupCooperative, groupCompetitivePerformance, groupCompetitiveTime
        modeConcepts: # list of pointConcept challenge-gen has to consider, ex. Walk_Km
          -
      playerSet:  # a list of playerIds target of assignment, leave empty to assign to all
      reward: # parameters to calculate the reward amount
        scoreName: # pointConcept name
        type: # unused in group challenge scenario leave empty
        value: # unused in group challenge scenario leave empty
        maxValue: # unused in group challenge scenario leave empty
```

### SpecialSingleChallenges

```
    specialSingleChallenges: 
      - settings:
          start: # begin date of the challenge season for the game, format as: YYYY-MM-DD HH:MM [Europe/Rome timezone]
          duration: # duration of the challenges, format X[d,w,m] X -> number, d: days, w: weeks, m:months ex: 7d for seven days
          model: # single challenge model
          fields: # fields relative to selected model, express as FIELD_NAME: VALUE one for every line
          hide: # True to assign a hidden challenge, False otherwise
        playerSet: # a list of playerIds target of assignment, leave empty to assign to all
        reward: # parameters to calculate the reward amount
          scoreName: # pointConcept name, it will be used as "bonusPointType" field of the challenge
          type: # reward strategy used by challgenge-gen. For special single challenges only valid value is: fixed
          value: # value bound to type field, it will be used as "bonusScore" field of the challenge
          maxValue: # unused in special single challenge scenario leave empty
```

[assign_script]: ./bin/assign-challenges.sh
[config_sample]: src/test/resources/config-simulation.yml]
