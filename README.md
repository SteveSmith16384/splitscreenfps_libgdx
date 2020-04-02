# Split-Screen FPS Games
A 1-4 player split-screen multiplayer games.

Gameplay video: https://twitter.com/stephencsmith

This codebase contains various game modes that all use the same framework, and I work on whichever one interests me at any moment in time (or sometimes start work on a new one).  When the game starts, you will begin in the "overworld".  To start a particular game mode, walk into the area that denotes that mode. 

Currently, the following games are at various stages of progress:
* 3D Monster maze: escape from the maze.  Status Complete
* Alien Tag: Don't be the player who's been tagged the longest.  Status: Complete
* Tower Defence: Build turrets to shoot the aliens.  Status: Can walk around and place turrets.
* Funny Farm: Grow and harvest crops.  Status: You can walk around an empty field
* FTL: An FTL-inspsired 3D game.  Status: You can walk around a spaceship 
* Deathchase: Crash into other players.  Status: You can drive
* Bladerunner: Identify and kill the replicant.  Status: Barely started
* Stock car: Race around a track.  Status: You can drive
* Dungeon: A 3D Roguelike.  Status: You can walk around a basic procedurally-generated dungeon.


## Controls
* Press Space at the start to use keyboard/mouse, or press X on your controller to use that.
* Esc to exit back to the overworld, or to quit out.

It depends on the specific game selected, but controls are typically:-

* W, A S, D, Space and Enter for keyboard & mouse player.  H for Help (where implemented).
* Tested with PS4 controllers.
* F1 - Toggle full Screen
* F2 - Toggle full screen but still windowed (required if you want to record the screen using Windows)


## Notes for other Developers
* Development branch is the cutting edge but possibly broken branch.  Master is the most stable but out of date.
* Gradle is a real pain to work with.  However, if you have trouble loading this project, I used Gradle v4.10.3.
* The file Settings.java contains various settings that determine what game mode the game starts in.


## Licence
This project uses the MIT licence.  See LICENCE.txt.


## Credits
* Designed and programmed by Stephen Carlyle-Smith https://twitter.com/stephencsmith
* Uses the LibGDX game framework
* Uses BasicECS from https://github.com/SteveSmith16384/BasicECS
* Controller code from https://github.com/electronstudio/sdl2gdx


### Assets Credits
* Car models taken from https://kenney.nl/assets/car-kit
* All humanoid figures by Quaternius http://quaternius.com
* Music by Matthew Pablo http://www.matthewpablo.com/ (Taken from https://opengameart.org/content/heroic-demise-updated-version)
* Game-specific credits as follows:-


#### 3D Monster Maze
* Ascii-style T-Rex by Malcolm Evans
* Monster growls from https://opengameart.org/content/large-monster
* Scream from https://opengameart.org/content/aargh-male-screams

#### Alien Tag
* Squishy sfx from https://opengameart.org/content/8-wet-squish-slurp-impacts

#### Deathchase
* 2D trees taken from https://opengameart.org/content/pixel-art-simple-trees
* 2D plants/flowers taken from https://opengameart.org/content/flowers
* Flat trees wall taken from https://opengameart.org/content/forest-trees-wall

#### Funny Farm
* Farm animal sfx from https://opengameart.org/content/farm-animals

#### FTL
* Alien by Quaternius
* Spaceship furniture by Kenney (www.kenney.nl)
* Battery by [TODO]

#### Tower Defence
* Models by Kenney - https://kenney.nl/assets/tower-defense-kit
* Alien by Quaternius
* Rotating coin by Master484: https://opengameart.org/content/cute-platformer-sisters

