function give(id, count, data){
	Player.addItem(id, count, data);
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
