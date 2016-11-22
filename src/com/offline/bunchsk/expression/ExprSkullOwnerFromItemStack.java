package com.offline.bunchsk.expression;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.offline.bunchsk.utils.RegisterOptions;

@RegisterOptions(
	Name="Owner of head",
	RegType="EXPRESSION",
	Syntaxes="skull owner of %itemstack%",
	ExprType=ExpressionType.PROPERTY,
	ExprClass=String.class)

//skull owner of %block%
public class ExprSkullOwnerFromItemStack extends SimpleExpression<String>{
	
	private Expression<ItemStack> item;
	
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
		item = (Expression<ItemStack>) e[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	@javax.annotation.Nullable
	protected String[] get(Event e) {

		if (item.getSingle(e) != null) {

			if (item.getSingle(e).getType() == Material.SKULL_ITEM || item.getSingle(e).getType() == Material.SKULL) {

				SkullMeta skull = (SkullMeta)item.getSingle(e).getItemMeta();
				return new String[] {skull.getOwner()};
			}
		} 
		return null;
	}
}
