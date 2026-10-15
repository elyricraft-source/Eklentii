package me.furkan.ticareteklenti.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class BorsaRunnable extends BukkitRunnable {
    @Override
    public void run() {
        // Tüm aktif sunucuya borsa fiyatlarının güncellendiğini duyurur
        Bukkit.broadcastMessage(ChatColor.GOLD + "[Borsa] " + ChatColor.YELLOW + "Bütün köylü fiyatlari ve ürün miktarlari borsa verilerine göre güncellendi!");
    }
}
