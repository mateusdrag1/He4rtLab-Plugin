package br.com.he4rtlabs.handlers;

import br.com.he4rtlabs.utils.Constants;
import br.com.he4rtlabs.utils.DiscordWebhook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;
import java.io.IOException;
import java.util.logging.Logger;

public class EventHandlers implements Listener {
    private Logger logger;

    public EventHandlers(Logger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        int playerCount = Bukkit.getOnlinePlayers().size();

        DiscordWebhook webhook = new DiscordWebhook(Constants.webhookURL);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setDescription("**" + player.getName() + "** has joined the server! (" + playerCount + "/" + Bukkit.getMaxPlayers() + ")")
                .setAuthor(player.getName(), "", "")
                .setColor(new Color(115, 21, 237))
        );

        try {
            webhook.execute();
        } catch (IOException e) {
            logger.severe(e.getStackTrace().toString());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        int playerCount = Bukkit.getOnlinePlayers().size() - 1;

        DiscordWebhook webhook = new DiscordWebhook(Constants.webhookURL);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setDescription("**" + player.getName() + "** has left the server.... (" + playerCount + "/" + Bukkit.getMaxPlayers() + ")")
                .setAuthor(player.getName(), "", "")
                .setColor(new Color(230, 7, 18))
        );

        try {
            webhook.execute();
        } catch (IOException e) {
            logger.severe(e.getStackTrace().toString());
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String eventMessage = event.getMessage();
        Player player = event.getPlayer();

        DiscordWebhook webhook = new DiscordWebhook(Constants.webhookURL);
        eventMessage = eventMessage.replaceAll("@everyone", "`@everyone`").replaceAll("@here", "`@here`");
        webhook.setContent("**" + player.getName() + "** >> " + eventMessage);

        try {
            webhook.execute();
        } catch (IOException e) {
            logger.severe(e.getStackTrace().toString());
        }
    }
}
