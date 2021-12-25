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
    private final ScoreboardManager sm;
    private final Scoreboard scoreboard;
    HashMap<String, Character> permsLetters = new HashMap<>();

    public TabSorter(TabsXI plugin) {
        this.plugin = plugin;
        sm = plugin.sm;
        scoreboard = plugin.scoreboard;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().setScoreboard(scoreboard);
        String name = event.getPlayer().getName();
        List<String> perms = plugin.getConfig().getStringList("perms");
        System.out.println("perms is (the string list btw): ");
        System.out.println(perms);
        if (perms.isEmpty()) {
            System.out.println(ChatColor.RED + "ADD PERMISSIONS IN CONFIG!");
            return;
        }

        for (String s : perms) {
            permsLetters.put(s, alphabet[perms.indexOf(s)]);
        }
        System.out.println("permsLetters is the hashmap btw: ");
        System.out.println(permsLetters);

        sort(name);
        for (org.bukkit.scoreboard.Team t : scoreboard.getTeams())
            event.getPlayer().sendMessage(t.getName() + ": " + t.getEntries());
    }


    private void sort(String name) {

        Player player = Bukkit.getPlayer(name);
        for (String perm : permsLetters.keySet()) {
            System.out.println(perm + " is one of keySet (For)");
            if (player.hasPermission(perm)) {
                System.out.println("player has permission");
                if (scoreboard.getTeam(String.valueOf(permsLetters.get(perm))) == null) {
                    System.out.println("PermLetters: " + permsLetters);
                    System.out.println("perm: " + perm);
                    System.out.println("ACTUAL TEAM NAME WHICH IS NULL :" + permsLetters.get(perm));
                    System.out.println("NULL FIRSTTT");
                    return;
                }
                System.out.println("FIRST is not null this time haha");
                Team team = scoreboard.getTeam(String.valueOf(permsLetters.get(perm)));
                plugin.teamColor(team.getName());
                team.addEntry(name);
                plugin.teamColor(team.getName());
                System.out.println("added");
            }

        }
    }
}

