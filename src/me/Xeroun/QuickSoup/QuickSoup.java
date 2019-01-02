package me.Xeroun.QuickSoup;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class QuickSoup extends JavaPlugin implements Listener {
	List<String> playersInvClick = new ArrayList<String>();
	
	List<String> warning = new ArrayList<String>();
	List<String> hacker = new ArrayList<String>();
	
	public void onEnable() {
		getLogger().info(this + " has been enabled.");
		getServer().getPluginManager().registerEvents(this, this);

		getConfig().options().copyDefaults(true);
		saveConfig();

	}

	public void onDisable() {
		getLogger().info(this + " has been disabled.");
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		
		if (!playersInvClick.contains(player.getName())) {
			playersInvClick.add(player.getName());
		}
		
		if(warning.contains(player.getName())){
			if(!hacker.contains(player.getName())){
				hacker.add(player.getName());
			}
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent event) {

		Player player = (Player) event.getPlayer();
		if (playersInvClick.contains(player.getName())) {
			playersInvClick.remove(player.getName());
		}
		
		if (warning.contains(player.getName())) {
			warning.remove(player.getName());
		}
		
		if (hacker.contains(player.getName())) {
			hacker.remove(player.getName());
		}
		
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {

		Player player = event.getEntity();
		if (playersInvClick.contains(player.getName())) {
			playersInvClick.remove(player.getName());
		}
		
		if (warning.contains(player.getName())) {
			warning.remove(player.getName());
		}

		if (hacker.contains(player.getName())) {
			hacker.remove(player.getName());
		}
		
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			Player player = event.getPlayer();
			if ((player.getItemInHand().getTypeId() == 282)) {
				if(playersInvClick.contains(player.getName())){
					warning.add(player.getName());
				}
				
				if (!hacker.contains(player.getName())) {
					if ((player.getMaxHealth() - player.getHealth() < getConfig().getInt("Heal Amount"))) {
						player.setHealth(player.getMaxHealth());
					} else {
						player.setHealth(player.getHealth() + getConfig().getInt("Heal Amount"));
					}

				} else {
					
					hacker.remove(player.getName());
					if (playersInvClick.contains(player.getName())) {
						playersInvClick.remove(player.getName());
					}
					
					if (warning.contains(player.getName())) {
						warning.remove(player.getName());
					}
					
				}

				player.getItemInHand().setTypeId(281);
			}

		}
	}

}
