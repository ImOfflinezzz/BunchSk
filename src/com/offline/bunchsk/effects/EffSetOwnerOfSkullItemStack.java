package com.offline.bunchsk.effects;
 
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.offline.bunchsk.utils.RegisterOptions;
 
@RegisterOptions(
        Name="Set owner of block skull",
        RegType="EFFECT",
        Syntaxes="set owner of %itemstack% to %string%")

public class EffSetOwnerOfSkullItemStack extends Effect{
       
        private Expression<ItemStack> item;
        private Expression<String> name;
       

        @SuppressWarnings("unchecked")
		@Override
        public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
                item = (Expression<ItemStack>) e[0];
                name = (Expression<String>) e[1];
                return true;
        }
 
        @Override
        public String toString(@javax.annotation.Nullable Event arg0, boolean arg1) {
                return null;
        }
 
        @SuppressWarnings("deprecation")
		@Override
        protected void execute(Event e) {
        	if (item.getSingle(e) != null && name.getSingle(e) != null){
        		if (item.getSingle(e).getType() == Material.SKULL || item.getSingle(e).getType() == Material.SKULL_ITEM){
            		SkullMeta skull = (SkullMeta)item.getSingle(e).getItemMeta();
            		item.getSingle(e).setType(Material.SKULL);
            		item.getSingle(e).setData(new MaterialData(item.getSingle(e).getData().getItemTypeId(), (byte)3));
            		skull.setOwner(name.getSingle(e));
            		item.getSingle(e).setItemMeta(skull);
        		}
        	}
        }
 
}