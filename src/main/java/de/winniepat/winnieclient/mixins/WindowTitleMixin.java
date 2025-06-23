package de.winniepat.winnieclient.mixins;

import de.winniepat.winnieclient.Client;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class WindowTitleMixin {

    @Inject(method = "getWindowTitle", at = @At("HEAD"), cancellable = true)
    private void injectCustomWindowTitle(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(Client.clientInfo);
    }
}
