package com.pikycz.mobplugin.entities.spawners.monster;

import cn.nukkit.IPlayer;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.level.generator.biome.Biome;
import cn.nukkit.utils.Config;
import com.pikycz.mobplugin.FileLogger;
import com.pikycz.mobplugin.entities.autospawn.AbstractEntitySpawner;
import com.pikycz.mobplugin.entities.autospawn.SpawnResult;
import com.pikycz.mobplugin.entities.monster.flying.Blaze;
import com.pikycz.mobplugin.task.AutoSpawnTask;
import com.pikycz.mobplugin.entities.utils.Utils;

/**
 *
 * @author PikyCZ
 */
public class BlazeSpawner extends AbstractEntitySpawner {

    public BlazeSpawner(AutoSpawnTask spawnTask, Config pluginConfig) {
        super(spawnTask, pluginConfig);
    }

    @Override
    protected String getLogprefix() {
        return this.getClass().getSimpleName();
    }

    @Override
    public SpawnResult spawn(IPlayer iPlayer, Position pos, Level level) {
        SpawnResult result = SpawnResult.OK;

        if (Utils.rand(0, 3) > 0) {
            return SpawnResult.SPAWN_DENIED;
        }

        int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);
        int blockLightLevel = level.getBlockLightAt((int) pos.x, (int) pos.y, (int) pos.z);
        int biomeId = level.getBiomeId((int) pos.x, (int) pos.z);

        if (biomeId == Biome.HELL) {
            result = SpawnResult.WRONG_BLOCK;
        } else {
            this.spawnTask.createEntity(getEntityName(), pos.add(0, 1.9, 0));
        }

        FileLogger.info(String.format("[%s] spawn for %s at %s,%s,%s with lightlevel %s and blockId %s, result: %s", getLogprefix(), iPlayer.getName(), pos.x, pos.y, pos.z, blockLightLevel, blockId, result));

        return result;
    }

    @Override
    public int getEntityNetworkId() {
        return Blaze.NETWORK_ID;
    }

    @Override
    public String getEntityName() {
        return "Blaze";
    }

}