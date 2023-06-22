package net.Indyuce.bountyhunters.listener;

import net.Indyuce.bountyhunters.api.CustomItem;
import net.Indyuce.bountyhunters.api.language.Message;
import net.Indyuce.bountyhunters.api.player.PlayerData;
import net.Indyuce.bountyhunters.api.player.PlayerHunting;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class HuntListener implements Listener {

	@EventHandler
	public void a(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if ((event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
				|| !CustomItem.BOUNTY_COMPASS.matches(event.getItem()))
			return;

		// Heavy checks afterwards
		PlayerData data = PlayerData.get(player);
		if (!data.isHunting())
			return;

		PlayerHunting hunt = data.getHunting();
		if (hunt.isCompassActive())
			return;

		if (!hunt.getHunted().isOnline() || !hunt.getHunted().getPlayer().getWorld().equals(player.getWorld())) {
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
			Message.NOT_SAME_WORLD.format("player", hunt.getHunted().getPlayer().getName(),
					"world", hunt.getHunted().getPlayer().getWorld().getName()).send(player);
			return;
		}

		if (!player.hasPermission("bountyhunters.compass")) {
			return;
		}

		player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);
		hunt.enableCompass(player);
	}
}
