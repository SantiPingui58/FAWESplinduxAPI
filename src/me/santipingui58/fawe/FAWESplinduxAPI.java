package me.santipingui58.fawe;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.FaweCache;
import com.boydti.fawe.util.EditSessionBuilder;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.ClipboardFormats;
import com.sk89q.worldedit.function.mask.BlockMask;
import com.sk89q.worldedit.function.pattern.BlockPattern;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.math.transform.Transform;
import com.sk89q.worldedit.patterns.SingleBlockPattern;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;



@SuppressWarnings("deprecation")
public class FAWESplinduxAPI {
	private static FAWESplinduxAPI manager;	
	 public static FAWESplinduxAPI getAPI() {
	        if (manager == null)
	        	manager = new FAWESplinduxAPI();
	        return manager;
	    }
	 
	 private HashMap<UUID,EditSession> sessions = new HashMap<UUID,EditSession>();
	 
	public void placeBlocks(Location l1,Location l2,Material material) {
		
		
		 EditSession editSession = new EditSessionBuilder(FaweAPI.getWorld(l1.getWorld().getName())).fastmode(true).build();
		 Region region = new CuboidRegion(new Vector(l1.getBlockX(), l1.getBlockY(), l1.getBlockZ()), new Vector(l2.getBlockX(), l2.getBlockY(), l2.getBlockZ()));
		 
		BaseBlock block = FaweCache.getBlock(material.getId(), 0);		
		 editSession.setBlocks(region, block); 
		 editSession.flushQueue();
	
	 }
	
	public void placeBlocks(Location l1,Location l2,Material material, byte id) {
			
		 EditSession editSession = new EditSessionBuilder(FaweAPI.getWorld(l1.getWorld().getName())).fastmode(true).build();
		 Region region = new CuboidRegion(new Vector(l1.getBlockX(), l1.getBlockY(), l1.getBlockZ()), new Vector(l2.getBlockX(), l2.getBlockY(), l2.getBlockZ()));
		 
		BaseBlock block = FaweCache.getBlock(material.getId(), id);		
		 editSession.setBlocks(region, block); 
		 editSession.flushQueue();
	
	 }
	
	
	public void placeCyl(Location center,Material block,int radius, int height, boolean isHcyl) {
		 EditSession editSession = new EditSessionBuilder(FaweAPI.getWorld(center.getWorld().getName())).fastmode(true).build();
		SingleBlockPattern a= new SingleBlockPattern(new BaseBlock(block.getId()));
		 editSession.makeCylinder(new Vector(center.getBlockX(),center.getBlockY(),center.getBlockZ()), a, radius, height, !isHcyl);
		 editSession.flushQueue();
	}
	 
	 
	 public void placeBlocks(Location l1, Location l2,HashMap<Material,Double> materials) {
		 EditSession editSession = new EditSessionBuilder(FaweAPI.getWorld(l1.getWorld().getName())).fastmode(true).build();
		 Region region = new CuboidRegion(new Vector(l1.getBlockX(), l1.getBlockY(), l1.getBlockZ()), new Vector(l2.getBlockX(), l2.getBlockY(), l2.getBlockZ()));

		 RandomPattern pattern = new RandomPattern();
		 
		 for (Map.Entry<Material, Double> entry : materials.entrySet()) {
			    Material material = entry.getKey();
			    double i = entry.getValue();
			    BlockPattern p = new BlockPattern(material.getId());
				 pattern.add(p, i);
			}
		 
		 editSession.setBlocks(region, pattern);
		 editSession.flushQueue();
	 }
	 
 public void replace(Location l1,Location l2,Material mask, HashMap<Material,Double> materials) {
		 
		 EditSession editSession = new EditSessionBuilder(FaweAPI.getWorld(l1.getWorld().getName())).fastmode(true).build();
		 Region region = new CuboidRegion(new Vector(l1.getBlockX(), l1.getBlockY(), l1.getBlockZ()), new Vector(l2.getBlockX(), l2.getBlockY(), l2.getBlockZ()));
		 
		 RandomPattern pattern = new RandomPattern();
		 
		 for (Map.Entry<Material, Double> entry : materials.entrySet()) {
			    Material material = entry.getKey();
			    double i = entry.getValue();
			    BlockPattern p = new BlockPattern(material.getId());
				 pattern.add(p, i);
			}
			BaseBlock block = FaweCache.getBlock(mask.getId(), 0);	
		    BlockMask m = new BlockMask(editSession,block);
		    
		 editSession.replaceBlocks(region, m, pattern);
		 editSession.flushQueue();
	 }
	 
	 public void replace(Location l1,Location l2,Material filter, Material replacement) {
		 
		 EditSession editSession = new EditSessionBuilder(FaweAPI.getWorld(l1.getWorld().getName())).fastmode(true).build();
		 Region region = new CuboidRegion(new Vector(l1.getBlockX(), l1.getBlockY(), l1.getBlockZ()), new Vector(l2.getBlockX(), l2.getBlockY(), l2.getBlockZ()));
		 
		BaseBlock r = FaweCache.getBlock(replacement.getId(), 0);		
		BaseBlock ff = FaweCache.getBlock(replacement.getId(), 0);
		Set<BaseBlock> f = new HashSet<BaseBlock>();
		f.add(ff);
		 editSession.replaceBlocks(region, f, r);
		 editSession.flushQueue();
	 }
	 
	 
	 public UUID pasteSchematic(File file,Location location, boolean pasteAir) {
		 
		 Vector v = new Vector(location.getBlockX(),location.getBlockY(),location.getBlockZ());
		 BukkitWorld w = new BukkitWorld(location.getWorld());
		  UUID uuid = UUID.randomUUID();
			EditSession session = null;
			try {
				session = ClipboardFormats.findByFile(file).load(file).paste(w, v, true, pasteAir, (Transform) null);
				session.flushQueue();
			} catch (IOException e) {
				e.printStackTrace();
			}	
			  sessions.put(uuid, session);

		
		  return uuid;
}
	 
	 public void undoSchematic(UUID uuid) {
		EditSession session = this.sessions.get(uuid);
		session.undo(session);
		session.flushQueue();
	 }
	 

	 
	 
	 
}
