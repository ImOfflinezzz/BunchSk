package com.offline.bunchsk.expressions;

import org.bukkit.event.Event;

import java.util.Arrays;

import javax.annotation.Nullable;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.offline.bunchsk.utils.RegisterOptions;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

@RegisterOptions(
        Name="Pages of book",
        RegType="EXPRESSION",
        Syntaxes="[all] [book] pages of %itemstack%",
        ExprType=ExpressionType.PROPERTY,
        ExprClass=String.class)

public class ExprBookPages extends SimpleExpression<String>{
	private Expression<ItemStack> item;
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		item = (Expression<ItemStack>) expr[0];
		return true;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET){
			BookMeta book = (BookMeta) item.getSingle(e).getItemMeta();
			Object[] array1 = delta;
			String[] stringArray = Arrays.copyOf(array1, array1.length, String[].class);
			book.setPages(stringArray);
			item.getSingle(e).setItemMeta(book);
		}
		else if (mode == ChangeMode.DELETE){
			BookMeta book = (BookMeta) item.getSingle(e).getItemMeta();
			book.setPages("");
			item.getSingle(e).setItemMeta(book);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.DELETE)
			return CollectionUtils.array(String[].class);
		return null;
	}
	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[all] pages of %itemstack%";
	}
	
	@Override
	@Nullable
	protected String[] get(Event e) {
		BookMeta book = (BookMeta) item.getSingle(e).getItemMeta();
		Object[] array1 = book.getPages().toArray();
		String[] stringArray = Arrays.copyOf(array1, array1.length, String[].class);
		return stringArray;
	}

}