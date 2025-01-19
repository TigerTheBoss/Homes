package org.slimecraft.homes.plugin.command;

import org.bukkit.entity.Player;
import org.incendo.cloud.Command;
import org.incendo.cloud.key.CloudKey;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.paper.util.sender.PlayerSource;
import org.incendo.cloud.paper.util.sender.Source;
import org.incendo.cloud.parser.standard.StringParser;
import org.slimecraft.homes.api.inventory.BedrockInventoryHolder;
import org.slimecraft.homes.plugin.command.parser.HomeParser;
import org.slimecraft.homes.api.model.Home;
import org.slimecraft.homes.api.model.SafeLocation;
import org.slimecraft.homes.api.model.User;
import org.slimecraft.homes.plugin.inventory.HomesInventoryHolder;

import java.util.UUID;

public class HomesCommand {

    public HomesCommand(PaperCommandManager<Source> commandManager) {
        final Command.Builder<Source> homes = commandManager.commandBuilder("homes");

        final Command.Builder<PlayerSource> homesView = homes
                .senderType(PlayerSource.class)
                .handler(commandContext -> {
                   final Player player = commandContext.sender().source();
                   final BedrockInventoryHolder homesInventoryHolder = new HomesInventoryHolder();

                   player.openInventory(homesInventoryHolder.getInventory());
                });

        commandManager.command(homesView);
    }
}
