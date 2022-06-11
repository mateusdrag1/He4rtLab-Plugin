package br.com.he4rtlabs;

import br.com.he4rtlabs.handlers.EventHandlers;
import br.com.he4rtlabs.utils.Constants;
import br.com.he4rtlabs.utils.DiscordWebhook;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class He4rtLab extends JavaPlugin {

    @Override
    public void onEnable() {

        Bukkit.getLogger().info("[He4rt] Initialize Plugin V1 with Configs");

        getServer().getPluginManager().registerEvents(new EventHandlers(getLogger()), this);

        DiscordWebhook webhook = new DiscordWebhook(Constants.webhookURL);
        webhook.addEmbed(new DiscordWebhook.EmbedObject().setDescription("[He4rt] Server is Running!"));

        try {
            webhook.execute();
        } catch (IOException e) {
            getLogger().severe(e.getStackTrace().toString());
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("[He4rt] Disabled Plugin V1");
    }
}
