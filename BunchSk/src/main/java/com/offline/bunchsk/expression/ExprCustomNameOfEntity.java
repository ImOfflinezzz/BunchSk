package com.offline.bunchsk.expression;

import ch.njol.skript.classes.Changer;
import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.offline.bunchsk.utils.RegisterOptions;


@RegisterOptions(
	Name="Custom name of entity",
	RegType="EXPRESSION",
	Syntaxes="custom name of %livingentities%",
	ExprType=ExpressionType.SIMPLE,
	ExprClass=String.class)

public class ExprCustomNameOfEntity extends SimpleExpression<String> {

	private Expression<LivingEntity> entity;

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		entity = (Expression<LivingEntity>) e[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}
        
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET)
			entity.getSingle(e).setCustomName((String) delta[0]);
		else if (mode == Changer.ChangeMode.DELETE)
			entity.getSingle(e).setCustomName("");

	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {

		if (mode == Changer.ChangeMode.SET||mode == Changer.ChangeMode.DELETE)
			return CollectionUtils.array(String.class);
		return null;
	}
	@Override
	protected String[] get(Event e) {

		if (entity.getSingle(e) != null)
			return new String[] {entity.getSingle(e).getCustomName()};

		return new String[] {};
	}
}
