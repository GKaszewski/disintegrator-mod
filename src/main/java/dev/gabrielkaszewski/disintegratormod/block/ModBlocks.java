package dev.gabrielkaszewski.disintegratormod.block;

import dev.gabrielkaszewski.disintegratormod.DisintegratorMod;
import dev.gabrielkaszewski.disintegratormod.block.custom.DisintegratorBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block DISINTEGRATOR = registerBlock("disintegrator", new DisintegratorBlock(FabricBlockSettings.of(Material.STONE).strength(1.0f)), ItemGroups.REDSTONE);

    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registries.BLOCK, new Identifier(DisintegratorMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup itemGroup) {
        Item item = Registry.register(Registries.ITEM, new Identifier(DisintegratorMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> entries.add(item));
        return item;
    }

    public static void registerModBlocks() {
        DisintegratorMod.LOGGER.info("Registering mod blocks...");
    }
}
