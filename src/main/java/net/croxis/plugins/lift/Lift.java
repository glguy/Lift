package net.croxis.plugins.lift;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.V10lator.V10verlap.V10verlap;
import de.V10lator.V10verlap.V10verlap_API;

public class Lift extends JavaPlugin {
	boolean debug = false;
	boolean useSpout = false;
	public HashSet<Entity> fallers = new HashSet<Entity>();
	public HashSet<Elevator> lifts = new HashSet<Elevator>();
	//public double liftSpeed = 0.5;
	public int liftArea = 16;
	//public Material baseMaterial = Material.IRON_BLOCK;
	public HashMap<Material, Double> blockSpeeds = new HashMap<Material, Double>();
	public boolean autoPlace = false;
	public boolean checkGlass = false;
	public boolean useV10 = false;
	public V10verlap_API v10verlap_API = null;
	
    public void onDisable() {
    	lifts.clear();
        System.out.println(this + " is now disabled!");
    }

    public void onEnable() {
    	new LiftRedstoneListener(this);
    	new LiftPlayerListener(this);
    	
    	//liftSpeed = this.getConfig().getDouble("liftSpeed");
    	liftArea = this.getConfig().getInt("maxLiftArea");
    	debug = this.getConfig().getBoolean("debug");
    	//baseMaterial = Material.valueOf(this.getConfig().getString("baseBlock", "IRON_BLOCK"));
    	autoPlace = this.getConfig().getBoolean("autoPlace");
    	checkGlass = this.getConfig().getBoolean("checkGlass");
    	Set<String> baseBlockKeys = this.getConfig().getConfigurationSection("baseBlockSpeeds").getKeys(false);
    	for (String key : baseBlockKeys){
    		blockSpeeds.put(Material.valueOf(key), this.getConfig().getDouble("baseBlockSpeeds." + key));
    	}
    	
    	this.getConfig().options().copyDefaults(true);
        saveConfig();
        
        Plugin test = getServer().getPluginManager().getPlugin("Spout");
        if(test != null) {
        	useSpout = true;
        	System.out.println(this + " detected Spout!");
        }
        
        test = getServer().getPluginManager().getPlugin("V10verlap");
        if(test != null) {
        	V10verlap v10verlap = (V10verlap) test;
        	v10verlap_API = v10verlap.getAPI();
        	System.out.println(this + " detected V10verlap!");
            if(v10verlap_API.getVersion() >= 2.0D){ // Check for an API breakage, this is important!
              v10verlap_API = null;
              System.out.println("Wrong V10verlap version. Disabled integration");
            } else {
            	useV10 = true;
            }
            
        }
        
        if (debug){
			System.out.println("maxArea: " + Integer.toString(liftArea));
			System.out.println("autoPlace: " + Boolean.toString(autoPlace));
			System.out.println("checkGlass: " + Boolean.toString(checkGlass));
		}
        
        System.out.println(this + " is now enabled!");
    }
    
    public void removeLift(Elevator elevator){
    	
    }
}
