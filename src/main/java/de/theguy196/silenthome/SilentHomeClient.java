package de.theguy196.silenthome;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.network.chat.Component;

import java.util.Locale;

public final class SilentHomeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Block matching game/system messages before Minecraft displays them.
        ClientReceiveMessageEvents.ALLOW_GAME.register(
                (message, overlay) -> !isHomeMessage(message)
        );

        // Also block matching player-style chat messages.
        ClientReceiveMessageEvents.ALLOW_CHAT.register(
                (message, signedMessage, sender, chatType, timestamp) -> !isHomeMessage(message)
        );
    }

    private static boolean isHomeMessage(Component message) {
        String text = message.getString().toLowerCase(Locale.ROOT);

        // Deliberately broad: hide ANY incoming chat/game message containing
        // "home", rather than relying on a particular server confirmation phrase.
        // This catches wording changes such as "new home set", "home created",
        // "you have set a new home", etc.
        return text.contains("home") || text.contains("homes");
    }
}
