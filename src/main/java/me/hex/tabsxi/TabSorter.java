package me.hex.tabsxi;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.List;

public class TabSorter implements Listener {
    private final TabsXI plugin;
    private final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private final Scoreboard scoreboard;
    HashMap<String, Character> permsLetters = new HashMap<>();

    public TabSorter(TabsXI plugin) {

        this.plugin = plugin;
        scoreboard = plugin.scoreboard;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        String name = event.getPlayer().getName();
        List<String> perms = plugin.getConfig().getStringList("perms");

        event.getPlayer().setScoreboard(scoreboard);

        if (perms.isEmpty()) {
            System.out.println(ChatColor.RED + "ADD PERMISSIONS IN config.yml!");
            return;
        }

        for (String s : perms) {
            permsLetters.put(s, alphabet[perms.indexOf(s)]);
        }
        sort(name);

    }


    private void sort(String name) {

        Player player = Bukkit.getPlayer(name);

        for (String perm : permsLetters.keySet()) {
            if (player.hasPermission(perm)) {
                if (scoreboard.getTeam(String.valueOf(permsLetters
                        .get(perm))) == null) {
                    return;
                }

                Team team = scoreboard.getTeam(String.valueOf(permsLetters.get(perm)));

                plugin.teamColor(team.getName());
                team.addEntry(name);
                plugin.teamColor(team.getName());
            }

        }
    }
}

