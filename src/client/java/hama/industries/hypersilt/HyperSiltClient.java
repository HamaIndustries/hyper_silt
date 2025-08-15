package hama.industries.hypersilt;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class HyperSiltClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientTickEvents.START_CLIENT_TICK.register(
                client -> {
                    if (client.world != null) HYPER.clean(client.world);
                }
        );
    }
}