package de.winniepat.winnieclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class ClientEntrypoint implements ModInitializer, ClientModInitializer {

    private static Client client;

    @Override
    public void onInitialize() {
        System.out.println("Mod initialized");
        client = new Client();
    }

    @Override
    public void onInitializeClient() {
        System.out.println("Client initialized");
        client.init();
    }

    public static Client getClient() {
        return client;
    }
}
