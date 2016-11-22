package com.offline.bunchsk.expression;

import javax.annotation.Nullable;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.offline.bunchsk.utils.RegisterOptions;

@RegisterOptions(
	Name="Dyed item",
	RegType="EXPRESSION",
	Syntaxes="dyed %itemstack% with %number%, %number%, %number%",
	ExprType=ExpressionType.SIMPLE,
	ExprClass=ItemStack.class)

public class ExprDyedItemStack extends SimpleExpression<ItemStack> {

    private Expression<ItemStack> item;
    private Expression<Number> numb1;
    private Expression<Number> numb2;
    private Expression<Number> numb3;
    
	@Override
	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}
    
    @SuppressWarnings("unchecked")
	@Override
    public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
            item = (Expression<ItemStack>) e[0];
            numb1 = (Expression<Number>) e[1];
            numb2 = (Expression<Number>) e[2];
            numb3 = (Expression<Number>) e[3];
            return true;
    }

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}
	@Override
	@javax.annotation.Nullable
	protected ItemStack[] get(Event e) {
		if (item.getSingle(e) != null && numb1.getSingle(e) != null && numb2.getSingle(e) != null && numb3.getSingle(e) != null) {

			if (item.getSingle(e).getType() == Material.LEATHER_BOOTS 
					|| item.getSingle(e).getType() == Material.LEATHER_CHESTPLATE 
					|| item.getSingle(e).getType() == Material.LEATHER_HELMET 
					|| item.getSingle(e).getType() == Material.LEATHER_LEGGINGS) {

				ItemStack litem = new ItemStack(item.getSingle(e).getType(), 1);
	    		LeatherArmorMeta lch = (LeatherArmorMeta)item.getSingle(e).getItemMeta();
	    		lch.setColor(Color.fromBGR(numb1.getSingle(e).intValue(), numb2.getSingle(e).intValue(), numb3.getSingle(e).intValue()));
				litem.setItemMeta(lch);
				return new ItemStack[] {litem};
			}
			return new ItemStack[] {item.getSingle(e)};
		}
		return new ItemStack[] {item.getSingle(e)};
	}
}
