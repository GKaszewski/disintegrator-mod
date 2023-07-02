package dev.gabrielkaszewski.disintegratormod;

import dev.gabrielkaszewski.disintegratormod.block.ModBlocks;
import dev.gabrielkaszewski.disintegratormod.block.entity.ModBlockEntities;
import dev.gabrielkaszewski.disintegratormod.screen.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DisintegratorMod implements ModInitializer {
	public static final String MOD_ID = "disintegratormod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerAllBlockEntities();
		ModScreenHandlers.registerModScreenHandlers();

	}
}