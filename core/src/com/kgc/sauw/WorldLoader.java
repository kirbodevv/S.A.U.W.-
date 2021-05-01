package com.kgc.sauw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.intbyte.bdb.DataBuffer;
import com.intbyte.bdb.ExtraData;
import com.kgc.sauw.map.Tile;

import java.util.Date;
import java.util.List;

import static com.kgc.sauw.entity.Entities.ENTITIES;
import static com.kgc.sauw.map.World.MAPS;
import static com.kgc.sauw.map.World.WORLD;
import static com.kgc.sauw.entity.Entities.PLAYER;

public class WorldLoader {
    public static void save(String WorldName) {
        WORLD.setWorldName(WorldName);
        FileHandle worldFolder = Gdx.files.external("S.A.U.W./Worlds/" + WorldName);
        FileHandle map = Gdx.files.external(worldFolder + "/map");

        if (!worldFolder.exists()) worldFolder.mkdirs();
        if (!map.exists()) map.mkdirs();

        try {

            FileHandle mapFile = Gdx.files.external("S.A.U.W./Worlds/" + WorldName + "/map/map.bdb");
            FileHandle worldData = Gdx.files.external("S.A.U.W./Worlds/" + WorldName + "/world.bdb");
            FileHandle playerFile = Gdx.files.external("S.A.U.W./Worlds/" + WorldName + "/player.bdb");
            FileHandle mobsFile = Gdx.files.external("S.A.U.W./Worlds/" + WorldName + "/mobs.bdb");

            if (!mapFile.exists()) mapFile.file().createNewFile();
            if (!worldData.exists()) worldData.file().createNewFile();
            if (!playerFile.exists()) playerFile.file().createNewFile();
            if (!mapFile.exists()) mobsFile.file().createNewFile();

            DataBuffer playerBuffer = new DataBuffer();
            DataBuffer worldDataBuffer = new DataBuffer();
            playerBuffer.put("player", PLAYER);
            worldDataBuffer.put("time", WORLD.WorldTime.getTime());
            Date date = new Date();
            worldDataBuffer.put("saveTime", date.getTime());

            playerFile.writeBytes(playerBuffer.toBytes(), false);
            mobsFile.writeBytes(ENTITIES.getBytes(), false);
            worldData.writeBytes(worldDataBuffer.toBytes(), false);
            mapFile.writeBytes(MAPS.toDataBuffer().toBytes(), false);
        } catch (Exception e) {
            Gdx.app.log("saveError", e.toString());
        }
    }

    public static void load(String WorldName) {
        WORLD.setWorldName(WorldName);
        FileHandle worldFolder = Gdx.files.external("/S.A.U.W./Worlds/" + WorldName);
        if (worldFolder.exists()) {
            FileHandle mapFile = Gdx.files.external("S.A.U.W./Worlds/" + WorldName + "/map/map.bdb");
            FileHandle worldData = Gdx.files.external("S.A.U.W./Worlds/" + WorldName + "/world.bdb");
            FileHandle playerFile = Gdx.files.external("S.A.U.W./Worlds/" + WorldName + "/player.bdb");
            FileHandle mobsFile = Gdx.files.external("S.A.U.W./Worlds/" + WorldName + "/mobs.bdb");
            if (mapFile.exists()) {
                DataBuffer buffer = new DataBuffer();
                buffer.readBytes(mapFile.readBytes());
                int[] map = buffer.getIntArray("mapIds");
                int[] mapDmg = buffer.getIntArray("mapDamage");
                Tile.TileEntityFactory TEF = new Tile.TileEntityFactory();
                List<? extends ExtraData> tileEntitys = null;
                if (buffer.getInt("tileEnCount") > 0) {
                    tileEntitys = buffer.getExtraDataList("tileEntitys", TEF);
                }
                int i = 0;
                for (int y = 0; y < MAPS.map0.length; y++) {
                    for (int x = 0; x < MAPS.map0[y].length; x++) {
                        for (int z = 0; z < MAPS.map0[y][x].length; z++) {
                            if (buffer.getInt("tileEnCount") > 0) {
                                for (ExtraData tileEntity : tileEntitys) {
                                    Tile tile = (Tile) tileEntity;
                                    if (x == tile.x && y == tile.y && z == tile.z) {
                                        WORLD.setBlock(tile);
                                    }
                                }
                            }
                            if (MAPS.map0[y][x][z] == null)
                                WORLD.setBlock(x, y, z, map[i]);
                            MAPS.map0[y][x][z].damage = mapDmg[i];
                            i++;
                        }
                    }
                }
                buffer.readBytes(playerFile.readBytes());
                if (PLAYER != null)
                    PLAYER.readBytes(buffer.getByteArray("player"), 0, buffer.getByteArray("player").length);
                byte[] mobsBytes = mobsFile.readBytes();
                if (ENTITIES != null) ENTITIES.readBytes(mobsBytes, 0, mobsBytes.length);
                buffer.readBytes(worldData.readBytes());
                if (WORLD.WorldTime != null) WORLD.WorldTime.setTime(buffer.getInt("time"));
            }
        }
    }
}
