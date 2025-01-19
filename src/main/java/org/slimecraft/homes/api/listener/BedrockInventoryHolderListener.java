package org.slimecraft.homes.api.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.slimecraft.homes.api.inventory.BedrockInventoryHolder;
import org.slimecraft.homes.api.inventory.button.Button;

public class BedrockInventoryHolderListener implements Listener {

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!(event.getInventory().getHolder(false) instanceof final BedrockInventoryHolder bedrockInventoryHolder)) return;

        bedrockInventoryHolder.render();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder(false) instanceof final BedrockInventoryHolder bedrockInventoryHolder)) return;

        for (Button button : bedrockInventoryHolder.getButtons()) {
            if (button.getSlot() == event.getRawSlot()) {
                if (!button.isInteractable()) {
                    event.setCancelled(true);
                }

                final Player player = (Player) event.getWhoClicked();

                button.activate(bedrockInventoryHolder, player);
            }
        }
    }
}
