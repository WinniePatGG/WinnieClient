package de.winniepat.winnieclient.module;

import net.minecraft.client.gui.DrawContext;

public interface Module {

    default void start() { }

    default void stop() { }

    default void tick() { }

    default void render(DrawContext context) { }

}
