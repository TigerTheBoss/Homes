package org.slimecraft.homes.plugin.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.slimecraft.homes.api.model.Home;
import org.slimecraft.homes.api.model.SafeLocation;
import org.slimecraft.homes.api.model.User;

import java.time.Duration;
import java.util.UUID;

public class PlayerListener implements Listener {
    private final BukkitScheduler scheduler;
    private final Plugin plugin;

    public PlayerListener(BukkitScheduler scheduler, Plugin plugin) {
        this.scheduler = scheduler;
        this.plugin = plugin;
    }

    @EventHandler
    public void onAsyncChat(AsyncChatEvent event) {
        final Player player = event.getPlayer();
        final User user = User.from(player.getUniqueId());

        if (!user.isCreatingHome()) return;

        event.setCancelled(true);

        final String name = PlainTextComponentSerializer.plainText().serialize(event.message());

        for (Home home : user.getHomes()) {
            if (home.getName().equalsIgnoreCase(name)) {
                player.showTitle(Title.title(Component.text("Couldn't Create Home!").color(NamedTextColor.RED), Component.text("Home of name \"" + name + "\" already exists. Try another name.").color(NamedTextColor.YELLOW), Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3), Duration.ofSeconds(1))));
                this.scheduler.runTaskLater(this.plugin, task -> {
                    player.showTitle(Title.title(Component.text("Creating Home...").color(NamedTextColor.GRAY), Component.text("Choose a name.").color(NamedTextColor.YELLOW), Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(10), Duration.ofSeconds(1))));
                }, 4 * 20L);
                return;
            }
        }

        final Home home = new Home(UUID.randomUUID());

        home.setName(name);
        home.setSafeLocation(new SafeLocation(player.getWorld().getUID(), player.getX(), player.getY(), player.getZ(), player.getPitch(), player.getYaw()));

        user.addHome(home);

        user.setCreatingHome(false);
        player.showTitle(Title.title(Component.text("Created Home!").color(NamedTextColor.GREEN), Component.text("Home of name \"" + name + "\" was created.").color(NamedTextColor.YELLOW), Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3), Duration.ofSeconds(1))));
    }
}
