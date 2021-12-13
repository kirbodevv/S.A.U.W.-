function check(){
    var x = Player.getCurrentTileX();
    var z = Player.getCurrentTileY();
    if(Environment.getWorld().map.getTile(x, 0, z).id == ID.get("sauw", "block:tree")) return true;
    return false;
}