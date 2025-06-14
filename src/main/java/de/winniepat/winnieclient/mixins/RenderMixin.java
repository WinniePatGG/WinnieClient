package de.winniepat.winnieclient.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import de.winniepat.winnieclient.Client;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.FrameGraphBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class RenderMixin implements AutoCloseable{
   FrameGraphBuilder.Profiler profiler = FrameGraphBuilder.Profiler.NONE;

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;render(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V"
            )
    )

    public void render(RenderTickCounter counter, boolean tick, CallbackInfo ci, @Local DrawContext context) {
        profiler.push("client");
        Client.instance().modules().render(context);
        profiler.pop("client");
    }
}
