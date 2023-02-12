package com.HiWord9.ReconnectButton.mixin;

import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public abstract class RBMixin extends Screen {

    protected RBMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("RETURN"), method = "initWidgets")
    private void addReconnectButton(CallbackInfo ci) {

        boolean bl = this.client.isInSingleplayer();
        boolean bl2 = this.client.isConnectedToRealms();

        if (!bl && !bl2) {
            Text text = Text.of("R");
            this.addDrawableChild(new ButtonWidget(this.width / 2 - 102 + 208, this.height / 4 + 120 + -16, 20, 20, text, (button) -> {
                ServerInfo CurrentServer = this.client.getCurrentServerEntry();
                ServerAddress ServerIp = ServerAddress.parse(CurrentServer.address);
                System.out.println(ServerIp);

                button.active = false;
                this.client.world.disconnect();
                this.client.disconnect();

                ConnectScreen.connect(null, client, ServerIp, CurrentServer);
            }));
        }
    }
}
