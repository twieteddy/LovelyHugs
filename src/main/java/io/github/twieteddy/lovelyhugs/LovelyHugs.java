package io.github.twieteddy.lovelyhugs;

import io.github.twieteddy.lovelyhugs.commands.HugCommand;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class LovelyHugs extends JavaPlugin {

  @Override
  public void onEnable() {
    getCommand("hug").setExecutor(new HugCommand());
  }
}
