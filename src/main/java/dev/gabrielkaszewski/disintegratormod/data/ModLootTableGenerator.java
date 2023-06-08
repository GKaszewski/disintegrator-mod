package dev.gabrielkaszewski.disintegratormod.data;

import dev.gabrielkaszewski.disintegratormod.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class ModLootTableGenerator extends FabricBlockLootTableProvider {

    public ModLootTableGenerator(FabricDataOutput fabricDataOutput) {
        super(fabricDataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.DISINTEGRATOR);
    }
}
