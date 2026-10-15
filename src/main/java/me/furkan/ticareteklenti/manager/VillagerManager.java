package me.furkan.ticareteklenti.manager;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.metadata.FixedMetadataValue;
import me.furkan.ticareteklenti.Main;

public class VillagerManager implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (command.getName().equalsIgnoreCase("ticaretköylüsü")) {
            if (args.length > 0 && args[0].equalsIgnoreCase("koy")) {
                Location loc = player.getLocation();
                Villager villager = loc.getWorld().spawn(loc, Villager.class);
                villager.setCustomName(ChatColor.GRAY + "Mesleksiz Köylü");
                villager.setCustomNameVisible(true);
                villager.setInvulnerable(true);
                villager.setAI(false);
                villager.setMetadata("ticaret_turu", new FixedMetadataValue(Main.getInstance(), "MESLEKSIZ"));
                player.sendMessage(ChatColor.GREEN + "Ticaret köylüsü basariyla olusturuldu!");
                return true;
            }
        }

        // Baktığı köylüyü bulup meslek atama
        Entity target = player.getTargetEntity(5);
        if (!(target instanceof Villager villager)) {
            player.sendMessage(ChatColor.RED + "Bir köylüye bakmalisiniz!");
            return true;
        }

        String meslek = command.getName().toLowerCase();
        switch (meslek) {
            case "madenciköylü" -> atamayıYap(villager, "MADENCI", ChatColor.AQUA + "" + ChatColor.BOLD + "MADENCİ");
            case "oduncuköylü" -> atamayıYap(villager, "ODUNCU", ChatColor.GOLD + "" + ChatColor.BOLD + "ODUNCU");
            case "eşyacıköylü" -> atamayıYap(villager, "ESYACI", ChatColor.YELLOW + "" + ChatColor.BOLD + "EŞYACI");
            case "büyücüköylü" -> atamayıYap(villager, "BUYUCU", ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "BÜYÜCÜ");
            case "çiftçiköylü" -> atamayıYap(villager, "CIFTCI", ChatColor.GREEN + "" + ChatColor.BOLD + "ÇİFTÇİ");
            case "blokcuköylü" -> atamayıYap(villager, "BLOKCU", ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "BLOKÇU");
        }
        player.sendMessage(ChatColor.GREEN + "Köylünün meslegi güncellendi!");
        return true;
    }

    private void atamayıYap(Villager v, String tur, String isim) {
        v.setCustomName(isim);
        v.setMetadata("ticaret_turu", new FixedMetadataValue(Main.getInstance(), tur));
    }
}
