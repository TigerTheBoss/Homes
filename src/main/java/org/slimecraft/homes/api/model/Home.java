package org.slimecraft.homes.api.model;

import java.util.UUID;

public class Home implements Identifiable {
    private final UUID identifier;
    private String name;
    private SafeLocation safeLocation;

    public Home(UUID identifier) {
        this.identifier = identifier;
    }

    @Override
    public UUID getIdentifier() {
        return this.identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SafeLocation getSafeLocation() {
        return safeLocation;
    }

    public void setSafeLocation(SafeLocation safeLocation) {
        this.safeLocation = safeLocation;
    }
}
