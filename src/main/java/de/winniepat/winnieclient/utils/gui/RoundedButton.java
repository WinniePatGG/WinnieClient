package de.winniepat.winnieclient.utils.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.text.Text;
import org.joml.Matrix4f;

public class RoundedButton extends ClickableWidget {
    private final Runnable onClick;
    private final Color color;
    private final Color hoverColor;
    private boolean isHovered = false;
    private final SoundManager soundManager = new SoundManager(MinecraftClient.getInstance().options);

    public RoundedButton(int x, int y, int width, int height, Text message, Color color, Runnable onClick) {
        super(x, y, width, height, message);
        this.color = color;
        this.hoverColor = color.add(1.0F, 1.0F, 1.0F);
        this.onClick = onClick;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        isHovered = isMouseOver(mouseX, mouseY);
        Matrix4f matrix4f = context.getMatrices().peek().getPositionMatrix();
        drawRoundedBox(matrix4f, getX(), getY(), getWidth(), getHeight(), 2, isHovered ? hoverColor : color);

        int textX = getX() + getWidth() / 2;
        int textY = getY() + getHeight() / 2 - 4;
        context.drawCenteredTextWithShadow(MinecraftClient.getInstance().textRenderer, getMessage(), textX, textY, 0xFFFFFF);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            onClick.run();
            playDownSound(soundManager);
            return true;
        }
        return false;
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    public static void drawRoundedBox(Matrix4f matrix4f, float x, float y, float width, float height, float radius, Color color) {

        Render2D.drawRoundedBoxOutline(matrix4f, x, y, width, height, radius, color);

    }
}