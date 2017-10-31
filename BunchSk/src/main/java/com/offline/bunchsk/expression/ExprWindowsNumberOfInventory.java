package com.offline.bunchsk.expression;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.offline.bunchsk.utils.RegisterOptions;

@RegisterOptions(
	Name="Windows of inventory",
	RegType="EXPRESSION",
	Syntaxes="[number of] windows of %inventory%",
	ExprType=ExpressionType.PROPERTY,
	ExprClass=Number.class)

public class ExprWindowsNumberOfInventory extends SimpleExpression<Number> {

	private Expression<Inventory> inv;

	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		inv = (Expression<Inventory>) e[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	@javax.annotation.Nullable
	protected Number[] get(Event e) {
		return new Number[] {inv.getSingle(e).getSize()};
	}






}
