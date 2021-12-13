package com.kgc.sauw.core.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.intbyte.bdb.DataBuffer;
import com.intbyte.bdb.ExtraData;
import com.kgc.sauw.core.world.chunk.Chunk;

import java.util.Date;
import java.util.List;

import static com.kgc.sauw.core.entity.EntityManager.ENTITY_MANAGER;
import static com.kgc.sauw.core.entity.EntityManager.PLAYER;
import static com.kgc.sauw.core.environment.Environment.getWorld;

public class WorldLoader {

    public static String[] worldNames;

    public static void saveChunks(Map map, String worldName) {
        for (int x = 0; x < map.getChunks().length; x++) {
            for (int z = 0; z < map.getChunks()[x].length; z++) {
                Chunk chunk = map.getChunk(x, z);
                if (chunk.isChanged()) {
                    FileHandle chunkFile = Gdx.files.external("S.A.U.W./Worlds").child(worldName).child("map").child("c." + x + "." + z + ".bdb");
                    chunkFile.writeBytes(chunk.getBytes(), false);
                }
            }
        }
    }

    public static void updateWorldsList() {
        FileHandle worldsFolder = Gdx.files.external("S.A.U.W./Worlds/");
        FileHandle[] files = worldsFolder.list();
        int i = 0;
        for (FileHandle file : files) {
            if (file.isDirectory()) i++;
        }

        worldNames = new String[i];
        int ii = 0;
        for (FileHandle file : files) {
            if (file.isDirectory()) worldNames[ii] = file.name();
            ii++;
        }
    }

    public static void save(String worldName) {
    /*    FileHandle worldFolder = Gdx.files.external("S.A.U.W./Worlds").child(worldName);
        if (!worldFolder.exists()) worldFolder.mkdirs();
        try {
            FileHandle mapFile = worldFolder.child("map.bdb");
            FileHandle worldData = worldFolder.child("worldData.bdb");
            FileHandle playerFile = worldFolder.child("player.bdb");
            FileHandle entitiesFile = worldFolder.child("entities.bdb");

            DataBuffer playerBuffer = new DataBuffer();
            DataBuffer worldDataBuffer = new DataBuffer();

            //Сохранение игрока
            playerBuffer.put("player", PLAYER);
            PLAYER.achievementsData.save(worldFolder.child("receivedAchievements.list"));
            //Сохранение данных мира
            worldDataBuffer.put("time", getWorld().worldTime.getTime());
            Date date = new Date();
            worldDataBuffer.put("saveTime", date.getTime());

            playerFile.writeBytes(playerBuffer.toBytes(), false);
            entitiesFile.writeBytes(ENTITY_MANAGER.getBytes(), false);
            worldData.writeBytes(worldDataBuffer.toBytes(), false);
            mapFile.writeBytes(getWorld().map.toDataBuffer().toBytes(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public static void load(String WorldName) {
        FileHandle worldFolder = Gdx.files.external("/S.A.U.W./Worlds/" + WorldName);
        if (worldFolder.exists()) {
            FileHandle mapFile = worldFolder.child("map.bdb");
            FileHandle worldData = worldFolder.child("worldData.bdb");
            FileHandle playerFile = worldFolder.child("player.bdb");
            FileHandle entitiesFile = worldFolder.child("entities.bdb");

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
                for (int x = 0; x < Map.xSize; x++) {
                    for (int y = 0; y < Map.ySize; y++) {
                        for (int z = 0; z < Map.zSize; z++) {
                            if (buffer.getInt("tileEnCount") > 0) {
                                for (ExtraData tileEntity : tileEntities) {
                                    Tile tile = (Tile) tileEntity;
                                    if (x == tile.x && y == tile.y && z == tile.z) {
                                        getWorld().map.setBlock(tile);
                                    }
                                }
                            }
                            if (getWorld().map.getTile(x, y, z) == null)
                                getWorld().map.setBlock(x, y, z, map[i]);
                            getWorld().map.getTile(x, y, z).damage = mapDmg[i];
                            i++;
                        }
                    }
                }
                buffer.readBytes(playerFile.readBytes());
                PLAYER.readBytes(buffer.getByteArray("player"), 0, buffer.getByteArray("player").length);
                PLAYER.achievementsData.load(worldFolder.child("receivedAchievements.list"));
                byte[] mobsBytes = entitiesFile.readBytes();
                if (ENTITY_MANAGER != null) ENTITY_MANAGER.readBytes(mobsBytes, 0, mobsBytes.length);
                buffer.readBytes(worldData.readBytes());
                if (getWorld().worldTime != null) getWorld().worldTime.setTime(buffer.getInt("time"));
            }
        }
    }
}