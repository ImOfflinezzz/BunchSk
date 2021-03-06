package com.offline.bunchsk.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.offline.bunchsk.utils.RegisterOptions;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

@RegisterOptions(
        Name="Can Pickup",
        RegType="CONDITION",
        Syntaxes={"%entity% can pickup [items]", "%entity% can't pickup [items]"})

public class CondCanPickup extends Condition {

    private int matchedPattern;
    private Expression<LivingEntity> e;

    public boolean check(Event event) {
        LivingEntity e = this.e.getSingle(event);
        if (e == null) return false;
        Boolean b = matchedPattern == 0 ? false : true;
        if (e.getCanPickupItems()){
            b = !b;
        }
        return b;
    }

    public String toString(Event event, boolean b) {
        return this.getClass().getName();
    }

    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.matchedPattern = matchedPattern;
        this.e = (Expression<LivingEntity>) expressions[0];
        return true;
    }
}