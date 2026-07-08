README

zMCSandbox


(Join our public Discord and come participate in discussion as well as Map creation! Get feedback on your maps, make new friends and find new worlds to play!)




Overview


This mod contains a set of tools designed to allow players to create custom survival wave-based maps. 





RECOMMENDED


Turn off music in-game for further immersion



Notes


Armour can be crafted using drops from zombies, though I haven't gotten around to implementing it in the crate






Features


This feature rich mod includes many of the features seen in other popular survival-type gamemodes, including but not limited to:

A functional wave feature with appropriate difficulty scaling

An economy system, allowing players to set costs for different features (Still WIP

An purchase system, giving creators the power to set difficulty further

Several UI elements, including a round number and a point scoreboard





Function


When in game, the player can expect to deal with hordes of enemies. On activating the command, the waves begin. This sets the custom spawner block
as active, progressing through rounds as the player eliminates all hostiles.

As the player attacks or defeats an enemy, points are awarded which can then be used to purchase elements placed in the map by the creator. This can
be set to be as easy or as difficult as one likes, giving for a wide range of options on how maps play.





Installation


This mod REQUIRES Forge for Minecraft 1.19.3, with no dependencies. Compatibility with other mods will most likely be limited.

Simply drag the mod into the mods folder, and let your little creator heart go wild!





Map Creation


The map creation is made to be simple. With a handful of tools at your disposal, you are able to implement barrier for players to purchase as well as wall weapons which can be customised and have their prices set.


The perks are bundled into 1 block. This can be used to set any of the 4 implemented perks. Although the costs are preset and cannot be changed, I plan on giving the ability to change this too.


The barricade system and the spawner system are interconnected. Two wands are given. The first of which can be used to select and deselect blocks to set as purchasable. This is achieved by simply right clicking a set of blocks with the Blockade Wand, which can then be activated as a set by crouching and right clicking the air. A cost can then be set. Holding the sprint key will display all inactive clusters, which can be brought back to focus on then right clicking the inactive cluster. (This is still very buggy, but it does work.)

The second wand allows creators to deactivate spawners based on doors that haven't been purchased. By right clicking a spawner with the wand, followed by right clicking a blockade cluster, this will disable the spawner until the door has been purchased.


The wall weapon system can be placed as you would an item frame, and simply right clicking with the desired item. This will bring up the cost menu, allowing you to then set the cost for later purchase.

Additionally, there are commands creators can use in-game. These include '/rgivetools' which will immediately give the user the toolset needed to create maps, as well as '/resetblockades' which allows you to reset the map after playing.





How to play


Simple. When you're ready to play, activate the '/startwaves' command. If for any reason you're done playing, you can use '/stopwaves' 





Known Issues


The perk machine renderer got the better of me. This early alpha fails to render the texture of the machine

There are transparency issues with the spawners

Wall weapon frames can be placed at odd angles, breaking the texture

There is no way to tell which mystery crate is active yet

There are almost certainly bugs with the barrier system and the spawner mechanics.

Spawners can only link to one door for the time being. Limiting, I'm aware, but I will fix this asap

Zombie pathfinding sucks currently

Perks do not reset as they're meant to. Copy before playing.

Linking function between doors and spawners DO NOT WORK as of yet. I need to do some digging into this. I'm sure you can make cool maps without doors for now ;)


(Remember, this is an Alpha build. While mildly polished, do not be surprised if you encounter some issues)




Planned Features


This is the fun part


New boss zombies will be added as time goes on

More tool sets will be added, with deeper progression.

Animations are planned for different features such as the perk machines and the crate

The UI will naturally be polished over time

Special Weapons, invoking new playstyles




Credits


Ari. This was all coded by Aria in a week, picked up from a codebase which I put down over a year ago. (I lost a LOT and I still don't know how.)