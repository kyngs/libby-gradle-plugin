# libby-gradle-plugin

A solution to easily download dependencies at runtime with Gradle and [Libby](https://github.com/AlessioDP/libby).

## How to use

### Adding the plugin

Firstly, in the `settings.gradle` file, add the plugin repository:

```groovy
pluginManagement {
    repositories {
        maven {
            url = uri("https://repo.kyngs.xyz/gradle-plugins")
        }
        gradlePluginPortal()
    }
}
```

Then, simply apply the plugin in the `build.gradle` file:

```groovy
plugins {
    id 'xyz.kyngs.libby.plugin' version '1.0.0'
}
```

(You can find the latest version in the releases)

### Declaring dependencies

Instead of using the `compileOnly` configuration, and then manually specifying the dependencies in the libby code, you
can simply replace `compileOnly` with `libby`:

```groovy
dependencies {
    libby 'com.zaxxer:HikariCP:5.0.1'
}
```

When running the libby task (which will be automatically run when building) the plugin will create a libby.json file
inside the final JAR. This file contains all the information about the dependencies (and their transient dependencies),
and their repositories.

### Linking with libby

The plugin itself only creates the `libby.json` which specifies what libraries should libby load. However, we need to tell
libby to load it.

Fortunately, we can do this easily by invoking the `LibraryManager.configureFromJSON()` method. You can find an example
at the end of the README.

### Further configuration

#### Relocating

Relocation is an extremely important part when bundling libraries in plugins (or using libby). Fortunately for us, this
can be simply achieved.

Firstly, we need to add the `shadow` plugin:

```groovy
plugins {
    id 'com.github.johnrengelman.shadow' version '8.1.1'
    id 'xyz.kyngs.libby.plugin' version '1.0.0'
}
```

Then, we need to add the relocation rules:

```groovy
shadowJar {
    relocate 'com.zaxxer.hikari', 'com.example.hikari'
}
```

This will relocate all the classes in the `com.zaxxer.hikari` package to `com.example.hikari`. You can find more info
about relocation in
the [shadow plugin documentation](https://imperceptiblethoughts.com/shadow/configuration/relocation/).

**If you use the relocation feature, you must build your plugin using the shadowJar task!**

#### Excluding dependencies

The plugin automatically resolves all transient dependencies. While this is a useful feature, sometimes it downloads
completely unnecessary dependencies.

For example, if you use the `com.zaxxer:HikariCP:version` dependency, it will
download `org.slf4j:slf4j-api:version`.

While this does make sense for a standalone application, plugin platforms like
bukkit etc. already bundle in slf4j, so downloading it is not only unnecessary, but it can also cause conflicts.

Fortunately, this can be easily solved by excluding the dependency:

```groovy
libby {
    exludeDependency 'org.slf4j:.*:.*'
}
```

So, what did we just do? The `excludeDependency` method takes a regular expression as an argument. This regular
expression is then compared against all dependencies ids (groupId:artifactId:version) and if it matches, the dependency
is
excluded from being downloaded by libby.
In our example we used the regular expression `org.slf4j:.*:.*`. This means that we want to exclude all dependencies from the `org.slf4j` group.

## Examples

### A demonstration example

TBD

### A practical example

TBD