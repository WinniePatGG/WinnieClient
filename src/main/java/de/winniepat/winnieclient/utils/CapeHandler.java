package de.winniepat.winnieclient.utils;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.util.Identifier;

public class CapeHandler {

    public static SkinTextures texture;
    public static Identifier capeTexture;

    public static SkinTextures getCapes(SkinTextures original, AbstractClientPlayerEntity player){
        capeTexture = Identifier.of("winnieclient", "textures/capes/cape.png");
        texture = new SkinTextures(original.texture(), original.textureUrl(), capeTexture, capeTexture, original.model(), original.secure());
        return texture;
    }

}