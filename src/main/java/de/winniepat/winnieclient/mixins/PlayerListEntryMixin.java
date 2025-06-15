package de.winniepat.winnieclient.mixins;

import net.minecraft.client.network.PlayerListEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerListEntry.class)
public interface PlayerListEntryMixin {
    @Accessor("latency")
    int getLatency();
}
