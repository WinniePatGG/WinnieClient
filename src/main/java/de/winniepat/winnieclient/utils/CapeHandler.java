package de.winniepat.winnieclient.utils;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.util.Identifier;

public class CapeHandler {

    public static SkinTextures winnieclientTexture;
    public static Identifier capeTexture;

    public static SkinTextures getCapes(SkinTextures original, AbstractClientPlayerEntity player){

        capeTexture = Identifier.of("winnieclient", "textures/capes/cape.png");

        winnieclientTexture = new SkinTextures(
                original.texture(),
                original.textureUrl(),
                capeTexture,
                capeTexture,
                original.model(),
                original.secure()
        );

        return winnieclientTexture;
    }

}