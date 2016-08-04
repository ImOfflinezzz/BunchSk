package com.offline.bunchsk.effects;


import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.offline.bunchsk.utils.RegisterOptions;

@RegisterOptions(
        Name="Force respawn player",
        RegType="EFFECT",
        Syntaxes="[exert|bunch] force (respawn|revive) %player%")

public class EffForceRespawn extends Effect{
	private static Expression<Player> player;
        
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int label, Kleenean arg2, ParseResult arg3) {
		EffForceRespawn.player = (Expression<Player>) expr[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[exter] force respawn %player%";
	}

	@Override
	protected void execute(Event e) {
    	EffForceRespawn.player.getSingle(e).spigot().respawn();
	}

}