package de.winniepat.winnieclient.mixins;

import de.winniepat.winnieclient.utils.gui.Color;
import de.winniepat.winnieclient.utils.gui.RoundedButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerWarningScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    public TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void addButtons(CallbackInfo ci) {

        int l = this.height / 4 + 48;

        RoundedButton singleplayer = new RoundedButton(this.width / 2 - 100, l, 200, 20, Text.literal("Singleplayer"), new Color(21, 205, 212), () -> {
            assert this.client != null;
            this.client.setScreen(new SelectWorldScreen(this));
        });

        RoundedButton multiplayer = new RoundedButton(this.width / 2 - 100, l + 24, 200, 20, Text.literal("Multiplayer"), new Color(21, 205, 212), () -> {
            assert this.client != null;
            Screen screen = this.client.options.skipMultiplayerWarning ? new MultiplayerScreen(this) : new MultiplayerWarningScreen(this);
            this.client.setScreen(screen);
        });

        RoundedButton options = new RoundedButton(this.width / 2 - 100, l + 48, 100, 20, Text.literal("Options"), new Color(21, 205, 212), () -> {
            this.client.setScreen(new OptionsScreen(this, this.client.options));
        });

        RoundedButton quit = new RoundedButton(this.width / 2, l + 48, 100, 20, Text.literal("Quit Game"), new Color(21, 205, 212), () -> {
                    assert this.client != null;
                    this.client.scheduleStop();
        });

        clearChildren();
        blur();

        this.addDrawableChild(singleplayer);
        this.addDrawableChild(multiplayer);
        this.addDrawableChild(options);
        this.addDrawableChild(quit);
    }
}