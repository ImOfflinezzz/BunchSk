package com.offline.bunchsk;

/*
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;

import com.offline.bunchsk.effects.EffBroadcastPerm;
import com.offline.bunchsk.effects.EffSurfaceHigh;
import com.offline.bunchsk.effects.EffSurfaceNear;
import com.offline.bunchsk.effects.EffPickupState;

import com.offline.bunchsk.conditions.CondCanPickup;*/

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import com.offline.bunchsk.utils.ClassFinder;
import com.offline.bunchsk.utils.RegisterOptions;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import static java.lang.System.console;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BunchSk extends JavaPlugin {   
    
    @Override
    public void onEnable(){
        Skript.registerAddon(this);
        try {    
            registerElements();
        } catch (IOException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(BunchSk.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("BunchSk couldn't register stuff");
        }
        
        }
            /* Classes.registerClass(new ClassInfo<PluginCheck>(PluginCheck.class, "pluginenabled").parser(new Parser<PluginCheck>() {
            @Override
            public String getVariableNamePattern() {
            return ".+";
            }
            
            @Override
            public PluginCheck parse(String s, ParseContext cont) {
            try {
            return PluginCheck.valueOf(s.replace(" ", "_").trim().toUpperCase());
            } catch (IllegalArgumentException e) {
            return null;
            }
            }
            
            @Override
            public String toString(PluginCheck pc, int i) {
            return pc.name().replace("_", " ").toLowerCase();
            }
            
            @Override
            public String toVariableNameString(PluginCheck pc) {
            return pc.name().replace("_", " ").toLowerCase();
            }
            }));
            Skript.registerEffect(EffSurfaceHigh.class, "surf[ace] %player% to [the] high[est] [loc[ation]]");
            Skript.registerEffect(EffSurfaceNear.class, "surf[ace] %player% to [the] near[est] [loc[ation]]");
            Skript.registerEffect(EffPickupState.class, "allow %entity% to pick[ ]up","disallow %entity% to pick[ ]up");
            Skript.registerEffect(EffBroadcastPerm.class, "broad[cast] %string% to [all] players with perm[ission] %string%");
            Skript.registerExpression(ExprBunchVer.class, String.class, ExpressionType.SIMPLE, "bunch[sk] ver[sion]");
            Skript.registerCondition(CondCanPickup.class, "%entity% can pick[ ]up", "%entity% can't pick[ ]up");
            if (Bukkit.getServer().getVersion().contains("1.9")) {
            Skript.registerCondition(CondCanPickup.class, "%entity% is collidable", "%entity% isn't collidable");
            }*/
    
    @Override
    public void onDisable(){
        
    }
    
    public void registerElements() throws IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        
        int effcount = 0;
        int exprcount = 0;
        int evtcount = 0;
        int condcount = 0;
        
        
        System.out.println("BunchSk registering stuff");
        JavaPlugin plugin = (JavaPlugin) getServer().getPluginManager().getPlugin("BunchSk");
        Method getFileMethod = JavaPlugin.class.getDeclaredMethod("getFile");
        getFileMethod.setAccessible(true);
        File file = (File) getFileMethod.invoke(this);
        
        Set<Class<?>> classes = ClassFinder.getClasses(file, "com.offline.bunchsk");
        
        if (classes != null){
            for (Class c : classes){
                if(c.isAnnotationPresent(RegisterOptions.class)){
                    Annotation ann = c.getAnnotation(RegisterOptions.class);
                    RegisterOptions ro = (RegisterOptions) ann;
                    int asd = 1;
                    if (ro.PluginDepend()!="None" && Bukkit.getPluginManager().getPlugin(ro.PluginDepend()) == null) asd=0;
                    if (asd==0){
                        switch (ro.RegType()) {
                            case "EFFECT":
                                Skript.registerEffect(c, ro.Syntaxes());
                                effcount++;
                                break;
                            case "CONDITION":
                                Skript.registerCondition(c, ro.Syntaxes());
                                condcount++;
                                break;
                            case "EVENT":
	                        Skript.registerEvent(ro.Name(), SimpleEvent.class, c, ro.Syntaxes());
		                evtcount++;
		                break;
	                    case "EXPRESSION":
	            	        Skript.registerExpression(c,ro.ExprClass(),ro.ExprType(),ro.Syntaxes());
		                exprcount++;
                                break;
                        }
                    }
                }    
            }   
            System.out.println("BunchSk successfully registered "+effcount+" effects, "+exprcount+" expressions, "+evtcount+" events, "+condcount+" conditions");
        }
    }
}
