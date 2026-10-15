package me.furkan.ticareteklenti.manager;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import java.util.UUID;

public class SkinManager {
    // Özel kafa derileri (Texture Base64 değerleri)
    public static final String MADENCI_SKIN = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmI0MThmNzkyN2I2YWY0N2Y4YzZkNjFlYTA0MmFlZjY0ZDRlZTkwNDU2NTliYmVkY2M5MWY0Njg4Y2FjYjMifX19";
    public static final String ODUNCU_SKIN = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNThiMWY3ZDJkOWZlOTVjZGM1NWI4NTM3YjRiNzhiYTlhYTBjZDYyOWU1YmFjNjg3N2UzNWMwYWJjMTYyYjUifX19";

    public static ItemStack getCustomHead(String urlValue) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        if (meta != null) {
            // Paper API yardımıyla profile güvenli kafa dokusu ekleme işlemi
            var profile = org.bukkit.Bukkit.createProfile(UUID.randomUUID(), null);
            profile.getProperties().add(new org.bukkit.profile.ProfileProperty("textures", urlValue));
            meta.setPlayerProfile(profile);
            head.setItemMeta(meta);
        }
        return head;
    }
}
