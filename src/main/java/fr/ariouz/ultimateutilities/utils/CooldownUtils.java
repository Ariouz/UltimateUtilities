package fr.ariouz.ultimateutilities.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class CooldownUtils {

    public long getCooldown(Player player, HashMap<String, Long> map){
        if(map.containsKey(player.getName())){
            return map.get(player.getName());
        }
        return 0;
    }

    public boolean isOnCooldown(Player player, HashMap<String, Long> map) {
        return !(getCooldown(player, map) - System.currentTimeMillis() <= 0);
    }

}
