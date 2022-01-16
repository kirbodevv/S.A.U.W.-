# Структура мода

В корневой директории мода должен быть файл `manifest.json`

## manifest.json

Пример `manifest.json`:

```json
{
  "package": "com.kgc.example_mod",
  "mod_format": 1,
  "authors": [
    "Kirbo"
  ],
  "header": {
    "name": "Mod name",
    "description": "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam"
  },
  "dependencies": [
    {
      "package": "com.kgc.example_dependence",
      "version": "0.0.1"
    }
  ]
}
```

### Обязательные ключи

* `package` - пакет мода
* `authors` - авторы мода
* `header.name` - название мода
* `header.description` - описание мода

### Необязательные ключи

* `dependencies` - зависимости мода

## config.json

`config.json` - это файл с настройками мода

```json5
{
  "enabled": true,
  "some_setting": 64,
  "some_setting_2": 123.345
}
```

P.S. с помощью `enabled`, можно включать / выключать мод. В случае отсутствия данного параметра, игра создаст его сама.

Поддерживаемые типы данных:

* `int`
* `float`
* `boolean`

## Директории

* `resources` - Ресурсы
* `localizations` - Локализация
* `items` - Предметы
* `blocks` - Блоки
* `achievements` - Достижения
* `scripts` - Скрипты