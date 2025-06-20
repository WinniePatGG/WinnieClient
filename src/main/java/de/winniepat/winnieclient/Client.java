package de.winniepat.winnieclient;

import de.winniepat.winnieclient.backend.LoginChallenge;
import de.winniepat.winnieclient.gui.overlay.OverlayManager;
import de.winniepat.winnieclient.gui.overlay.overlays.FPSOverlay;
import de.winniepat.winnieclient.gui.overlay.overlays.PingOverlay;
import de.winniepat.winnieclient.module.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.net.http.HttpClient;
import java.time.Duration;

public class Client {

    public static String clientInfo = "WinnieClient 1.21.4-0.0.1 (20250614) | Minecraft 1.21.4";
    private final ModuleManager moduleManager;
    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10)).build();

    public Client() {
        this.moduleManager = new ModuleManager();
    }

    protected void init() {
        OverlayManager overlayManager = new OverlayManager();
        this.moduleManager.register(overlayManager);

        overlayManager.register(new FPSOverlay());
        overlayManager.setActive(FPSOverlay.class, true);

        overlayManager.register(new PingOverlay());
        overlayManager.setActive(PingOverlay.class, true);

        LoginChallenge loginChallenge = new LoginChallenge(client);
        loginChallenge.verifyLoginOrLogin();
    }

    public ModuleManager modules() {
        return moduleManager;
    }

    public static Client instance() {
        return ClientEntrypoint.getClient();
    }

    public void sendInfo(String message) {
        MinecraftClient.getInstance().execute(() -> {
            MinecraftClient.getInstance().inGameHud.getChatHud()
                    .addMessage(Text.literal("ℹ " + message).formatted(Formatting.GRAY));
        });
    }

    public void sendSuccess(String message) {
        MinecraftClient.getInstance().execute(() -> {
            MinecraftClient.getInstance().inGameHud.getChatHud()
                    .addMessage(Text.literal("✅ " + message).formatted(Formatting.GREEN));
        });
    }

    public void sendError(String message) {
        MinecraftClient.getInstance().execute(() -> {
            MinecraftClient.getInstance().inGameHud.getChatHud()
                    .addMessage(Text.literal("❌ " + message).formatted(Formatting.RED));
        });
    }
}
