package dev.gabrielkaszewski.disintegratormod.screen;

import dev.gabrielkaszewski.disintegratormod.DisintegratorMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static ScreenHandlerType<DisintegratorScreenHandler> DISINTEGRATOR_SCREEN_HANDLER = Registry.register(
            Registries.SCREEN_HANDLER,
            new Identifier(DisintegratorMod.MOD_ID, "disintegrator"),
            new ScreenHandlerType<>(DisintegratorScreenHandler::new, null)
    );
}
