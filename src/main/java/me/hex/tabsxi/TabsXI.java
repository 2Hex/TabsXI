package me.hex.tabsxi;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class TabsXI extends JavaPlugin {

    String myString = "abcdefghijklmnopqrstuvwxyz";
    ArrayList<Character> alphabet = new ArrayList<>(myString
            .chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
    ScoreboardManager sm;
    Scoreboard scoreboard;
    List<String> cols;

    @Override
    public void onEnable() {

        sm = Bukkit.getScoreboardManager();
        scoreboard = sm.getMainScoreboard();

        saveDefaultConfig();

        cols = getConfig().getStringList("colors");

        for (char letter = 'a'; letter <= 'z'; letter++) {

            List<String> cols = getConfig().getStringList("colors");

            if (scoreboard.getTeam(String.valueOf(letter)) == null)
                scoreboard.registerNewTeam(String.valueOf(letter));

            if ((cols.size() - 1) == alphabet.indexOf(letter)) {
                break;
            }

            scoreboard.getTeam(String.valueOf(letter))
                    .setPrefix(cols.get(alphabet.indexOf(letter)));
        }
        Bukkit.getServer().getPluginManager()
                .registerEvents(new TabSorter(this), this);

    }

    public void teamColor(String team) {
        scoreboard.getTeam(team).setPrefix(ChatColor
                .translateAlternateColorCodes('&',
                        cols.get(alphabet.indexOf(team.toCharArray()[0]))));
    }

}
