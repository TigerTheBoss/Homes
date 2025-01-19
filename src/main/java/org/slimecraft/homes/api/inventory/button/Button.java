package org.slimecraft.homes.api.inventory.button;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.slimecraft.homes.api.inventory.BedrockInventoryHolder;

import java.util.function.BiConsumer;

public class Button {
    private int slot;
    private ItemStack itemStack;
    private BiConsumer<BedrockInventoryHolder, Player> onActivated;
    private boolean interactable;

    public Button(int slot, ItemStack itemStack, BiConsumer<BedrockInventoryHolder, Player> onActivated) {
        this.slot = slot;
        this.itemStack = itemStack;
        this.onActivated = onActivated;
    }

    public void activate(BedrockInventoryHolder bedrockInventoryHolder, Player player) {
        this.onActivated.accept(bedrockInventoryHolder, player);
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public BiConsumer<BedrockInventoryHolder, Player> getOnActivated() {
        return onActivated;
    }

    public void setOnActivated(BiConsumer<BedrockInventoryHolder, Player> onActivated) {
        this.onActivated = onActivated;
    }

    public boolean isInteractable() {
        return interactable;
    }

    public void setInteractable(boolean interactable) {
        this.interactable = interactable;
    }
}
