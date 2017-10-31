package com.offline.bunchsk.conditions;

import org.bukkit.event.Event;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.offline.bunchsk.utils.RegisterOptions;

@RegisterOptions(
	Name="ExertSk Web Status",
	RegType="CONDITION",
	Syntaxes={"%string% is (online|working|not offline|good|not bad|not 404|not down)", "%string% is (offline|not working|not online|good|bad|404|down)"})

public class CondWebStatus extends Condition {
        
	private Expression<String> EXPRurl;

	private int matchedPattern;

	@Override
	public boolean check(Event e) {
		try {
			URL u = new URL (EXPRurl.getSingle(e));
			HttpURLConnection huc =  (HttpURLConnection)  u.openConnection ();
			huc.setRequestMethod ("GET");
			huc.connect();
			if (matchedPattern == 1)
				return true;

			return false;
		}
		catch(Exception e1){
			if (matchedPattern == 1)
				return false;

			return true;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		EXPRurl = (Expression<String>) expr[0];
                this.matchedPattern = arg1;
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "%string% is (online|working|not offline|good|not bad|not 404|not down)";
	}

}