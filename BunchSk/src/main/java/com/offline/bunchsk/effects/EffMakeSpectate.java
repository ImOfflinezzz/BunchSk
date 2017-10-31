package com.offline.bunchsk.effects;


import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.offline.bunchsk.utils.RegisterOptions;

@RegisterOptions(
	Name="Make players spectate entities",
	RegType="EFFECT",
	Syntaxes="make %player% spectate %entity%")

public class EffMakeSpectate extends Effect{
	private static Expression<Player> player;
	private static Expression<Entity> entity;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int label, Kleenean arg2, ParseResult arg3) {
		EffMakeSpectate.player = (Expression<Player>) expr[0];
		EffMakeSpectate.entity = (Expression<Entity>) expr[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "make %player% spectate %entity%";
	}

	@Override
	protected void execute(Event e) {
    	Player player = EffMakeSpectate.player.getSingle(e);
    	if (player.getGameMode() != GameMode.SPECTATOR)
    		player.setGameMode(GameMode.SPECTATOR);

    	player.setSpectatorTarget(EffMakeSpectate.entity.getSingle(e));
	}

}