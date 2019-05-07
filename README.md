# DI-1-Hexentanz

[![Build Status](https://travis-ci.com/elhoferson/DI-1-Hexentanz.svg?branch=master)](https://travis-ci.com/elhoferson/DI-1-Hexentanz)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=DI-1-Hexentanz&metric=alert_status)](https://sonarcloud.io/dashboard?id=DI-1-Hexentanz)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=DI-1-Hexentanz&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=DI-1-Hexentanz)

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=DI-1-Hexentanz&metric=coverage)](https://sonarcloud.io/dashboard?id=DI-1-Hexentanz)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=DI-1-Hexentanz&metric=bugs)](https://sonarcloud.io/dashboard?id=DI-1-Hexentanz)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=DI-1-Hexentanz&metric=code_smells)](https://sonarcloud.io/dashboard?id=DI-1-Hexentanz)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=DI-1-Hexentanz&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=DI-1-Hexentanz)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=DI-1-Hexentanz&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=DI-1-Hexentanz)

# Sofware Engineering II - Hexentanz App

An Hexentanz game implementation with Java for Android Phones.


The requirements were:
* A playable game
* Multiplayer functionality using P2P
* A way to 'cheat' and detect cheating as a player, and react to it
* Additional custom rules or functionality, which is not part of the original game
* Based on the existing boad game "Hexentanz"

It was created for the course _Software Engineering II_ on _Alpen-Adria-University Klagenfurt_.

### Description
An __Hexentanz__ implementation, with custom cards and cheating functionality.

* Max supported players: 6
* Custom game board
* Cheating supported
* Cheating detection supported
* Shaking smartphone for rolling the dice supported
* Multiplayer supported


### Cheating and detection
Cheating is possible in a build in way. It is possible see the color of the witches, when covering the brightness sensor. So it's helpful for moving the right witch to the goal.

If you detect that a player has cheated, than you can blame him for cheating. If you are correct, the player has to miss a turn, if not, you have to miss a turn.

### Networking
The networking component is completely own-written, and is based on tcp sockets.
The Host opens a game, and other devices can connect to him via the ip.
Its written completely non-blocking, so a easy usability is given.

## Build with
* [Android Studio](https://developer.android.com/studio/) - The IDE used
* [Git](https://git-scm.com) - for VC
* [Github](https://github.com) - online VC, pull requests, webhooks, etc.
* [Sonarcloud](https://sonarcloud.io) - for linting (code quality, code smells, coverage)
* [Jacoco](https://www.eclemma.org/jacoco) - test coverage
* [Travis](https://travis.com) - build service
* [Gradle](https://gradle.org) - package manager

## Versioning
We used __git__ for versioning, and __Github__ for pull requests, reviews, hooks etc. 

## Authors
* __Hofer Alexander__
  * Continous Integration 
  * Network
* __Hofmann Daniel__
  * Cheating Function
* __Hülser Matthias__
  * Board Game Design
  * Interface design
* __Isak Selina__
  * Witches
* __Szolderits Chiara__
    * Sensor Implementation
    * Walking on Board Game
* __Wiltschnig Kevin__
    * Game Logic
  

## Acknowledgment
Thank you [stackoverflow](https://stackoverflow.com), for answering many questions :)

