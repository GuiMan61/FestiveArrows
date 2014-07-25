package me.yukonapplegeek.festivearrows;

import net.minecraft.server.v1_7_R3.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftFirework;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class FestiveArrows extends JavaPlugin {
    private static FestiveArrows festiveArrows;
    public boolean letItGoBoomEnabled = false;

    public FestiveArrows() {
        festiveArrows = this;
    }
    public static FestiveArrows get() {
        return festiveArrows;
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.reloadConfig();

        this.getServer().getPluginManager().registerEvents(new LetItGoBoom(), this);
        if (this.getConfig().getBoolean("bow.enabled")) {
            this.getServer().getPluginManager().registerEvents(new BowEffect(), this);
        }
        if (this.getConfig().getBoolean("death-enabled")) {
            this.getServer().getPluginManager().registerEvents(new DeathEffect(), this);
        }
        if (this.getConfig().getBoolean("tnt-enabled")) {
            this.getServer().getPluginManager().registerEvents(new TntEffect(), this);
        }
    }

    public static void playFirework(Location location, FireworkEffect effect) {
        final Firework firework = location.getWorld().spawn(location, Firework.class);
        FireworkMeta meta = firework.getFireworkMeta();
        meta.addEffect(effect);
        firework.setFireworkMeta(meta);
        NBTTagCompound nbtData = new NBTTagCompound();
        nbtData.setInt("Life", 1);
        nbtData.setInt("LifeTime", 2);
        ((CraftFirework) firework).getHandle().a(nbtData);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if ((sender.isOp() || sender.hasPermission("festivearrows.letitgoboom")) && cmd.getName().equalsIgnoreCase("letitgoboom")) {
            letItGoBoomEnabled = !letItGoBoomEnabled;
            if (letItGoBoomEnabled) {
                this.getServer().broadcastMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "Let it go BOOM!, escribe el comando otra vez para desactivarlo.");
            }

            return true;
        }

        return false;
    }
}
