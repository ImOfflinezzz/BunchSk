package com.offline.bunchsk.expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.offline.bunchsk.utils.RegisterOptions;

@RegisterOptions(
        Name="Viewers of inventory",
        RegType="EXPRESSION",
            Syntaxes="[number of] viewers of %inventory%",
        ExprType=ExpressionType.PROPERTY,
        ExprClass=Player.class)

public class ExprViewersOfInventory extends SimpleExpression<Player>{
	private Expression<Inventory> inventory;
	@Override
	public Class<? extends Player> getReturnType() {
		return Player.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		inventory = (Expression<Inventory>) e[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	@javax.annotation.Nullable
	protected Player[] get(Event e) {
		if (inventory.getSingle(e) != null){
			Player[] list = inventory.getSingle(e).getViewers().toArray(new Player[inventory.getSingle(e).getViewers().size()]);
			return list;
		}
		return new Player[] {null};
		
	}






}
