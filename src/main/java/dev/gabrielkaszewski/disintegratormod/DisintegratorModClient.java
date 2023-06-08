package dev.gabrielkaszewski.disintegratormod;

import dev.gabrielkaszewski.disintegratormod.screen.DisintegratorScreen;
import dev.gabrielkaszewski.disintegratormod.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class DisintegratorModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.DISINTEGRATOR_SCREEN_HANDLER, DisintegratorScreen::new);
    }
}
