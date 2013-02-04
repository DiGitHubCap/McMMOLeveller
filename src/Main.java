/*
 * McMMO support for LevelFlare.
 * Copyright (C) 2013 Andrew Stevanus (Hoot215) <hoot893@gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.gmail.nossr50.events.experience.McMMOPlayerLevelUpEvent;

import me.Hoot215.LevelFlare.api.LevelFlareLeveller;
import me.Hoot215.LevelFlare.api.LevellerHandler;

@LevellerHandler(name = "McMMOLeveller", version = "1.1")
public class Main extends LevelFlareLeveller implements Listener
  {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onMcMMOPlayerLevelUp (McMMOPlayerLevelUpEvent event)
      {
        FileConfiguration config = this.getPlugin().getConfig();
        
        if (config.isList("mcmmo.enabled-skills"))
          {
            if ( !config.getStringList("mcmmo.enabled-skills").contains(
                event.getSkill().toString()))
              return;
          }
        
        if (config.isInt("mcmmo.multiple"))
          {
            if (event.getSkillLevel() % config.getInt("mcmmo.multiple") != 0)
              return;
          }
        
        Player player = event.getPlayer();
        
        this.getPlugin().launchFireworks(player);
      }
    
    @Override
    public void onUnload ()
      {
        this.getPlugin().getLevellerManager().unregisterBukkitEvents(this);
        this.getLogger().info("Is now unloaded");
      }
    
    @Override
    public void onLoad ()
      {
        this.getPlugin().getLevellerManager().registerBukkitEvents(this);
        this.getLogger().info("Is now loaded");
      }
  }
