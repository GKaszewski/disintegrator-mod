package dev.gabrielkaszewski.disintegratormod.block.entity;

import dev.gabrielkaszewski.disintegratormod.DisintegratorMod;
import dev.gabrielkaszewski.disintegratormod.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static BlockEntityType<DisintegratorBlockEntity> DISINTEGRATOR;

    public static void registerAllBlockEntities() {
        DISINTEGRATOR = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(DisintegratorMod.MOD_ID, "disintegrator"),
                FabricBlockEntityTypeBuilder.create(DisintegratorBlockEntity::new, ModBlocks.DISINTEGRATOR).build(null));
    }
}
