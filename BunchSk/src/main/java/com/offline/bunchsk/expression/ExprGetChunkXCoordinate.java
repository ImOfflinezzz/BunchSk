package com.offline.bunchsk.expression;

import javax.annotation.Nullable;

import org.bukkit.Chunk;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.offline.bunchsk.utils.RegisterOptions;

@RegisterOptions(
	Name="x-coord of chunk",
	RegType="EXPRESSION",
	Syntaxes="x[-](loc[ation]|coord[inate]) of %chunk%",
	ExprType=ExpressionType.PROPERTY,
	ExprClass=Integer.class)

public class ExprGetChunkXCoordinate extends SimpleExpression<Integer> {

	private Expression<Chunk> chunk;
	@Override
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		chunk = (Expression<Chunk>) e[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	@javax.annotation.Nullable
	protected Integer[] get(Event e) {

		if (chunk.getSingle(e) != null) {

			return new Integer[] {chunk.getSingle(e).getX()*16};
		}
		return new Integer[] {};
	}






}
