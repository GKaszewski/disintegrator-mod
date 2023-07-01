package dev.gabrielkaszewski.disintegratormod.block.entity;

import dev.gabrielkaszewski.disintegratormod.item.inventory.ImplementedInventory;
import dev.gabrielkaszewski.disintegratormod.screen.DisintegratorScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.registry.Registries;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

public class DisintegratorBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    public static final int SLOTS_COUNT = 10;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(SLOTS_COUNT, ItemStack.EMPTY);

    public DisintegratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DISINTEGRATOR, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Disintegrator");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new DisintegratorScreenHandler(syncId, playerInventory, this);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
    }

    public static void tick(World world, BlockPos pos, BlockState state, DisintegratorBlockEntity entity) {
        if (hasRecipe(entity) && hasNotReachedStackLimit(entity)) {
            craftItem(entity);
        }
    }

    private static boolean hasRecipe(DisintegratorBlockEntity entity) {
        return !entity.getStack(0).isEmpty();
    }

    private static boolean hasNotReachedStackLimit(DisintegratorBlockEntity entity) {
        for (int i = 1; i < entity.getItems().size(); i++) {
            if (!entity.getStack(i).isEmpty() && entity.getStack(i).getCount() >= entity.getStack(i).getMaxCount()) {
                return false;
            }
        }
        return true;
    }

    private static void craftItem(DisintegratorBlockEntity entity) {
        ItemStack inputStack = entity.getStack(0);
        if (handleEnchantedBook(inputStack, entity)) return;
        if (handleEnchantedItem(inputStack, entity)) return;
        handleItem(inputStack, entity);
    }

    private static CraftingRecipe getRecipe(ItemStack inputStack, DisintegratorBlockEntity entity) {
        RecipeManager recipeManager = Objects.requireNonNull(entity.getWorld()).getRecipeManager();
        if (recipeManager == null) return null;
        Identifier inputStackId = Registries.ITEM.getId(inputStack.getItem());

        try {
            return (CraftingRecipe) recipeManager.get(inputStackId).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    private static void disintegrateItem(CraftingRecipe recipe, DisintegratorBlockEntity entity) {
        DefaultedList<ItemStack> ingredients = DefaultedList.ofSize(9, ItemStack.EMPTY);
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            Ingredient ingredient = recipe.getIngredients().get(i);
            ItemStack[] matchingStacks = ingredient.getMatchingStacks().clone();
            if (matchingStacks.length > 0) {
                ItemStack matchingStack = matchingStacks[0].copy();
                ingredients.set(i, matchingStack);
            }
        }

        decrementInput(entity);

        for (int i = 0; i < ingredients.size(); i++) {
            ItemStack ingredient = ingredients.get(i);
            if (ingredient.isEmpty()) continue;
            if (entity.getStack(i + 1).getItem() == ingredient.getItem()) {
                ItemStack stack = entity.getStack(i + 1);
                stack.setCount(stack.getCount() + 1);
                entity.setStack(i + 1, stack);
            } else {
                entity.setStack(i + 1, ingredient);
            }
        }
    }

    private static void decrementInput(DisintegratorBlockEntity entity) {
        ItemStack inputStack = entity.getStack(0);
        inputStack.setCount(inputStack.getCount() - 1);
        entity.setStack(0, inputStack);
    }

    private static boolean checkIfItemIsPlank(ItemStack itemStack) {
        return itemStack.getItem() == Items.OAK_PLANKS || itemStack.getItem() == Items.SPRUCE_PLANKS || itemStack.getItem() == Items.BIRCH_PLANKS || itemStack.getItem() == Items.JUNGLE_PLANKS || itemStack.getItem() == Items.ACACIA_PLANKS || itemStack.getItem() == Items.DARK_OAK_PLANKS || itemStack.getItem() == Items.CRIMSON_PLANKS || itemStack.getItem() == Items.WARPED_PLANKS;
    }

    private static boolean handleItem(ItemStack inputStack, DisintegratorBlockEntity entity) {
        if (checkIfItemIsPlank(inputStack)) return false;
        CraftingRecipe recipe = getRecipe(inputStack, entity);
        if (recipe == null) return false;
        disintegrateItem(recipe, entity);
        return true;
    }

    private static boolean handleEnchantedItem(ItemStack inputStack, DisintegratorBlockEntity entity) {
        if (inputStack.hasEnchantments()) {
            Map<Enchantment, Integer> enchantmentMap = EnchantmentHelper.get(inputStack);
            ItemStack outputStack = new ItemStack(Items.ENCHANTED_BOOK);
            EnchantmentHelper.set(enchantmentMap, outputStack);
            entity.setStack(1, outputStack);
            inputStack.removeSubNbt("Enchantments");
            return true;
        }

        return false;
    }

    private static boolean handleEnchantedBook(ItemStack inputStack, DisintegratorBlockEntity entity) {
        if (inputStack.getItem() == Items.ENCHANTED_BOOK) {
            entity.removeStack(0);
            ItemStack outputStack = new ItemStack(Items.BOOK);
            entity.setStack(1, outputStack);
            return true;
        }

        return false;
    }
}
