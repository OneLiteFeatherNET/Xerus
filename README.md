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

To use **Xerus**, you must first have [**Minestom**](https://github.com/Minestom/Minestom) set up in your project.  
Once that's done, add the following dependency to your `build.gradle.kts` file:

```kotlin
dependencies {
    implementation("net.theevilreaper:xerus:1.0.0")
}
```

Other releases can be found on the [Release Page](https://github.com/OneLiteFeatherNET/Xerus/releases)