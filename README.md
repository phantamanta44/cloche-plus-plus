# Cloche++

This mod will let you do **many Garden Cloche** for **great profit**!

## What does it do?

Cloche++ adds more mod integrations for the [Immersive Engineering](https://github.com/BluSunrize/ImmersiveEngineering) garden cloche.
In particular, it does the following:

* Allows the growth of [Mystical Agradditions](https://github.com/BlakeBr0/MysticalAgradditions) seeds.
* Allows the growth of [AgriCraft](https://github.com/AgriCraft/AgriCraft) seeds and integrates with its seed stat system.
* Allows the use of [Mystical Agriculture](https://github.com/BlakeBr0/MysticalAgriculture) mystical fertilizer.

## How do I configure it?

Cloche++ is divided into a number of modules, each of which is further divided into a number of components.
Each module corresponds to one external mod that Cloche++ provides integration for, while each component of a module is a single feature of that integration.
For example, the Mystical Agradditions module has a component for nether star seeds; you could disable that component to disable integration for nether star seeds, or you could disable the entire Mystical Agradditions module as a whole.

To disable a module, add its module ID to the `disabledModules` list in the config.
To disable a component, add a module ID/component ID pair in the form of `moduleid:componentid` to the `disabledComponents` list in the config.
Module and component IDs can be discovered in the game's debug logs on startup, or by looking through the subpackages of `xyz.phanta.clochepp.module` in the source code.

## Doesn't this already exist?

Originally, I was planning on PRing a fix for the outstanding issues on [IE Cloche Compat](https://github.com/NicJames2378/IEClocheCompat), but I eventually decided to just write a new mod from the ground up.
The necessary changes would have been rather drastic, since IE Cloche Compat's codebase is not very well-organized;
I figured that since I was basically rewriting the entire mod for a PR, it would be better to scrap it entirely.
