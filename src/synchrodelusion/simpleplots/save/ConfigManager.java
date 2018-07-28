package synchrodelusion.simpleplots.save;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import synchrodelusion.simpleplots.main.SimplePlots;
import synchrodelusion.simpleplots.plots.SimplePlot;

public class ConfigManager {
	private final File plotsDirectory;
	public ConfigManager() {
		if(!SimplePlots.getInst().getDataFolder().exists()) {
			SimplePlots.getInst().getDataFolder().mkdir();
		}
		plotsDirectory=new File(SimplePlots.getInst().getDataFolder(), "/plots");
		if(!plotsDirectory.exists()) {
			plotsDirectory.mkdir();
		}
	}
	public File getPlotFile(SimplePlot plot) {
		File f=new File(plotsDirectory, plot.getID()+".yml");
		if(!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return f;
	}
	
	public File getPlotsDirectory() {
		return plotsDirectory;
	}
	public void savePlot(YamlConfiguration plot) {
		File f=new File(plotsDirectory, plot.getString("id")+".yml");
		try {
			plot.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
