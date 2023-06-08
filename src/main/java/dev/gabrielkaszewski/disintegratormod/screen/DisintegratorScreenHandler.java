package dev.gabrielkaszewski.disintegratormod.screen;

import dev.gabrielkaszewski.disintegratormod.block.entity.DisintegratorBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class DisintegratorScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    public DisintegratorScreenHandler(int syncId, PlayerInventory playerInventory) {
       this(syncId, playerInventory, new SimpleInventory(DisintegratorBlockEntity.SLOTS_COUNT));
    }

    public DisintegratorScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.DISINTEGRATOR_SCREEN_HANDLER, syncId);
        checkSize(inventory, DisintegratorBlockEntity.SLOTS_COUNT);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);

        this.addSlot(new Slot(inventory, 0, 27, 36));
        int gridX = 98;
        int gridY = 17;
        int slotIndex = 1;
        for (int row = 0; row < 3; row++) {
            gridX = 98;
            for (int col = 0; col < 3; col++) {
                this.addSlot(new Slot(inventory, slotIndex, gridX, gridY));
                gridX += 18;
                slotIndex++;
            }
            gridY += 18;
        }

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
