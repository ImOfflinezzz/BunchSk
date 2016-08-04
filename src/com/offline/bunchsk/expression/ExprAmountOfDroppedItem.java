package com.offline.bunchsk.expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.offline.bunchsk.utils.RegisterOptions;

@RegisterOptions(
        Name="Amount of dropped items",
        RegType="EXPRESSION",
        Syntaxes="amount of items on %entity%",
        ExprType=ExpressionType.SIMPLE,
        ExprClass=Number.class)

public class ExprAmountOfDroppedItem extends SimpleExpression<Number>{
	private Expression<Entity> entity;
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
		entity = (Expression<Entity>) e[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	@javax.annotation.Nullable
	protected Number[] get(Event e) {
		if (entity.getSingle(e) != null){
			if (entity.getSingle(e).getType() == EntityType.DROPPED_ITEM){
				if (entity.getSingle(e) instanceof Item){
					return new Number[] {((Item)entity.getSingle(e)).getItemStack().getAmount()};
				} return new Number[] {0};
			} return new Number[] {0};
		} return new Number[] {0};
	}
}
