package de.winniepat.winnieclient;

import de.winniepat.winnieclient.gui.overlay.OverlayManager;
import de.winniepat.winnieclient.gui.overlay.overlays.FPSOverlay;
import de.winniepat.winnieclient.module.ModuleManager;

public class Client {

    private final ModuleManager moduleManager;

    public Client() {
        this.moduleManager = new ModuleManager();
    }

    protected void init() {
        OverlayManager overlayManager = new OverlayManager();
        this.moduleManager.register(overlayManager);
        overlayManager.register(new FPSOverlay());
        overlayManager.setActive(FPSOverlay.class, true);
    }

    public ModuleManager modules() {
        return moduleManager;
    }

    public static Client instance() {
        return ClientEntrypoint.getClient();
    }
}
