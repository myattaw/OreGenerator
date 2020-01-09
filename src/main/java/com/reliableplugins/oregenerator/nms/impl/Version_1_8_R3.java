package com.reliableplugins.oregenerator.nms.impl;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.nms.NMSHandler;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
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
    public void breakBlock(org.bukkit.block.Block block, Player player) {

        net.minecraft.server.v1_8_R3.Block nmsBlock = CraftMagicNumbers.getBlock(block);
        net.minecraft.server.v1_8_R3.EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        net.minecraft.server.v1_8_R3.World world = ((CraftWorld) block.getWorld()).getHandle();

        if (player.getGameMode() == GameMode.CREATIVE) return;

        Item itemType = entityPlayer.inventory.getItemInHand().getItem();

        if (nmsBlock.getMaterial().isAlwaysDestroyable() || (itemType != null && itemType.canDestroySpecialBlock(nmsBlock))) {

            if (itemType.usesDurability()) entityPlayer.bZ().damage(1, entityPlayer);

            nmsBlock.a(world, entityPlayer, new BlockPosition(block.getX(), block.getY(), block.getZ()), nmsBlock.getBlockData(), null);
            if (nmsBlock != null && !nmsBlock.isTileEntity() && !EnchantmentManager.hasSilkTouchEnchantment(entityPlayer)) {
                int expDrop = nmsBlock.getExpDrop(world, nmsBlock.getBlockData(), EnchantmentManager.getBonusBlockLootEnchantmentLevel(entityPlayer));
                if (expDrop != 0) {
                    for (int i = expDrop; i > 0; i--) {
                        world.addEntity(new EntityExperienceOrb(world, block.getX() + 0.5, block.getY() + 0.5, block.getZ() + 0.5, EntityExperienceOrb.getOrbValue(i)));
                    }
                }
            }
        }

    }

    @Override
    public String getVersion() {
        return "v1_8_R3";
    }
}
