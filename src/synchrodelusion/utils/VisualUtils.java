package synchrodelusion.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;

public class VisualUtils {
	public static int[] getLine(int A, int B) {
		int min=Math.min(A, B);
		int max=Math.max(A, B);
		int[] line=new int[max-min];
		for(int i=min; i<=max; i++) {
			line[i-min]=i;
		}
		return line;
	}
	public static Location[] getBorder(Location A, Location B) {
		if(!A.getWorld().equals(B.getWorld())) return null;
		int minX=Math.min(A.getBlockX(), B.getBlockX());
		int maxX=Math.max(A.getBlockX(), B.getBlockX());
		
		int minZ=Math.min(A.getBlockZ(), B.getBlockZ());
		int maxZ=Math.max(A.getBlockZ(), B.getBlockZ());
		
		int minY=Math.min(A.getBlockY(), B.getBlockY());
		int maxY=Math.max(A.getBlockY(), B.getBlockY());
		
		final World world=A.getWorld();
		List<Location> border=new ArrayList<>();
		for(int x=minX; x<=maxX; x++) {
			for(int z=minZ; z<=maxZ; z++) {
				for(int y=minY; y<=maxY; y++){
					Location loc=new Location(world,x,y,z);
					loc.add(0.5, 0.5, 0.5);
					if((y==minY)||(y==maxY)) {
						if((x==minX)||(x==maxX)||(z==minZ)||(z==maxZ)) {
							if(!border.contains(loc)) border.add(loc);
						}
					}else {
						if(((x==minX)||(x==maxX))&&((z==minZ)||(z==maxZ))) {
							if(!border.contains(loc)) border.add(loc);
						}
					}
				}
			}
		}
		return border.toArray(new Location[border.size()]);
	}
}	
