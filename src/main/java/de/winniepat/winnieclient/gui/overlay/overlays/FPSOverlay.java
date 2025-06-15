package de.winniepat.winnieclient.gui.overlay.overlays;

import de.winniepat.winnieclient.gui.overlay.Overlay;
import de.winniepat.winnieclient.utils.RainbowColorShift;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class FPSOverlay extends Overlay {

    public FPSOverlay() {
        super("fps");
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(DrawContext context, TextRenderer font) {
        String fps = "FPS: " + MinecraftClient.getInstance().getCurrentFps();
        RainbowColorShift.rainbowColorShift(context, font, fps, 0, 0, 0L, 2.0f);
    }
}
