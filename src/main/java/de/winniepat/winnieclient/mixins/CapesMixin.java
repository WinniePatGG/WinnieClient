package de.winniepat.winnieclient.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import de.winniepat.winnieclient.utils.CapeHandler;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.SkinTextures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class CapesMixin {
    @Shadow
    private PlayerListEntry playerListEntry;

    @ModifyReturnValue(method = "getSkinTextures", at = @At("RETURN"))
    public SkinTextures handelCapes(SkinTextures original){
        if (playerListEntry == null) {
            DefaultSkinHelper.getSkinTextures(((AbstractClientPlayerEntity) (Object) this).getUuid());
        }else {
            return CapeHandler.getCapes(original, ((AbstractClientPlayerEntity) (Object) this));
        }
        return original;
    }
}