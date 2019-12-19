package com.reliableplugins.oregenerator.nms.impl;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.nms.NMSHandler;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class Version_1_8_R3 implements NMSHandler {

    @Override
    public void setBlock(OreGenerator plugin, World world, int x, int y, int z, int id, byte data) {

        if (y > 255) return;

        net.minecraft.server.v1_8_R3.World w = ((CraftWorld) world).getHandle();
        Chunk chunk = w.getChunkAt(x >> 4, z >> 4);
        BlockPosition bp = new BlockPosition(x, y, z);

        IBlockData ibd = Block.getByCombinedId(id + (data << 12));

        ChunkSection chunksection = chunk.getSections()[bp.getY() >> 4];

        if (chunksection == null) {
            chunksection = chunk.getSections()[bp.getY() >> 4] = new ChunkSection(bp.getY() >> 4 << 4, !chunk.getWorld().worldProvider.o());
        }

        chunksection.setType(bp.getX() & 15, bp.getY() & 15, bp.getZ() & 15, ibd);

        plugin.getExecutorService().submit(() -> w.notify(bp));
    }

    @Override
    public String getItemName(ItemStack itemStack) {
        return CraftItemStack.asNMSCopy(itemStack).getName();
    }

    @Override
    public String getVersion() {
        return "v1_8_R3";
    }
}
