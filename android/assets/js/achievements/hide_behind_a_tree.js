function check(){
    var x = Player.getCurrentTileX();
    var z = Player.getCurrentTileZ();
    if(Environment.getWorld().map.getTile(x, 0, z).id == ID.get("sauw", "block:tree")) return true;
    return false;
}