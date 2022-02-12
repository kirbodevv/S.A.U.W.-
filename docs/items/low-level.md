# Low-Level item

To create item you need to create class extends [`Item`](../../core/src/com/kgc/sauw/core/item/Item.java)

```java
package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;

public class SomeItem extends Item {
    public SomeItem() {
        //set item texture
        setTexture(Resource.getTexture("item/item_texture.png"));
        itemConfiguration.weight = 0.25f;
    }
}
```

This code will create a simple item with weight `0.25kg` texture from [`assets`](../../assets) dir.

Now you need to register this item.

### Registration

You need to use [`@RegistryMetadata`](../utils/Registry.md)

Then you need to add item to end of [items.list](../../core/json/items/items.list)

```
//another items
$com.kgc.sauw.game.items.MyItem
```

You should use `$` + `item_package`
