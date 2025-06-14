package de.winniepat.winnieclient.gui.overlay;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.NotNull;

public abstract class Overlay {

    private final String name;

    private boolean active = false;
    private double x;
    private double y;

    public Overlay(@NotNull String name) {
        this.name = name;

        this.x = 10;
        this.y = 10;
    }

    public abstract void tick();

    public abstract void render(DrawContext context, TextRenderer font);

    public boolean shouldRenderInGUI() {
        return false;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void toggleActive() {
        this.setActive(!this.isActive());
    }

    public boolean isActive() {
        return active;
    }

    public int getX() {
        return (int) Math.round(x);
    }

    public int getY() {
        return (int) Math.round(y);
    }
}
