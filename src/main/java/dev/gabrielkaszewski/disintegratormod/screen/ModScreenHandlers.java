package dev.gabrielkaszewski.disintegratormod.screen;

import dev.gabrielkaszewski.disintegratormod.DisintegratorMod;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static ScreenHandlerType<DisintegratorScreenHandler> DISINTEGRATOR_SCREEN_HANDLER;

    public static void registerModScreenHandlers() {
        DisintegratorMod.LOGGER.info("Registering mod screen handlers...");
        DISINTEGRATOR_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(DisintegratorMod.MOD_ID, "disintegrator"), DisintegratorScreenHandler::new);
    }
}
