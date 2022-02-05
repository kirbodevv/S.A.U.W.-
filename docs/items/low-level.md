# Low-Level item

To create item you need to create class extends [`Item`](../../core/src/com/kgc/sauw/core/item/Item.java)

```java
package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;

public class SomeItem extends Item {
    public SomeItem() {
        //set item texture
        setTexture(Resource.getTexture("items/item_texture.png"));
        itemConfiguration.weight = 0.25f;
    }
}
```