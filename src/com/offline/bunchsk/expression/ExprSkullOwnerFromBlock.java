package com.offline.bunchsk.expressions;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.offline.bunchsk.utils.RegisterOptions;

@RegisterOptions(
        Name="Owner of head  block",
        RegType="EXPRESSION",
        Syntaxes="skull owner of %block%",
        ExprType=ExpressionType.PROPERTY,
        ExprClass=String.class)

//skull owner of %block%
public class ExprSkullOwnerFromBlock extends SimpleExpression<String>{
	
	private Expression<Block> block;
	
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
		block = (Expression<Block>) e[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	@javax.annotation.Nullable
	protected String[] get(Event e) {
		if (block.getSingle(e) != null){
			if (block.getSingle(e).getType() == Material.SKULL || block.getSingle(e).getType() == Material.SKULL_ITEM){
				Skull skull = (Skull)block.getSingle(e).getState();
				if (skull.getSkullType() == SkullType.PLAYER){
					return new String[] {skull.getOwner()};
				}
			}
		} 
		return null;
	}
}
