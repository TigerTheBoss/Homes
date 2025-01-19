package org.slimecraft.homes.api.inventory;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.slimecraft.homes.api.inventory.button.Button;

import java.util.ArrayList;
import java.util.List;

public class BedrockInventoryHolder implements InventoryHolder {
    private final Inventory inventory;
    private final List<Button> buttons;

    public BedrockInventoryHolder(int size, Component title) {
        this.inventory = Bukkit.createInventory(this, size, title);
        this.buttons = new ArrayList<>();
    }

    public BedrockInventoryHolder(InventoryType inventoryType, Component title) {
        this.inventory = Bukkit.createInventory(this, inventoryType, title);
        this.buttons = new ArrayList<>();
    }

    public void render() {
        for (Button button : this.buttons) {
            this.inventory.setItem(button.getSlot(), button.getItemStack());
        }
    }

    public void addButton(Button button) {
        this.buttons.add(button);
    }

    public void removeButton(Button button) {
        this.buttons.remove(button);
    }

    public List<Button> getButtons() {
        return buttons;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    public static final class Builder {
        private final BedrockInventoryHolder bedrockInventoryHolder;

        public Builder(int size, Component title) {
            this.bedrockInventoryHolder = new BedrockInventoryHolder(size, title);
        }

        public Builder(InventoryType inventoryType, Component title) {
            this.bedrockInventoryHolder = new BedrockInventoryHolder(inventoryType, title);
        }

        public Builder button(Button button) {
            this.bedrockInventoryHolder.addButton(button);
            return this;
        }
    }
}
