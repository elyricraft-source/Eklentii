package me.furkan.ticareteklenti.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;

public class GUIManager {

    public static void openTradeGUI(Player player, String tur) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_BLUE + tur + " Borsasi");

        // Örnek eşya eklemeleri (Borsa durumuna göre ölçeklenebilir)
        if (tur.equalsIgnoreCase("MADENCI")) {
            inv.setItem(11, createGuiItem(Material.DIAMOND, "Elmas Satisi", 16, 2500));
            inv.setItem(13, createGuiItem(Material.EMERALD, "Zümrüt Satisi", 32, 1200));
            inv.setItem(15, createGuiItem(Material.IRON_INGOT, "Demir Külçesi Satisi", 64, 800));
        } else if (tur.equalsIgnoreCase("ODUNCU")) {
            inv.setItem(11, createGuiItem(Material.OAK_LOG, "Mese Kütügü Satisi", 64, 150));
            inv.setItem(13, createGuiItem(Material.SPRUCE_LOG, "Ladin Kütügü Satisi", 64, 200));
            inv.setItem(15, createGuiItem(Material.CRIMSON_STEM, "Kizil Kök Satisi", 32, 400));
        } else {
            inv.setItem(13, createGuiItem(Material.WHEAT, "Genel Ticaret", 64, 100));
        }

        player.openInventory(inv);
    }

    private static ItemStack createGuiItem(Material material, String display, int miktar, double para) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.YELLOW + display);
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Istenen Miktar: " + ChatColor.WHITE + miktar);
            lore.add(ChatColor.GRAY + "Verilecek Para: " + ChatColor.GOLD + para + "$");
            lore.add("");
            lore.add(ChatColor.GREEN + "Satmak için tikla!");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }
}
