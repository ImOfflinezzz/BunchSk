package com.offline.bunchsk.expressions;

import javax.annotation.Nullable;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.offline.bunchsk.utils.RegisterOptions;

@RegisterOptions(
        Name="Amount of items",
        RegType="EXPRESSION",
            Syntaxes="amount of items in %itemstack%",
        ExprType=ExpressionType.SIMPLE,
        ExprClass=Number.class)

public class ExprAmountOfItem extends SimpleExpression<Number>{
	private Expression<ItemStack> item;
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
		item = (Expression<ItemStack>) e[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	@javax.annotation.Nullable
	protected Number[] get(Event e) {
		if (item.getSingle(e) != null){
			return new Number[] {item.getSingle(e).getAmount()};
		} else {
			return new Number[] {0};
		}
	}
}
