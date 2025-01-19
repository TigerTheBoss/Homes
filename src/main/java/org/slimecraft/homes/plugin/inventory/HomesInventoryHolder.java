package org.slimecraft.homes.plugin.inventory;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.entity.TeleportFlag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.slimecraft.homes.api.inventory.BedrockInventoryHolder;
import org.slimecraft.homes.api.inventory.button.Button;
import org.slimecraft.homes.api.model.Home;
import org.slimecraft.homes.api.model.User;

import java.time.Duration;
import java.util.List;

public class HomesInventoryHolder extends BedrockInventoryHolder {
    public HomesInventoryHolder() {
        super(27, Component.text("Homes"));

        final ItemStack createItem = ItemStack.of(Material.LIME_DYE);
        createItem.setData(DataComponentTypes.ITEM_NAME, Component.text("Create").color(NamedTextColor.GREEN));

        final ItemStack viewItem = ItemStack.of(Material.ENDER_EYE);
        viewItem.setData(DataComponentTypes.ITEM_NAME, Component.text("View").color(NamedTextColor.YELLOW));

        final Button createButton = new Button(10, createItem, (bedrockInventoryHolder, player) -> {
            final User user = User.from(player.getUniqueId());

            user.setCreatingHome(true);
            player.showTitle(Title.title(Component.text("Creating Home...").color(NamedTextColor.GRAY), Component.text("Choose a name.").color(NamedTextColor.YELLOW), Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(10), Duration.ofSeconds(1))));
            player.closeInventory();
        });

        createButton.setInteractable(false);

        final Button viewButton = new Button(12, viewItem, (bedrockInventoryHolder, player) -> {
            final User user = User.from(player.getUniqueId());

            player.openInventory(new HomesViewInventoryHolder(user.getHomes(), null).getInventory());
        });

        viewButton.setInteractable(false);

        this.addButton(createButton);
        this.addButton(viewButton);
    }

    private static final class HomesViewInventoryHolder extends BedrockInventoryHolder {
        public HomesViewInventoryHolder(List<Home> homes, HomesViewInventoryHolder previous) {
            super(27, Component.text("Homes -> View"));
            boolean previousCreated = false;

            for (int i = 0; i < homes.size(); i++) {
                if (i >= this.getInventory().getSize() - 1) {
                    final ItemStack nextPageItem = ItemStack.of(Material.ARROW);

                    nextPageItem.setData(DataComponentTypes.ITEM_NAME, Component.text("Next Page").color(NamedTextColor.YELLOW));

                    final List<Home> remainingHomes = homes.subList(i, homes.size());
                    final Button nextPageButton = new Button(i, nextPageItem, (bedrockInventoryHolder, player) -> {
                        player.openInventory(new HomesViewInventoryHolder(remainingHomes, this).getInventory());
                    });

                    nextPageButton.setInteractable(false);

                    this.addButton(nextPageButton);

                    return;
                }

                if (!previousCreated) {
                    if (previous != null) {
                        final ItemStack previousPageItem = ItemStack.of(Material.ARROW);

                        previousPageItem.setData(DataComponentTypes.ITEM_NAME, Component.text("Previous Page").color(NamedTextColor.YELLOW));

                        final Button previousPageButton = new Button(18, previousPageItem, (bedrockInventoryHolder, player) -> {
                            player.openInventory(previous.getInventory());
                        });

                        this.addButton(previousPageButton);
                        previousCreated = true;
                    }
                }

                final Home home = homes.get(i);
                final ItemStack homeItem = ItemStack.of(home.getSafeLocation().toRegularLocation().subtract(0, 1, 0).getBlock().getType());

                homeItem.setData(DataComponentTypes.ITEM_NAME, Component.text(home.getName()).color(NamedTextColor.GRAY));

                Button homeButton = new Button(i, homeItem, (bedrockInventoryHolder, player) -> {
                    player.teleport(home.getSafeLocation().toRegularLocation(), TeleportFlag.Relative.VELOCITY_ROTATION);
                });

                homeButton.setInteractable(false);

                if (i == 18 && previousCreated) {
                    homeButton.setSlot(i + 1);
                }

                this.addButton(homeButton);
            }
        }
    }
}
