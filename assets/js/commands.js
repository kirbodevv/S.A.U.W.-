function give(id, count){
	give("sauw", id, count);
}
function give(package, id, count){
	Player.inventory.addItem(package, id, count);
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
function exit(){
    com.badlogic.gdx.Gdx.app.exit();
}