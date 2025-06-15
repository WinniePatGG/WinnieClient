package de.winniepat.winnieclient.gui.overlay.overlays;

import de.winniepat.winnieclient.gui.overlay.Overlay;
import de.winniepat.winnieclient.mixins.PlayerListEntryMixin;
import de.winniepat.winnieclient.utils.RainbowColorShift;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;

public class PingOverlay extends Overlay {
    public PingOverlay() {
        super("Ping");
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(DrawContext context, TextRenderer font) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.getNetworkHandler() != null && client.player != null) {
            PlayerListEntry entry = client.getNetworkHandler().getPlayerListEntry(client.player.getUuid());

            if (entry instanceof PlayerListEntryMixin accessor) {
                int ping = accessor.getLatency();
                String text = "Ping: " + ping + "ms";
                RainbowColorShift.rainbowColorShift(context, font, text, 0, 0, 0L, 2.0f);
            }
        }
    }

    public boolean shouldRenderInGui() {
        return false;
    }

    public int getRenderOffsetY() {
        return 4;
    }
}
