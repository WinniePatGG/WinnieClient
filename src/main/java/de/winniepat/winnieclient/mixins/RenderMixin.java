package de.winniepat.winnieclient.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class RenderMixin implements AutoCloseable{

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;<init>(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;)V",
                    shift = At.Shift.BY,
                    by = 2
            )
    )
    public void render(RenderTickCounter counter, boolean tick, CallbackInfo ci, @Local DrawContext context) {
        System.out.println("Render " + context);
    }
}
