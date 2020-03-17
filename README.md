# Split-Screen FPS Games
A 1-4 player split-screen FPS engine and games.

I have a very short interest span when it comes to gamedev: this codebase contains various games, and I work on whichever one interests me at any moment in time (or sometimes start work on a new one).  The games that are selectable from the first menu are typically mostly complete.  The rest may be barely started.

Currently, the following games are at various stages of progress:
* 3D Monster maze: escape from the maze
*  Alien Tag: Don't be the player who's been tagged the longest
* Tower Defence: Build turrets to shoot the aliens
* Funny Farm: Grow and harvest crops
* FTL: An FTL-inspsired 3D game
* Deathchase: Crash into other players
* Bladerunner: Identify and kill the replicant
* Stock car: Race around a track


### Example Videos
* 3D Monster maze:  https://www.youtube.com/watch?v=YDUUCQZeCSI


## Controls
* Press Space at the start to use keyboard/mouse, or press X on your controller to use that.

It depends on the specific game selected, but controls are typically:-

* W, A S, D, Space and Enter for keyboard & mouse player.  H for Help (where implemented).
* Tested with PS4 controllers.
* F1 - Toggle full Screen
* F2 - Toggle full screen but still windowed (required if you want to record the screen using Windows)


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


## Notes for other developers
* See Settings.java for settings that determine what game is selected.

