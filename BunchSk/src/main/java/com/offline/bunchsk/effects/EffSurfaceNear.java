package com.offline.bunchsk.effects;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.Material;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import com.offline.bunchsk.utils.RegisterOptions;

@RegisterOptions(
    Name="Surface player to highest place",
    RegType="EFFECT",
    Syntaxes="surf[ace] %player% to [the] near[est] [loc[ation]]")

public class EffSurfaceNear extends Effect {
        private Expression<Player> player;
        
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		player = (Expression<Player>) expr[0];
		return true;
	}
        
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "surface player to nearest";
	}
        
	@Override
	protected void execute(Event e) {
        Location location = player.getSingle(e).getLocation();
        Location loc = location;
        while (loc.getY() < 256) {
            loc.add(0, 1, 0);

            if(loc.getBlock().getType() != Material.AIR) {
                loc.add(0, 1, 0);

                if(loc.getBlock().getType() == Material.AIR) {

                    loc.add(0, 1, 0);

                    if(loc.getBlock().getType() == Material.AIR) {
                        loc.subtract(0, 1, 0);
                        player.getSingle(e).teleport(loc);
                        break;
                    }


                }
            }
        }
	}
}
