# Xerus - Core Game Library

![Xerus](.github/assets/xerus.jpg)

> [!CAUTION]
>
> This library only works with the server software Minestom
> and any fork of them (no guaranty it everything's works with each fork).
> If you are trying to use it with Paper etc. it will fail due to the different architecture.

## Features

The Xerus library is designed specifically for creating games within a Minecraft environment. It offers a range of
features that simplify game development, including:

- **Kits**: Easily create and manage player kits, which can include items, abilities, and other attributes.
- **Teams**: Organize players into teams, enabling team-based gameplay and interactions.
- **Phase System**: Implement a flexible game state system that supports various gameplay phases, such as preparation,
  active play, and endgame.

## Internationalization (i18n) Support

Certain parts of the library support internationalization (i18n), allowing you to develop games playable in multiple
languages. This feature is crucial for reaching a global audience and improving the player experience.

If your project uses internationalization, it’s important to ensure that any checks in your code consider the language
context. Failing to do so may result in unexpected behavior or unintended side effects.

## Getting Started

The usage of Xerus requires the Minestom / Microtus dependencies are included in your project.
If that is the case, you can add the Xerus library to your project using the following dependency:
`net.theevilreaper.xerus:xerus:<version>`.
Replace `<version>` with the desired version of the library.
When you need a specific version, please check
the [Release Page](https://github.com/OneLiteFeatherNET/Xerus/releases)