package com.offline.bunchsk.effects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LeashHitch;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.offline.bunchsk.utils.RegisterOptions;

@RegisterOptions(
	Name="Leash entity to fence",
	RegType="EFFECT",
	Syntaxes="(leash|lead) %livingentities% to %block%")

public class EffLeashFence extends Effect {

	private Expression<LivingEntity> entity1;

	private Expression<Block> block1;

	private static Entity hitch;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		entity1 = (Expression<LivingEntity>) expr[0];
		block1 = (Expression<Block>) expr[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "(leash|lead) %livingentities% to %block%";
	}

	@Override
	protected void execute(Event e) {
		LivingEntity[] entity = entity1.getArray(e);
		Block block = block1.getSingle(e);

		for (LivingEntity e1 : entity) {
			try {

				EffLeashFence.hitch = block.getLocation().getWorld().spawn(block.getLocation(), LeashHitch.class);
			}
			catch (Exception ex) {
				Location loc2 = block.getLocation();
				loc2.setX(loc2.getX() - 1);
				Material b = loc2.getWorld().getBlockAt(loc2).getType();
				loc2.getWorld().getBlockAt(loc2).setType(Material.STONE);
				EffLeashFence.hitch = block.getLocation().getWorld().spawn(block.getLocation(), LeashHitch.class);
				loc2.getWorld().getBlockAt(loc2).setType(b);
			}
			e1.setLeashHolder(hitch);
		}
	}

}
