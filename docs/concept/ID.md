# ID's

ID's in S.A.U.W. looks like this: `$type@$namespace:$id`

### Parameters

* `$type` - is type of registered item, for example: `block`, `item`, `achievement`, etc.
* `$namespace` - is namespace of registered item, for example the S.A.U.W. namespace in is `sauw`
* `$id` - the item ID itself, for example `dirt`, `glass`

P.S. the namespace is needed so that there are no name conflicts

### examples:

* `block@sauw:dirt`
* `item@sauw:torch`
* `command@sauw:echo`

The full ID, it is only used in GUI. The game stores all IDs in different objects

