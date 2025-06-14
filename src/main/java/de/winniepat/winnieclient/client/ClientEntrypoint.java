package de.winniepat.winnieclient.client;

import net.fabricmc.api.ClientModInitializer;

public class ClientEntrypoint implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        System.out.println("Client initialized");
    }
}
