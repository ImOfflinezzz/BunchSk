package com.offline.bunchsk.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.offline.bunchsk.effects.EffSurfaceHigh;
import com.offline.bunchsk.utils.RegisterOptions;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;

@RegisterOptions(
        Name="Plugin check",
        RegType="CONDITION",
        Syntaxes={"%string% is enable", "%string% is disabled"})
public class CondPluginCheck extends Condition {

    private int matchedPattern;
    private Expression<String> s;

    public boolean check(Event event) {
        if (s == null) return false;
        Boolean b = matchedPattern == 0 ? false : true;
        if (Bukkit.getPluginManager().getPlugin(this.s.getSingle(event)) != null){
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
        this.s = (Expression<String>) expressions[0];
        return true;
    }
}