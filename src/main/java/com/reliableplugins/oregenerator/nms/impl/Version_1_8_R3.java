package com.reliableplugins.oregenerator.nms.impl;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.nms.NMSHandler;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Version_1_8_R3 implements NMSHandler {

    @Override
    public void setBlock(OreGenerator plugin, World world, int x, int y, int z, Material material) {
        if (y > 255) return;
        net.minecraft.server.v1_8_R3.World w = ((CraftWorld) world).getHandle();
        Chunk chunk = w.getChunkAt(x >> 4, z >> 4);
        BlockPosition bp = new BlockPosition(x, y, z);
        ChunkSection chunksection = chunk.getSections()[bp.getY() >> 4];
        chunksection.setType(bp.getX() & 15, bp.getY() & 15, bp.getZ() & 15, Block.getByCombinedId(material.getId()));
        plugin.getExecutorService().submit(() -> w.notify(bp));
    }

    @Override
    public String getItemName(ItemStack itemStack) {
        return CraftItemStack.asNMSCopy(itemStack).getName();
    }

    @Override
    public void breakBlock(org.bukkit.block.Block block, ItemStack itemStack, Player player) {
        net.minecraft.server.v1_8_R3.Block nmsBlock = CraftMagicNumbers.getBlock(block);
        net.minecraft.server.v1_8_R3.EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();

        entityPlayer.b(StatisticList.MINE_BLOCK_COUNT[nmsBlock.getId(nmsBlock)]);
        entityPlayer.applyExhaustion(0.025f);

        Item itemType = (itemStack != null) ? Item.getById(itemStack.getTypeId()) : null;
        if (nmsBlock.getMaterial().isAlwaysDestroyable() || (itemType != null && itemType.canDestroySpecialBlock(nmsBlock))) {
            if (nmsBlock.d() && !nmsBlock.isTileEntity() && itemStack.getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {
                int i = 0;
                Item item = Item.getItemOf(nmsBlock);
                if (item != null && item.k()) {
                    i = nmsBlock.toLegacyData(nmsBlock.getBlockData());
                }
                nmsBlock.a(((CraftWorld) block.getWorld()).getHandle(), new BlockPosition(block.getX(), block.getY(), block.getZ()), new net.minecraft.server.v1_8_R3.ItemStack(item, 1, i));
            } else {
                int level = itemStack.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
                nmsBlock.dropNaturally(((CraftWorld) block.getWorld()).getHandle(), new BlockPosition(block.getX(), block.getY(), block.getZ()), nmsBlock.getBlockData(), 1.0f, level);
                //TODO: drop exp
            }
        }
    }

    @Override
    public String getVersion() {
        return "v1_8_R3";
    }
}
