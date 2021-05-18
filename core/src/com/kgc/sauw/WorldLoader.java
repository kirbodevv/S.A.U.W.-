package com.kgc.sauw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.intbyte.bdb.DataBuffer;
import com.intbyte.bdb.ExtraData;
import com.kgc.sauw.map.Maps;
import com.kgc.sauw.map.Tile;

import java.util.Date;
import java.util.List;

import static com.kgc.sauw.entity.EntityManager.ENTITY_MANAGER;
import static com.kgc.sauw.entity.EntityManager.PLAYER;
import static com.kgc.sauw.map.World.MAPS;
import static com.kgc.sauw.map.World.WORLD;

public class WorldLoader {
    public static void save(String WorldName) {
        WORLD.setWorldName(WorldName);
        FileHandle worldFolder = Gdx.files.external("S.A.U.W./Worlds/" + WorldName);

        if (!worldFolder.exists()) worldFolder.mkdirs();

        try {
            FileHandle mapFile = Gdx.files.external("S.A.U.W./Worlds/" + WorldName + "/map.bdb");
            FileHandle worldData = Gdx.files.external("S.A.U.W./Worlds/" + WorldName + "/worldData.bdb");
            FileHandle playerFile = Gdx.files.external("S.A.U.W./Worlds/" + WorldName + "/player.bdb");
            FileHandle entitiesFile = Gdx.files.external("S.A.U.W./Worlds/" + WorldName + "/entities.bdb");

            if (!mapFile.exists()) mapFile.file().createNewFile();
            if (!worldData.exists()) worldData.file().createNewFile();
            if (!playerFile.exists()) playerFile.file().createNewFile();
            if (!mapFile.exists()) entitiesFile.file().createNewFile();

            DataBuffer playerBuffer = new DataBuffer();
            DataBuffer worldDataBuffer = new DataBuffer();

            //Сохранение игрока
            playerBuffer.put("player", PLAYER);
            //Сохранение данных мира
            worldDataBuffer.put("time", WORLD.WorldTime.getTime());
            Date date = new Date();
            worldDataBuffer.put("saveTime", date.getTime());

            playerFile.writeBytes(playerBuffer.toBytes(), false);
            entitiesFile.writeBytes(ENTITY_MANAGER.getBytes(), false);
            worldData.writeBytes(worldDataBuffer.toBytes(), false);
            mapFile.writeBytes(MAPS.toDataBuffer().toBytes(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load(String WorldName) {
        WORLD.setWorldName(WorldName);
        FileHandle worldFolder = Gdx.files.external("/S.A.U.W./Worlds/" + WorldName);
        if (worldFolder.exists()) {
            FileHandle mapFile = Gdx.files.external("S.A.U.W./Worlds/" + WorldName + "/map.bdb");
            FileHandle worldData = Gdx.files.external("S.A.U.W./Worlds/" + WorldName + "/worldData.bdb");
            FileHandle playerFile = Gdx.files.external("S.A.U.W./Worlds/" + WorldName + "/player.bdb");
            FileHandle entitiesFile = Gdx.files.external("S.A.U.W./Worlds/" + WorldName + "/entities.bdb");
            if (mapFile.exists()) {
                DataBuffer buffer = new DataBuffer();
                buffer.readBytes(mapFile.readBytes());
                int[] map = buffer.getIntArray("mapIds");
                int[] mapDmg = buffer.getIntArray("mapDamage");
                Tile.TileEntityFactory TEF = new Tile.TileEntityFactory();
                List<? extends ExtraData> tileEntities = null;
                if (buffer.getInt("tileEnCount") > 0) {
                    tileEntities = buffer.getExtraDataList("tileEntities", TEF);
                }
                int i = 0;
                for (int x = 0; x < Maps.xSize; x++) {
                    for (int y = 0; y < Maps.ySize; y++) {
                        for (int z = 0; z < Maps.zSize; z++) {
                            if (buffer.getInt("tileEnCount") > 0) {
                                for (ExtraData tileEntity : tileEntities) {
                                    Tile tile = (Tile) tileEntity;
                                    if (x == tile.x && y == tile.y && z == tile.z) {
                                        WORLD.setBlock(tile);
                                    }
                                }
                            }
                            if (MAPS.getTile(x, y, z) == null)
                                WORLD.setBlock(x, y, z, map[i]);
                            MAPS.getTile(x, y, z).damage = mapDmg[i];
                            i++;
                        }
                    }
                }
                buffer.readBytes(playerFile.readBytes());
                if (PLAYER != null)
                    PLAYER.readBytes(buffer.getByteArray("player"), 0, buffer.getByteArray("player").length);
                byte[] mobsBytes = entitiesFile.readBytes();
                if (ENTITY_MANAGER != null) ENTITY_MANAGER.readBytes(mobsBytes, 0, mobsBytes.length);
                buffer.readBytes(worldData.readBytes());
                if (WORLD.WorldTime != null) WORLD.WorldTime.setTime(buffer.getInt("time"));
            }
        }
    }
}