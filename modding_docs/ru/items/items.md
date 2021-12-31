# Предметы

Для добавления своих предметов в игру необходимо создать файл с расширением `.item.json` в директории `items`

Пример кода предмета:

```json
{
  "id": "id",
  "icon": "$mod_package_item_texture",
  "configuration": {
    "max_count": 64,
    "weight": 1,
    "type": "ITEM"
  }
}
```

* `id` - Айди предмета
* `icon` - Иконка предмета
* `configuration.max_count` - Максимальное количество предметов в слоте
* `configuration.weight` - Вес предмета (в kg)
* `configuration.type` - Тип предмета

## Типы предметов

Кроме `ITEM` есть еще 3 типа предметов

* [INSTRUMENT](instrument.md)
* [BLOCK_ITEM](block_item.md)
* [FOOD](food.md)
