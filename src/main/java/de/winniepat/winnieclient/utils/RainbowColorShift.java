package de.winniepat.winnieclient.utils;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;

public class RainbowColorShift {

    public static void rainbowColorShift(DrawContext context, TextRenderer font, String text, int x, int y, long offset, float speed) {
        float time = (System.currentTimeMillis() + offset) % (int)(speed * 1000) / (speed * 1000f);

        int currentX = x;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            float hue = (time - (i * 0.05f)) % 1.0f;
            if (hue < 0) hue += 1.0f;
            int color = Color.HSBtoRGB(hue, 1.0f, 1.0f);

            context.drawText(font, String.valueOf(c), currentX, y, color, true);
            currentX += font.getWidth(String.valueOf(c));
        }
    }
}
