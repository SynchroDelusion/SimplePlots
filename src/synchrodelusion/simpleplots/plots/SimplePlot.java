package synchrodelusion.simpleplots.plots;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import synchrodelusion.simpleplots.main.SimplePlots;

public class SimplePlot {
	private static final int defaultSize=24;
	private static final List<SimplePlot> plots=new ArrayList<SimplePlot>();
	private final PlotCuboid cuboid;
	private final Location centerBlock;
	private final String player;
	private final int size;
	private final List<String> members;
	private final String ID;
	private SimplePlot(String ID, String player, Location location, int size, List<String> members) {
		this.player=player;
		this.centerBlock=location.clone();
		this.size=size;
		Location A=location.clone().add(Math.negateExact(size), 0, Math.negateExact(size));
		Location B=location.clone().add(size,0,size);
		PlotCuboid cuboid=new PlotCuboid(A,B);
		this.cuboid=cuboid;
		this.members=members;
		this.ID=ID;
	}
	public PlotCuboid getCuboid() {
		return cuboid;
	}
	public Location getCenter() {
		return centerBlock.clone();
	}
	public String getPlayer() {
		return player;
	}
	public int getSize() {
		return size;
	}
	public List<String> getMembers(){
		return members;
	}
	public String getID() {
		return ID;
	}
	public boolean isOwner(Player player) {
		if(player.getName().equalsIgnoreCase(this.player)) return true;
		return false;
	}
	public static void runTimer() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(SimplePlots.getInst(), new Runnable() {
			@Override
			public void run() {
				Bukkit.getConsoleSender().sendMessage("§7[SimplePlots] Zapisywanie dzia³ek do plików...");
				saveAll();
				Bukkit.getConsoleSender().sendMessage("§7[SimplePlots] Dzia³ki zosta³y zapisane...");
			}
		}, 0, 6000);
	}
	public static void start() {
		File[] files=SimplePlots.getConfigManager().getPlotsDirectory().listFiles();
		for(File f:files) {
			YamlConfiguration config=YamlConfiguration.loadConfiguration(f);
			if(config!=null) {
				SimplePlot plot=loadConfig(config);
				plots.add(plot);
			}
		}
	}
	public static void saveAll() {
		for(SimplePlot plot:plots) {
			YamlConfiguration config=generateConfig(plot);
			File f=SimplePlots.getConfigManager().getPlotFile(plot);
			try {
				config.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void stop() {
		saveAll();
	}
		
	public static List<SimplePlot> plots(){
		return plots;
	}
	public static int getOwnedPlots(Player player) {
		String name=player.getName();
		int amount=0;
		for(SimplePlot plot:plots) {
			if(plot.getPlayer().equalsIgnoreCase(name)) amount++;
		}
		return amount;
	}
	public static SimplePlot getPlotByLoc(Location location) {
		for(SimplePlot plot:plots) {
			if(plot.getCuboid().inside(location)) return plot;
		}
		return null;
	}
	
	public static SimplePlot createPlot(Player player, Location location) {
		String id=generateID(player);
		SimplePlot plot=new SimplePlot(id, player.getName(), location, defaultSize, new ArrayList<String>());
		plots.add(plot);
		return plot;
	}
	public static void removePlot(SimplePlot plot) {
		if(plots.contains(plot)) {
			File f=SimplePlots.getConfigManager().getPlotFile(plot);
			f.delete();
			plots.remove(plot);
		}
	}
	public boolean canModify(Player p) {
		if(p.getName().equalsIgnoreCase(player)) return true;
		if(contains(members, p.getName())) return true;
		return false;
	}
	private static YamlConfiguration generateConfig(SimplePlot plot) {
		YamlConfiguration config=new YamlConfiguration();
		config.set("player", plot.getPlayer());
		config.set("members", plot.getMembers());
		config.set("id", plot.getID());
		config.set("size", plot.getSize());
		Location loc=plot.getCenter();
		config.set("location.world", loc.getWorld().getName());
		config.set("location.x", loc.getBlockX());
		config.set("location.y", loc.getBlockY());
		config.set("location.z", loc.getBlockZ());
		return config;
	}
	private static SimplePlot loadConfig(YamlConfiguration config) {
		String player=config.getString("player");
		List<String> members=config.getStringList("members");
		String id=config.getString("id");
		int size=config.getInt("size");
		World world=Bukkit.getServer().getWorld(config.getString("location.world"));
		int x=config.getInt("location.x");
		int y=config.getInt("location.y");
		int z=config.getInt("location.z");
		Location center=new Location(world,x,y,z);
		SimplePlot plot=new SimplePlot(id, player, center, size, members);
		return plot;
	}
	private boolean contains(List<String> list, String val) {
		for(String element: list) {
			if(element.equalsIgnoreCase(val)) return true;
		}
		return false;
	}
	
	private static String generateID(Player player) {
		Random rand=new Random();
		String id=player.getName().toLowerCase()+"-";
		for(int i=0; i<=2; i++) {
			int number=rand.nextInt(9);
			id=id+number;
		}
		return id;
	}
}
