package net.Indyuce.bountyhunters.compat.interaction.external;

import de.erethon.dungeonsxl.api.DungeonsAPI;
import net.Indyuce.bountyhunters.compat.interaction.InteractionRestriction;
import de.erethon.dungeonsxl.api.player.PlayerGroup;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DungeonsXLSupport implements InteractionRestriction {

    @Override
    public boolean canInteractWith(InteractionType interaction, Player claimer, OfflinePlayer target) {
        if (!Bukkit.getPluginManager().isPluginEnabled("DungeonsXL")) return false;

        DungeonsAPI api = (DungeonsAPI) Bukkit.getPluginManager().getPlugin("DungeonsXL");
        PlayerGroup group = api.getPlayerGroup(claimer);
        if (group != null)
            for (UUID uuid : group.getMembers().getUniqueIds())
                if (uuid.equals(target.getUniqueId()))
                    return false;

        return true;
    }
}
