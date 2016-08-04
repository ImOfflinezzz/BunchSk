package com.offline.bunchsk.expressions;

import java.util.Collection;

import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import com.offline.bunchsk.utils.RegisterOptions;


@RegisterOptions(
        Name="Duration of potion effect",
        RegType="EXPRESSION",
        Syntaxes="%string% [effect] duration of %livingentities%",
        ExprType=ExpressionType.SIMPLE,
        ExprClass=Timespan.class)

public class ExprDurationOfPotion extends SimpleExpression<Timespan>{
	private Expression<String> potion;
	private Expression<LivingEntity> entity;
	
	
	@Override
	public Class<? extends Timespan> getReturnType() {
		return Timespan.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		potion = (Expression<String>) e[0];
		entity = (Expression<LivingEntity>) e[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	@javax.annotation.Nullable
	protected Timespan[] get(Event e) {
		if (entity.getSingle(e) != null) {
			Collection<PotionEffect> C = entity.getSingle(e).getActivePotionEffects();
			for (PotionEffect p : C.toArray(new PotionEffect[0])){
				if (p.getType().getName().equalsIgnoreCase(potion.getSingle(e))){
					return new Timespan[] {Timespan.fromTicks(p.getDuration())};
				}
			}
		}
		return new Timespan[] {Timespan.fromTicks(0)};
	}
}
