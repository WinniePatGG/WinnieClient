package de.winniepat.winnieclient;

import de.winniepat.winnieclient.module.ModuleManager;

public class Client {

    private final ModuleManager moduleManager;

    public Client() {
        this.moduleManager = new ModuleManager();
    }

    protected void init() {
    }

    public ModuleManager modules() {
        return moduleManager;
    }

    public static Client instance() {
        return ClientEntrypoint.getClient();
    }
}
