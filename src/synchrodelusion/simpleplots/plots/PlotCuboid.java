package synchrodelusion.simpleplots.plots;

import org.bukkit.Location;

public class PlotCuboid {
	private final int minX;
	private final int maxX;
	private final int minZ;
	private final int maxZ;
	public PlotCuboid(Location A, Location B) {
		minX=Math.min(A.getBlockX(), B.getBlockX());
		maxX=Math.max(A.getBlockX(), B.getBlockX());
		minZ=Math.min(A.getBlockZ(), B.getBlockZ());
		maxZ=Math.max(A.getBlockZ(), B.getBlockZ());
	}
	public boolean inside(Location location) {
		int x=location.getBlockX();
		int z=location.getBlockZ();
		if ((x>=minX)&&(x<=maxX)&&(z>=minZ)&&(z<=maxZ)) {
			return true;
		}
		return false;
	}
}
