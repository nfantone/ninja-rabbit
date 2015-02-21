![libGDX 2D Platformer](https://db.tt/ogB3znYV "rabbit")  ninja-rabbit
==========

A very simple desktop and [OUYA](https://www.ouya.tv/) 2D platform game built in **JAVA** with _libGDX_ simply to demonstrate the use of various technologies and design patterns as I was learning them. This is, by no means, a complete video game and it puts no effort into being one. 

----------

#### Running the game
The following commands should be enough to run the game in any desktop platform with **JAVA** 1.6+ installed.

```
git clone https://github.com/nfantone/ninja-rabbit.git
cd ninja-rabbit
gradlew desktop:run
```

#### Technologies
This project was developed using the following frameworks and tools:

 - [libGDX](http://libgdx.badlogicgames.com/) - A great **JAVA** game development framework.
 - [gdx-ai](https://github.com/libgdx/gdx-ai) - Artificial Intelligence framework for libGDX games.
 - [Box2D](http://box2d.org/) - Physics engine. _libGDX_ has a built-in **JAVA** implementation of it.
 - [libgdx-utils](https://bitbucket.org/dermetfan/libgdx-utils/wiki/Home) - A set of utility classes and methods that alleviates common work while using _libGDX_. Written by the renowned [@dermetfan](https://www.youtube.com/user/dermetfan).
 - [Physics Body Editor](http://www.aurelienribon.com/blog/projects/physics-body-editor/) - Developed by [Aurelien Ribon](http://www.aurelienribon.com/blog/), this tool allows you draw Box2D bodies for your sprites, export the data as `json` and load them in your _libGDX_ game.
 - [Tiled](http://www.mapeditor.org) - Free tile map editor. Used as a level editor, as well as laying out physics related objects.
 - [TexturePacker](https://www.codeandweb.com/texturepacker) - Creation of sprite sheets. Allows exporting to _libGDX_ atlas format.

An [artemis-odb](https://github.com/junkdog/artemis-odb/wiki) implementation is in the making.

> With the exception of _TexturePacker_, all of the above are free and open source projects. You might as well considered donating or contributing something back if they have managed to help you in some way or if you enjoy using them. 

> _libGDX_ has [its own](https://github.com/libgdx/libgdx/wiki/Texture-packer) open source texture packer, as an alternative to the **CodeAndWeb** application. 

#### Design
Ideas for the architecture and design were shamelessly taken from the phenomenal [Game Programming Patterns](http://gameprogrammingpatterns.com) online book, by _EA_ developer [@munificentbob](https://twitter.com/intent/user?screen_name=munificentbob), following the path of [Erich Gamma](http://https://en.wikipedia.org/wiki/Erich_Gamma) and cia. Honorable mentions go to the [Component](http://gameprogrammingpatterns.com/component.html), [State](http://gameprogrammingpatterns.com/state.html) and [Observer](http://gameprogrammingpatterns.com/observer.html) design patterns. All of them were implemented in **ninja-rabbit**.

> **Game Programming Patterns** is a must read. If you think of yourself a game developer or enthusiast, you owe to yourself reading it.

Also worth mentioning, is the handy work of [ForeignGuyMike](http://neetlife2.blogspot.com.ar/) who created the [LibGDX Box2D Tiled video tutorial series](http://youtu.be/85A1w1iD2oA?list=PL-2t7SM0vDfdYJ5Pq9vxeivblbZuFvGJK). Great for starting out with Box2D and drawing sprites.

Let's not forget the invaluable help of the [Libgdx Cross-platform Game Development Cookbook](https://www.packtpub.com/game-development/libgdx-cross-platform-game-development-cookbook). You should give a it try.

----------

#### Controls


* You move the rabbit character around by using the `W`, `A`, `S`, `D` keys. Self explanatory.
* Collect carrots for points and trivial fun.
* If you fall down a pit, you'll lose a life and respawn at the beginning of the level. 
* There is an arbitrary ticking clock. Provides a sense of imminent doom.
* You have three lives. Losing them all means _game over_. No continues. Use them wisely.

------

Screenshots
-----------
![At the start of the level](https://www.dropbox.com/s/40yo6w7p9ms2q27/rabbit-screenshot.png?dl=0&raw=1 "Screenshot")

The rabbit character in the game is the work of graphic designer Julieta María Ojea ([@JulietteChuu](https://twitter.com/JulietteChuu)).
Tiles and other assets were gracefully borrowed from the [Platformer Art Complete Pack](http://www.kenney.nl/assets) published under [Creative Commons](http://creativecommons.org/publicdomain/zero/1.0/) license by [Kenney Vleugels](www.kenney.nl)
