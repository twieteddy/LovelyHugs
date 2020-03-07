package io.github.twieteddy.lovelyhugs;

import io.github.twieteddy.lovelyhugs.commands.HugCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class LovelyHugs extends JavaPlugin {

    private static LovelyHugs instance = null;

    @Override
    public void onEnable() {
        LovelyHugs.instance = this;
        getCommand("hug").setExecutor(new HugCommand());
    }

    @Override
    public void onDisable() {
        LovelyHugs.instance = null;
    }

    public static LovelyHugs getInstance() {
        return instance;
    }
}
