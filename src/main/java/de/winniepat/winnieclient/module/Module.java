package de.winniepat.winnieclient.module;

import net.minecraft.client.gui.DrawContext;

public abstract class Module {

    protected abstract void start();

    protected abstract void stop();

    protected abstract void tick();

    protected abstract void render(DrawContext context);

}
