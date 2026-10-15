package me.furkan.ticareteklenti.listener;

import me.furkan.ticareteklenti.Main;
import me.furkan.ticareteklenti.manager.GUIManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;

public class EventListeners implements Listener {

    private final Main plugin;

    public EventListeners(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onVillagerRightClick(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Villager villager)) return;
        
        if (villager.hasMetadata("ticaret_turu")) {
            event.setCancelled(true);
            String tur = villager.getMetadata("ticaret_turu").get(0).asString();
            if (tur.equalsIgnoreCase("MESLEKSIZ")) {
                event.getPlayer().sendMessage(ChatColor.RED + "Bu köylünün henüz bir meslegi yok!");
                return;
            }
            GUIManager.openTradeGUI(event.getPlayer(), tur);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().contains("Borsasi")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;

            Player player = (Player) event.getWhoClicked();
            ItemStack clicked = event.getCurrentItem();
            ItemMeta meta = clicked.getItemMeta();
            List<String> lore = meta.getLore();

            if (lore == null || lore.size() < 2) return;

            try {
                // Lore verilerinden miktar ve para bilgilerini ayrıştırma
                int miktar = Integer.parseInt(ChatColor.stripColor(lore.get(0)).replaceAll("[^0-9]", ""));
                double para = Double.parseDouble(ChatColor.stripColor(lore.get(1)).replaceAll("[^0-9.]", ""));

                ItemStack targetMaterial = new ItemStack(clicked.getType(), miktar);
                if (player.getInventory().containsAtLeast(targetMaterial, miktar)) {
                    player.getInventory().removeItem(targetMaterial);
                    Main.getEconomy().depositPlayer(player, para);
                    player.sendMessage(ChatColor.GREEN + "Urun basariyla satildi! Hesabiniza " + para + "$ yatirildi.");
                } else {
                    player.sendMessage(ChatColor.RED + "Envanterinizde yeterli miktarda ürün bulunmuyor!");
                }
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "Islem sirasinda bir hata olustu.");
            }
        }
    }
}
