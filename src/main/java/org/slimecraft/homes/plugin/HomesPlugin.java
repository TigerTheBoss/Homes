package org.slimecraft.homes.plugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.paper.util.sender.PaperSimpleSenderMapper;
import org.incendo.cloud.paper.util.sender.Source;
import org.slimecraft.homes.api.listener.BedrockInventoryHolderListener;
import org.slimecraft.homes.plugin.command.HomesCommand;
import org.slimecraft.homes.plugin.listener.PlayerListener;

public class HomesPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        final PaperCommandManager<Source> commandManager = PaperCommandManager.builder(PaperSimpleSenderMapper.simpleSenderMapper())
                .executionCoordinator(ExecutionCoordinator.simpleCoordinator())
                .buildOnEnable(this);

        new HomesCommand(commandManager);
        this.getServer().getPluginManager().registerEvents(new BedrockInventoryHolderListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerListener(this.getServer().getScheduler(), this), this);
    }
}
