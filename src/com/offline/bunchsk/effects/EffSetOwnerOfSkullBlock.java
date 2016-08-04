package com.offline.bunchsk.effects;
 
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.event.Event;
 
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.offline.bunchsk.utils.RegisterOptions;
 
@RegisterOptions(
        Name="Set owner of block skull",
        RegType="EFFECT",
        Syntaxes="set owner of [head at|skull at] %block% to %string%")

public class EffSetOwnerOfSkullBlock extends Effect{
       
        private Expression<Block> block;
        private Expression<String> name;
       

        @SuppressWarnings("unchecked")
		@Override
        public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
                block = (Expression<Block>) e[0];
                name = (Expression<String>) e[1];
                return true;
        }
 
        @Override
        public String toString(@javax.annotation.Nullable Event arg0, boolean arg1) {
                return null;
        }
 
        @Override
        protected void execute(Event e) {
        	if (block.getSingle(e) != null && name.getSingle(e) != null){
        		if (block.getSingle(e).getType() == Material.SKULL || block.getSingle(e).getType() == Material.SKULL_ITEM){
        			Skull skull = (Skull)block.getSingle(e).getState();
        		    skull.setSkullType(SkullType.PLAYER);
        		    skull.setOwner(name.getSingle(e));
        		    skull.update();
        		}
        	}
        }
 
}