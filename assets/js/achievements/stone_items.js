function check(){
    if (Player.inventory.getCountOfItems("item:stone_axe") > 0 ||
        Player.inventory.getCountOfItems("item:stone_pickaxe") > 0 ||
        Player.inventory.getCountOfItems("item:stone_shovel") > 0)
        return true;
    return false;
}