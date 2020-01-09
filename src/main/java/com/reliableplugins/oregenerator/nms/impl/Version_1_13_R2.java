package com.reliableplugins.oregenerator.nms.impl;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.nms.NMSHandler;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_13_R2.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Version_1_13_R2 implements NMSHandler {

    @Override
    public void setBlock(OreGenerator plugin, World world, int x, int y, int z,  Material material) {

    }

    @Override
    public void breakBlock(Block block, Player player) {
        net.minecraft.server.v1_13_R2.Block nmsBlock = CraftMagicNumbers.getBlock(block.getType());
        net.minecraft.server.v1_13_R2.EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        net.minecraft.server.v1_13_R2.World world = ((CraftWorld) block.getWorld()).getHandle();

        if (player.getGameMode() == GameMode.CREATIVE) return;

        Item itemType = entityPlayer.inventory.getItemInHand().getItem();

        if (nmsBlock.n(nmsBlock.getBlockData()).isAlwaysDestroyable() || (itemType != null && itemType.canDestroySpecialBlock(nmsBlock.getBlockData()))) {

            net.minecraft.server.v1_13_R2.ItemStack mainItem = entityPlayer.getEquipment(EnumItemSlot.MAINHAND);

            if (itemType.usesDurability()) mainItem.damage(1, entityPlayer);

            BlockPosition blockPosition = new BlockPosition(block.getX(), block.getY(), block.getZ());

            nmsBlock.a(world, entityPlayer, blockPosition, nmsBlock.getBlockData(), null, mainItem);
            if (nmsBlock != null && !nmsBlock.isTileEntity() && !(EnchantmentManager.getEnchantmentLevel(Enchantments.SILK_TOUCH, mainItem) > 0)) {
                int expDrop = nmsBlock.getExpDrop(nmsBlock.getBlockData(), world, blockPosition, EnchantmentManager.getEnchantmentLevel(Enchantments.LOOT_BONUS_BLOCKS, mainItem));
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
        return "v1_13_R2";
    }

}

