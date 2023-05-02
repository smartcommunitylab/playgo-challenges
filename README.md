# playgo-challenges

## Requirements

* Jdk 11 (Tested with Java 11.0.5)

## Build

### Standalone

`playgo-challenges` uses `game-engine.challenge-gen` to work.

At the moment `game-engine.challenge-gen` is not available on Smartcommunitylab Nexus, so you have to install locally manually

1. Clone the project 

`git clone https://github.com/smartcommunitylab/game-engine.challenge-gen.git`

2. Move to the right branch

`cd game-engine.challenge-gen`

3. Install on your local maven repo

`mvn clean install -DskipTests`

To create the `playgo-challenges` full standalone version run the command after cloning the project

`git clone https://github.com/smartcommunitylab/playgo-challenges.git`

Move to project directory

`cd playgo-challenges`

Build the project
 
`mvn clean install -DskipTests`



### Web application


### Basic Auth
The application is protected with Basic Auth mode, the credentials of which is configured in users.yml file.

```shell
src/main/resources/users.yml
```

By default the application starts on port 8020. The configuration can be changed in application.yml file.

```shell
src/main/resources/application.yml
```
The challenge configuration file can be specified either by overriding the config.challengeUrl property or by explicitly specifying $CHALLENGE_URL environment variable. The file description is detailed below in the configuration file section.

The schedule configuration file can be specified either by overriding the config.scheduleUrl property or by explicity specifcying the $SCHEDULE_URL environment variable. The file
allows to specify list of schedules for challenges per game using cron expression.

```
schedules:
 - gameId: 62555de98d3ac50d84315d70
   expression: 0 48 * * * *
   task: standardSingle
   assign: true

 - gameId: 62555de98d3ac50d84315d70
   expression: 0 49 * * * *
   task: standardGroup
   assign: true
   
 - gameId: 62555de98d3ac50d84315d70
   expression: 0 52 * * * *
   task: specialSingle
   assign: true
   
 - gameId: 62e3877b5b171200f39f8c49
   expression: 0 45 * * * *
   task: specialSingle
   assign: true
```

The following set of properties can be overridden in application.yml file or can be defined as environment variable during application launch.

`$GAMIFICATION_URL <url of gamification engine for e.g. http://localhost:8010/gamification>`

`$API_USER <gamification engine basic auth username>`

`$API_PASS <gamification engine basic auth password>`

`$CHALLENGE_URL <url/path of challenge configuration yaml file.>`

`$SCHEDULE_URL <url/path of challenge scheduler configuration yaml file.>`

`$POSTGRES_URL <connection url of postgres instance for e.g. jdbc:postgresql://localhost:5432/gamification?user=postgres&password=root>`

`$security.usersFile <url/path of security yaml file with credentials of basic auth.>`
### Run

In order to start web engine, it is required to run the following command from project root(profile specific). The web application on launch reads the challenge configuration file
and schedule challenge generation tasks based on scheduing configuration defined for the game inside schedule.yml file.

```shell
java -jar target\challenges.jar --spring.profiles.active=sec --config.url=http://localhost:8010/gamification --config.api_user=long-rovereto --config.api_pass=rov --config.challengeUrl=/home/dev/gamification/challenge-assignment-ferrara-v1.0.yml --config.scheduleUrl=/home/dev/gamification/schedule.yml --config.postgresUrl=jdbc:postgresql://localhost:5432/gamification?user=postgres&password=root
```

### API Console
In order to access the API console based on Spring Doc, open in browser the following url

```shell
http://localhost:8020/challenge-generator/swagger-ui/index.html
```

### PUT /api/reschedule

The API reset the schedules for challenge generation tasks.

```
curl -v -u "username:pass" -X "PUT" "http://localhost:8020/challenge-generator/api/reschedule" -H "accept: */*"
```

### POST /api/generate/ 

Request body

```
curl -v -u "username:pass" -X 'POST' \
  'http://localhost:8020/challenge-generator/api/generate' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '[{
	"gameId": "$gameId",
	"standardSingleChallenge": {
		"levelStrategies": [
			{
				"level": {
					"type": "Green Warrior",
					"index": 0
				},
				"strategy": "empty"
			},
			{
				"level": {
					"type": "Green Warrior",
					"index": 1
				},
				"strategy": "fixedOne"
			},
			{
				"level": {
					"type": "Green Warrior",
					"index": 2
				},
				"strategy": "choiceTwoV2"
			},
			{
				"level": {
					"type": "Green Warrior",
					"index": 3
				},
				"strategy": "choiceThreeV2"
			}
		],
		"settings": {
			"start": "2022-08-01 00:00",
			"duration": "P7D",
			"modeConcepts": [
				"Walk_Km",
				"Bike_Km"
			],
			"hide": true,
			"suspensions": []
		},
		"playerSet": [],
		"reward": {
			"scoreName": "green leaves",
			"type": "FIXED",
			"value": 100.0
		}
	},
	"standardGroupChallenge": {
		"settings": {
			"start": "2022-08-01 00:00",
			"duration": "P7D",
			"model": "groupCooperative",
			"modeConcepts": [
				"Walk_Km",
				"Bike_Km",
				"green leaves"
			]
		},
		"playerSet": [],
		"reward": {
			"scoreName": "green leaves"			
		}
	}
}
]'

```

Note: Duration is java.time.Period with following valid inputs

```
"P2Y"             -- Period.ofYears(2)
"P3M"             -- Period.ofMonths(3)
"P4W"             -- Period.ofWeeks(4)
"P5D"             -- Period.ofDays(5)
"P1Y2M3D"         -- Period.of(1, 2, 3)
"P1Y2M3W4D"       -- Period.of(1, 2, 25)
"P-1Y2M"          -- Period.of(-1, 2, 0)
"-P1Y2M"          -- Period.of(-1, -2, 0)
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



