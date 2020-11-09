package com.KGC.SAUW;

import android.os.Environment;
import com.KGC.SAUW.mods;
import com.badlogic.gdx.Gdx;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import com.KGC.SAUW.ModAPI.ModAPI;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONObject;
import org.json.JSONArray;

public class mods {
	Context cx;
	Scriptable mods[] = null;

    public void load(player pl, blocks BLOCKS, items ITEMS, ModAPI ModAPI, crafting crafting, settings settings, gameInterface GI, Textures T) {
		cx = Context.enter();
		cx.setOptimizationLevel(-1);
		try {
			String[] names;
			File t = new File(Environment.getExternalStorageDirectory().toString() + "/S.A.U.W./Mods");
			names = t.list();
			if (names != null) {
				mods = new Scriptable[names.length];
				for (int i = 0; i < names.length; i++) {
					/*FileReader FR1 = new FileReader(new File(t.toString() + "/" + names[i] + "manifest.json"));
					Scanner scanner1 = new Scanner(FR1);
					String result1 = "";
					while (scanner1.hasNext()) {
						result1 += scanner1.nextLine();
					}
					mods[i] = new mod(result1, t.toString() + names[i]);
					
					try {
						File crafts = new File(mods[i].modPath + mods[i].manifest.getString("crafts_path"));
						String[] craftss = crafts.list();
						for (String file : craftss) {
							File craft = new File(crafts + file);
							if (craft.isFile()) {
                                FileReader FRr = new FileReader(craft);
								Scanner scannerr = new Scanner(FRr);
								String resultt = "";
								while(scannerr.hasNext()){
									resultt += scannerr.nextLine();
								}
								JSONObject craftObj = new JSONObject(resultt);
								JSONObject resultCraft = craftObj.getJSONObject("result");
								JSONArray ingr = craftObj.getJSONArray("ingredients");
								int[][] ingrd = new int[ingr.length()][3];
								for(int j = 0; j < ingr.length(); j++){
									ingrd[j][0] = ingr.getJSONObject(i).getInt("id");
									ingrd[j][1] = ingr.getJSONObject(i).getInt("count");
									ingrd[j][2] = 0;
								}
								crafting.addCraft(new int[]{resultCraft.getInt("id"), resultCraft.getInt("count"), 0}, ingrd);
							}
						}
					} catch (Exception e) {

					}*/
					mods[i] = cx.initStandardObjects();
					FileReader FR = new FileReader(new File(t.toString() + "/" + names[i] + "/main.js"));
					Scanner scanner = new Scanner(FR);
					String result = "";
					
					ScriptableObject.putProperty(mods[i], "Player", pl);
					ScriptableObject.putProperty(mods[i], "Blocks", BLOCKS);
					ScriptableObject.putProperty(mods[i], "Items", ITEMS);
					ScriptableObject.putProperty(mods[i], "ModAPI", ModAPI);
					ScriptableObject.putProperty(mods[i], "Settings", settings);
					ScriptableObject.putProperty(mods[i], "GI", GI);
					ScriptableObject.putProperty(mods[i], "Textures", T);
					while (scanner.hasNext()) {
						result += scanner.nextLine();
					}
					cx.evaluateString(mods[i], result, names[i], 1, null);
				}
			}
		} catch (Exception e) {
			Gdx.app.log("error", e.toString());
		} finally {
			Context.exit();
		}
		new ModTick().run();
	}
	public void HookFunction(String functionName, Object args[]) {
		cx = Context.enter();
		//Gdx.app.log("cx:", cx.toString());
		cx.setOptimizationLevel(-1);
		try {
			for (Scriptable mod : mods) {
				Object fObj = mod.get(functionName, mod);
				if ((fObj instanceof Function)) {
					Function f = (Function)fObj;
					Object result = f.call(cx, mod, mod, args);
					//Gdx.app.log("log", result.toString());
				}
			}
		} catch (Exception e) {
			Gdx.app.log("error", e.toString());
		} finally {
			Context.exit();
		}
	}
	class ModTick extends Thread {
		Timer timer = new Timer();
		Object[] obj = new Object[]{};
		@Override
		public void run() {
			super.run();
			timer.schedule(new TimerTask(){
					@Override
					public void run() {
						HookFunction("tick", obj);
					}
				}, 0, 50);

		}
	}
	public class mod {
		public Scriptable sc;
		public String modPath;
		public JSONObject manifest;
		public mod(String manifest, String ModPath) {
			try {
				this.manifest = new JSONObject(manifest);
			} catch (Exception e) {

			}
			this.modPath = ModPath;
		}
	}
}
