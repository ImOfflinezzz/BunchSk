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
import com.offline.bunchsk.utils.RegisterOptions.RegisterType;

@RegisterOptions(
        Name="Surface player ti highest place",
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
                Location loc = player.getSingle(e).getLocation();
                Location loca = loc;
                do {
                        loca.setY(loca.getY() + 1);
                        if (loca.getBlock().getType() != Material.AIR){
                            loca.setY(loca.getY() + 1);
                            if (loca.getBlock().getType() == Material.AIR){
                                loca.setY(loca.getY() + 1);
                                if (loca.getBlock().getType() == Material.AIR)
                                    loca.setY(loca.getY() - 1);
                                    player.getSingle(e).teleport(loca);
                                    break;
                            }
                        
                        }
                } while (loca.getY() < 256);
	}
}