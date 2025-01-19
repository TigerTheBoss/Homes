package org.slimecraft.homes.api.model;

import java.util.*;

public class User implements Identifiable {
    private static final Map<UUID, User> USERS = new HashMap<>();
    private final UUID identifier;
    private final List<Home> homes;
    private boolean creatingHome;

    private User(UUID identifier) {
        this.identifier = identifier;
        homes = new ArrayList<>();
    }

    public static User from(UUID identifier) {
        return USERS.computeIfAbsent(identifier, User::new);
    }

    public void addHome(Home home) {
        this.homes.add(home);
    }

    public void removeHome(Home home) {
        this.homes.remove(home);
    }

    public List<Home> getHomes() {
        return homes;
    }

    public boolean isCreatingHome() {
        return creatingHome;
    }

    public void setCreatingHome(boolean creatingHome) {
        this.creatingHome = creatingHome;
    }

    @Override
    public UUID getIdentifier() {
        return this.identifier;
    }
}
