function give(id, count){
	Player.inventory.addItem(id, count);
}
function color(r, g, b){
	GI.setConsoleTextColor(r, g, b);
}
function print(txt){
	GI.consolePrint(txt);
}
function saveWorld(worldName){
	World.save(worldName);
}
function screenshot(screenshotName){
	World.screenshot(screenshotName);
}
function setTime(time){
    World.WorldTime.setTime(time);
}
function clear(){
    Player.inventory.deleteItems();
}