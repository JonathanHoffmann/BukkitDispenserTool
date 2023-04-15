package me.jonnyfant.bukkitdispensertool;

import org.bukkit.Axis;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dispenser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DispenserListener implements Listener {
    private static final ArrayList<Material> AXES = new ArrayList<Material>() {
        {
            add(Material.WOODEN_AXE);
            add(Material.GOLDEN_AXE);
            add(Material.STONE_AXE);
            add(Material.IRON_AXE);
            add(Material.DIAMOND_AXE);
            add(Material.NETHERITE_AXE);
        }
    };

    public static final Map<Material, Material> AXEABLE = new HashMap<Material, Material>() {
        {
            put(Material.JUNGLE_LOG, Material.STRIPPED_JUNGLE_LOG);
            put(Material.OAK_LOG, Material.STRIPPED_OAK_LOG);
            put(Material.DARK_OAK_LOG, Material.STRIPPED_DARK_OAK_LOG);
            put(Material.MANGROVE_LOG, Material.STRIPPED_MANGROVE_LOG);
            put(Material.SPRUCE_LOG, Material.STRIPPED_SPRUCE_LOG);
            put(Material.ACACIA_LOG, Material.STRIPPED_ACACIA_LOG);
            put(Material.BIRCH_LOG, Material.STRIPPED_BIRCH_LOG);
            put(Material.CHERRY_LOG, Material.STRIPPED_CHERRY_LOG);
            put(Material.WARPED_HYPHAE, Material.STRIPPED_WARPED_HYPHAE);
            put(Material.CRIMSON_HYPHAE, Material.STRIPPED_CRIMSON_HYPHAE);
            put(Material.BAMBOO_BLOCK, Material.STRIPPED_BAMBOO_BLOCK);
            put(Material.JUNGLE_WOOD, Material.STRIPPED_JUNGLE_WOOD);
            put(Material.OAK_WOOD, Material.STRIPPED_OAK_WOOD);
            put(Material.DARK_OAK_WOOD, Material.STRIPPED_DARK_OAK_WOOD);
            put(Material.MANGROVE_WOOD, Material.STRIPPED_MANGROVE_WOOD);
            put(Material.SPRUCE_WOOD, Material.STRIPPED_SPRUCE_WOOD);
            put(Material.ACACIA_WOOD, Material.STRIPPED_ACACIA_WOOD);
            put(Material.BIRCH_WOOD, Material.STRIPPED_BIRCH_WOOD);
            put(Material.CHERRY_WOOD, Material.STRIPPED_CHERRY_WOOD);
            put(Material.WARPED_STEM, Material.STRIPPED_WARPED_STEM);
            put(Material.CRIMSON_STEM, Material.STRIPPED_CRIMSON_STEM);
        }
    };

    private static final ArrayList<Material> HOES = new ArrayList<Material>() {
        {
            add(Material.WOODEN_HOE);
            add(Material.GOLDEN_HOE);
            add(Material.STONE_HOE);
            add(Material.IRON_HOE);
            add(Material.DIAMOND_HOE);
            add(Material.NETHERITE_HOE);
        }
    };

    public static final Map<Material, Material> HOEABLE = new HashMap<Material, Material>() {
        {
            put(Material.GRASS_BLOCK, Material.FARMLAND);
            put(Material.DIRT, Material.FARMLAND);
            put(Material.COARSE_DIRT, Material.DIRT);
            put(Material.ROOTED_DIRT, Material.DIRT);
        }
    };

    private static final ArrayList<Material> SHOVELS = new ArrayList<Material>() {
        {
            add(Material.WOODEN_SHOVEL);
            add(Material.GOLDEN_SHOVEL);
            add(Material.STONE_SHOVEL);
            add(Material.IRON_SHOVEL);
            add(Material.DIAMOND_SHOVEL);
            add(Material.NETHERITE_SHOVEL);
        }
    };
    public static final Map<Material, Material> SHOVELABLE = new HashMap<Material, Material>() {
        {
            put(Material.GRASS_BLOCK, Material.DIRT_PATH);
            put(Material.DIRT, Material.DIRT_PATH);
            put(Material.COARSE_DIRT, Material.DIRT_PATH);
            put(Material.ROOTED_DIRT, Material.DIRT_PATH);
            put(Material.PODZOL, Material.DIRT_PATH);
            put(Material.MYCELIUM, Material.DIRT_PATH);
        }
    };

    private static final ArrayList<Material> REQUIRE_AIR_ABOVE = new ArrayList<Material>() {
        {
            add(Material.DIRT_PATH);
            add(Material.FARMLAND);
        }
    };

    @EventHandler
    public void onBlockDispense(BlockDispenseEvent event) {
        ItemStack tool = event.getItem();
        Dispenser d = (Dispenser) event.getBlock().getState().getData();
        Block target = event.getBlock().getRelative(d.getFacing());
        if (AXES.contains(tool.getType()) && AXEABLE.containsKey(target.getType())) {
            rightClickBlock(target, AXEABLE);
            event.setCancelled(true);
        }
        if (HOES.contains(tool.getType()) && HOEABLE.containsKey(target.getType())) {
            rightClickBlock(target, HOEABLE);
            event.setCancelled(true);
        }
        if (SHOVELS.contains(tool.getType()) && SHOVELABLE.containsKey(target.getType())) {
            rightClickBlock(target, SHOVELABLE);
            event.setCancelled(true);
        }
    }

    public void rightClickBlock(Block b, Map<Material, Material> map) {
        //case rooted dirt
        if (b.getType().equals(Material.ROOTED_DIRT))
            b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.HANGING_ROOTS, 1));
        Orientable oldData = null;
        if (b.getBlockData() instanceof Orientable)
            oldData = ((Orientable) b.getBlockData());

        if (REQUIRE_AIR_ABOVE.contains(map.get(b.getType()))) {
            return;
        }
        b.setType(map.get(b.getType()));
        if (b.getBlockData() instanceof Orientable) {
            Orientable newData = (Orientable) b.getBlockData();
            newData.setAxis(oldData.getAxis());
            b.setBlockData(newData);
        }
    }
}
