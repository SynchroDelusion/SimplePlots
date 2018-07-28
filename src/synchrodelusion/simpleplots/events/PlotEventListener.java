package synchrodelusion.simpleplots.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import synchrodelusion.simpleplots.plots.SimplePlot;

public class PlotEventListener implements Listener{
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		SimplePlot plot=SimplePlot.getPlotByLoc(event.getBlock().getLocation());
		if(plot!=null) {
			if(!plot.canModify(event.getPlayer())) event.setCancelled(true);
		}else {
			
		}
	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		SimplePlot plot=SimplePlot.getPlotByLoc(event.getBlock().getLocation());
		if(plot!=null) {
			if(plot.canModify(event.getPlayer())) {
				if(event.getBlock().getLocation().equals(plot.getCenter())) {
					if(plot.isOwner(event.getPlayer())) {
						SimplePlot.removePlot(plot);
					}else event.setCancelled(true);
				}
			}else event.setCancelled(true);
		}
	}
}
