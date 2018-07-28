package synchrodelusion.simpleplots.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import synchrodelusion.simpleplots.events.PlotEventListener;
import synchrodelusion.simpleplots.plots.SimplePlot;
import synchrodelusion.simpleplots.save.ConfigManager;

public class SimplePlots extends JavaPlugin{
	private static SimplePlots inst;
	private static ConfigManager configManager;
	public void onEnable() {
		inst=this;
		configManager=new ConfigManager();
		Bukkit.getPluginManager().registerEvents(new PlotEventListener(), this);
		SimplePlot.start();
	}
	public void onDisable() {
		SimplePlot.stop();
	}
	public static SimplePlots getInst() {
		return inst;
	}
	public static ConfigManager getConfigManager() {
		return configManager;
	}
}
