package me.furkan.ticareteklenti;

import me.furkan.ticareteklenti.manager.BorsaRunnable;
import me.furkan.ticareteklenti.manager.VillagerManager;
import me.furkan.ticareteklenti.listener.EventListeners;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;
    private static Economy econ = null;
    private VillagerManager villagerManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        
        if (!setupEconomy()) {
            getLogger().severe("Vault veya Ekonomi eklentisi bulunamadi! Eklenti kapatiliyor.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.villagerManager = new VillagerManager();
        
        // Komutları ve Eventleri kaydet
        getCommand("ticaretköylüsü").setExecutor(villagerManager);
        getCommand("madenciköylü").setExecutor(villagerManager);
        getCommand("oduncuköylü").setExecutor(villagerManager);
        getCommand("eşyacıköylü").setExecutor(villagerManager);
        getCommand("büyücüköylü").setExecutor(villagerManager);
        getCommand("çiftçiköylü").setExecutor(villagerManager);
        getCommand("blokcuköylü").setExecutor(villagerManager);

        getServer().getPluginManager().registerEvents(new EventListeners(this), this);

        // Borsa Zamanlayıcısını Başlat
        long interval = getConfig().getLong("yenilenme-suresi", 300) * 20L;
        new BorsaRunnable().runTaskTimer(this, interval, interval);

        getLogger().info("BorsaTicaret eklentisi basariyla aktif edildi!");
    }

    @Override
    public void onDisable() {
        getLogger().info("BorsaTicaret eklentisi kapatildi.");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Main getInstance() { return instance; }
    public static Economy getEconomy() { return econ; }
    public VillagerManager getVillagerManager() { return villagerManager; }
}
