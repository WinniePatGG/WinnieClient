package de.winniepat.winnieclient.gui.overlay.overlays;

import de.winniepat.winnieclient.gui.overlay.Overlay;
import de.winniepat.winnieclient.utils.RainbowColorShift;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

import java.util.Objects;

public class IpOverlay extends Overlay {

    public IpOverlay(double x, double y) {
        super("ip", x, y);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(DrawContext context, TextRenderer font) {
        String serverAddress;
        var serverEntry = MinecraftClient.getInstance().getCurrentServerEntry();
        
        if (serverEntry == null) {
            serverAddress = "SinglePlayer";
        } else if (serverEntry.address == null) {
            serverAddress = "Unknown Server";
        } else {
            serverAddress = serverEntry.address;
        }

        RainbowColorShift.rainbowColorShift(context, font, "Ip: " + serverAddress, getX(), getY(), 0L, 2.0f);
    }
}
