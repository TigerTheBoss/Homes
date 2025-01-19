package org.slimecraft.homes.api.model;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.UUID;

public record SafeLocation(UUID worldIdentifier, double x, double y, double z, float pitch, float yaw) {
    public Location toRegularLocation() {
        return new Location(Bukkit.getWorld(worldIdentifier), x, y, z, pitch, yaw);
    }
}
