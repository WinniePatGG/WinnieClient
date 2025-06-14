package de.winniepat.winnieclient.gui.overlay;

import de.winniepat.winnieclient.module.Module;
import de.winniepat.winnieclient.utils.Registry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class OverlayManager extends Registry<Overlay> implements Module {
    public void setActive(Overlay overlay, boolean active) {
        setActive(overlay.getClass(), active);
    }

    public void setActive(Class<? extends Overlay> overlay, boolean active) {
        get(overlay).setActive(active);
    }

    public void toggleActive(Overlay overlay) {
        toggleActive(overlay.getClass());
    }

    public void toggleActive(Class<? extends Overlay> overlay) {
        get(overlay).toggleActive();
    }

    public boolean isActive(Overlay overlay) {
        return isActive(overlay.getClass());
    }

    public boolean isActive(Class<? extends Overlay> overlay) {
        return get(overlay).isActive();
    }

    public Overlay get(Class<? extends Overlay> overlay) {
        return entries().stream().filter(overlay::isInstance).findFirst().orElseThrow();
    }

    @Override
    public void tick() {
        entries().forEach(overlay -> {
            if (overlay.isActive())
                overlay.tick();
        });
    }

    @Override
    public void render(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!client.options.hudHidden)
            entries().forEach(overlay -> {
                if (overlay.isActive() && (overlay.shouldRenderInGUI() || client.world != null))
                    overlay.render(context, client.textRenderer);
            });
    }
}
