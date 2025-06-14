package de.winniepat.winnieclient.gui.overlay.overlays;

import de.winniepat.winnieclient.gui.overlay.Overlay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;

public class FPSOverlay extends Overlay {

    public FPSOverlay() {
        super("fps");
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(DrawContext context, TextRenderer font) {
        context.drawText(font, "FPS: " + MinecraftClient.getInstance().getCurrentFps(), getX(), getY(), Color.ORANGE.getRGB(), true);
    }
}
