package com.reliableplugins.oregenerator.nms.impl;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.nms.NMSHandler;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_14_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Version_1_14_R1 implements NMSHandler {

    @Override
    public void setBlock(OreGenerator plugin, World world, int x, int y, int z,  Material material) {
        if (y > 255) return;

        net.minecraft.server.v1_14_R1.World w = ((CraftWorld) world).getHandle();
        Chunk chunk = w.getChunkAt(x >> 4, z >> 4);
        BlockPosition bp = new BlockPosition(x, y, z);
        ChunkSection chunksection = chunk.getSections()[bp.getY() >> 4];

        IBlockData iBlockData = CraftMagicNumbers.getBlock(material).getBlockData();
        chunksection.setType(bp.getX() & 15, bp.getY() & 15, bp.getZ() & 15, iBlockData);

        plugin.getExecutorService().submit(() -> w.notify(bp, iBlockData, iBlockData, 2));
    }

    @Override
    public void breakBlock(Block block, Player player) {
        net.minecraft.server.v1_14_R1.Block nmsBlock = CraftMagicNumbers.getBlock(block.getType());
        net.minecraft.server.v1_14_R1.EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        net.minecraft.server.v1_14_R1.World world = ((CraftWorld) block.getWorld()).getHandle();

        if (player.getGameMode() == GameMode.CREATIVE) return;

        Item itemType = entityPlayer.inventory.getItemInHand().getItem();

        if (nmsBlock.l(nmsBlock.getBlockData()).isAlwaysDestroyable() || (itemType != null && itemType.canDestroySpecialBlock(nmsBlock.getBlockData()))) {

            net.minecraft.server.v1_14_R1.ItemStack mainItem = entityPlayer.getEquipment(EnumItemSlot.MAINHAND);

            if (itemType.usesDurability()) mainItem.damage(1, entityPlayer, entityPlayer1 -> entityPlayer.d(EnumHand.MAIN_HAND));

            BlockPosition blockPosition = new BlockPosition(block.getX(), block.getY(), block.getZ());

            nmsBlock.a(world, entityPlayer, blockPosition, nmsBlock.getBlockData(), null, mainItem);
            if (nmsBlock != null && !nmsBlock.isTileEntity() && !(EnchantmentManager.getEnchantmentLevel(Enchantments.SILK_TOUCH, mainItem) > 0)) {
                int expDrop = nmsBlock.getExpDrop(nmsBlock.getBlockData(), world, blockPosition, mainItem);
                if (expDrop != 0) {
                    for (int i = expDrop; i > 0; i--) {
                        world.addEntity(new EntityExperienceOrb(world, block.getX() + 0.5, block.getY() + 0.5, block.getZ() + 0.5, EntityExperienceOrb.getOrbValue(i)));
                    }
                }
            }
        }
    }

    @Override
    public String getItemName(ItemStack itemStack) {
        return CraftItemStack.asNMSCopy(itemStack).getName().getText();
    }


    @Override
    public String getVersion() {
        return "v1_14_R1";
    }

}
