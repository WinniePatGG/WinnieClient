package de.winniepat.winnieclient.gui.overlay.overlays;

import de.winniepat.winnieclient.gui.overlay.Overlay;
import de.winniepat.winnieclient.utils.RainbowColorShift;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class FPSOverlay extends Overlay {

    public FPSOverlay(double x, double y) {
        super("fps", x, y);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(DrawContext context, TextRenderer font) {
        String fps = "FPS: " + MinecraftClient.getInstance().getCurrentFps();
        RainbowColorShift.rainbowColorShift(context, font, fps, getX(), getY(), 0L, 2.0f);
    }
}
