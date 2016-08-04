package com.offline.bunchsk.effects;
 
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
 
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.offline.bunchsk.utils.RegisterOptions;

@RegisterOptions(
        Name="Set max health of players",
        RegType="EFFECT",
        Syntaxes="[bunch|mor] set max (health|hp) of %entity% to %number%")
 
public class EffMaxHealth extends Effect{
       
        private Expression<Entity> mob;
        private Expression<Number> var;
       

        @SuppressWarnings("unchecked")
		@Override
        public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
                mob = (Expression<Entity>) e[0];
                var = (Expression<Number>) e[1];
                return true;
        }
 
        @Override
        public String toString(@javax.annotation.Nullable Event arg0, boolean arg1) {
                return null;
        }
 
        @Override
        protected void execute(Event e) {
        	if (mob.getSingle(e) != null){
            	((LivingEntity)mob.getSingle(e)).setMaxHealth(var.getSingle(e).doubleValue());
        	}
        }
 
}