package com.offline.bunchsk.expressions;

import org.bukkit.event.Event;

import java.util.List;

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
        Name="Page NUMBER of book",
        RegType="EXPRESSION",
        Syntaxes="[book] pages [at] %integer% of %itemstack%",
        ExprType=ExpressionType.PROPERTY,
        ExprClass=String.class)

public class ExprBookSpecificPage extends SimpleExpression<String>{
	private Expression<ItemStack> item;
	private Expression<Integer> page;
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
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		item = (Expression<ItemStack>) expr[1];
		page = (Expression<Integer>) expr[0];
		return true;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET){
			BookMeta book = (BookMeta) item.getSingle(e).getItemMeta();
			book.setPage(page.getSingle(e), (String) delta[0]);
			item.getSingle(e).setItemMeta(book);
		}
		else if (mode == ChangeMode.DELETE){
			BookMeta book = (BookMeta) item.getSingle(e).getItemMeta();
			List<String> pages = book.getPages();
			pages.remove(page.getSingle(e));
			book.setPages(pages);
			item.getSingle(e).setItemMeta(book);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.DELETE)
			return CollectionUtils.array(String.class);
		return null;
	}
	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "page %integer% of %itemstack%";
	}
	
	@Override
	@Nullable
	protected String[] get(Event e) {
		BookMeta book = (BookMeta) item.getSingle(e).getItemMeta();
		return new String[]{book.getPage(page.getSingle(e))};
	}

}