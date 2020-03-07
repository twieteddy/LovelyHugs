package io.github.twieteddy.lovelyhugs.commands;

import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HugCommand implements CommandExecutor {

    private static final double HUG_DISTANCE = 2d;
    private static final Permission PERM_HUG = new Permission("lovelyhugs.hug");
    private static final String MSG_SENDER_NOT_PLAYER = "";
    private static final String MSG_YOU_HUGGED = ChatColor.LIGHT_PURPLE + "Du hast"
            + ChatColor.YELLOW + " %s "
            + ChatColor.LIGHT_PURPLE + "umarmt";
    private static final String MSG_HUGGED_BY = ChatColor.LIGHT_PURPLE + "Du wurdest von"
            + ChatColor.YELLOW + " %s "
            + ChatColor.LIGHT_PURPLE + "umarmt";
    private static final String MSG_NO_PLAYER_NEARBY = ChatColor.LIGHT_PURPLE + "Es ist leider niemand in der Nähe =(";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Check if user is a player
        if (!(sender instanceof Player)) return true;

        // Check if user has permission
        if (!sender.hasPermission(PERM_HUG)) return false;

        // Get all nearby players within the hug distance
        Player player = (Player) sender;
        Player closest = getClosestPlayer(player, HUG_DISTANCE);

        // Check if nobody was close enough
        if (closest == null) {
            player.sendMessage(MSG_NO_PLAYER_NEARBY);
            return true;
        }

        // Hug
        hugPlayer(player, closest);
        return true;
    }

    private void hugPlayer(Player huggingPlayer, Player huggedPlayer) {
        // Send title
        huggingPlayer.sendTitle("", String.format(MSG_YOU_HUGGED, huggedPlayer.getName()), 10, 40, 20);
        huggedPlayer.sendTitle("", String.format(MSG_HUGGED_BY, huggingPlayer.getName()), 10, 40, 20);

        // Spawn particles
        huggingPlayer.getWorld().spawnParticle(Particle.HEART, huggingPlayer.getLocation(), 64, 0.5d, 1.0d, 0.5d);
        huggedPlayer.getWorld().spawnParticle(Particle.HEART, huggedPlayer.getLocation(), 64, 0.5d, 1.0d, 0.5d);

        // Play sound
        huggingPlayer.playSound(huggingPlayer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 0.5f);
        huggedPlayer.playSound(huggedPlayer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 0.5f);
    }

    private Player getClosestPlayer(Player player, double hugDistance) {
        List<Entity> nearbyPlayers = player.getNearbyEntities(hugDistance, hugDistance, hugDistance).stream()
                .filter(p -> p instanceof Player)
                .collect(Collectors.toList());
        if (nearbyPlayers.isEmpty()) {
            return null;
        }
        Entity closest = Collections.min(nearbyPlayers, Comparator.comparing(e -> e.getLocation().distanceSquared(player.getLocation())));
        return closest == null ? null : (Player) closest;
    }
}
