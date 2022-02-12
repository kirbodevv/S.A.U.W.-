# Registry

In S.A.U.W. all game objects must be registered.

In each "storage" of objects there is a static `registry` field.

To register:

```java
Items.registry.register(new SomeItemClass(),"sauw","id");
```

Or if object has `@RegistryMetadata`:

```java
Items.registry.register(new SomeItem());
```

## RegistryMetadata

RegistryMetadata is annotation for the lazy :D

```java
@RegistryMetadata(package_ = "sauw", id = "ice")
public class SomeItem extends Item {
    //...
}
```

### List of game registries:

* [Items](../../core/src/com/kgc/sauw/core/item/Items.java)
* [Blocks](../../core/src/com/kgc/sauw/core/block/Blocks.java)
* [Achievements](../../core/src/com/kgc/sauw/core/achievements/Achievements.java)
* [CreativeCategories](../../core/src/com/kgc/sauw/core/creative_categories/CreativeCategories.java)
* [Interfaces](../../core/src/com/kgc/sauw/core/gui/Interfaces.java)
* [Recipes](../../core/src/com/kgc/sauw/core/recipes/Recipes.java)