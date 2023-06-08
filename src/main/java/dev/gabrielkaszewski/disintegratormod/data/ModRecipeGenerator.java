package dev.gabrielkaszewski.disintegratormod.data;

import dev.gabrielkaszewski.disintegratormod.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricDataOutput fabricDataOutput) {
        super(fabricDataOutput);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.DISINTEGRATOR)
                .pattern("###")
                .pattern("TDT")
                .pattern("###")
                .input('#', Items.SMOOTH_STONE)
                .input('T', Items.TNT)
                .input('D', Items.DIAMOND)
                .criterion(FabricRecipeProvider.hasItem(Items.SMOOTH_STONE),
                        FabricRecipeProvider.conditionsFromItem(Items.SMOOTH_STONE))
                .criterion(FabricRecipeProvider.hasItem(Items.DIAMOND),
                        FabricRecipeProvider.conditionsFromItem(Items.DIAMOND))
                .criterion(FabricRecipeProvider.hasItem(Items.TNT),
                        FabricRecipeProvider.conditionsFromItem(Items.TNT))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModBlocks.DISINTEGRATOR)));
    }
}
