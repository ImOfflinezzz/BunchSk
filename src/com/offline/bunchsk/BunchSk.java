package com.offline.bunchsk;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import com.offline.bunchsk.utils.ClassFinder;
import com.offline.bunchsk.utils.RegisterOptions;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.wh1lec0d3r_.bunchsk.core.client.CoreClient;
import com.wh1lec0d3r_.bunchsk.core.client.config.ConfigData;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BunchSk extends JavaPlugin {   

    static BunchSk bunchSk;

    private ConfigData configData;
    private CoreClient coreClient;

    private File configFile = new File(this.getDataFolder(), "config.json");

    @Override
    public void onEnable() {

        bunchSk = this;

        Skript.registerAddon(this);

        try {

            registerElements();

        } catch (IOException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {

            Logger.getLogger(BunchSk.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("BunchSk couldn't register stuff");
        }

        this.loadConfig();
        this.coreClient = new CoreClient(this.configData);
    }
    
    @Override
    public void onDisable() {
        
    }

    private void loadConfig() {

        if (this.getConfigFile().exists()) {
            System.out.println("Loading from file...");
            configData = new ConfigData();
            configData = configData.readConfig(ConfigData.class);
        } else {
            System.out.println("Creating file...");
            configData = new ConfigData();

            try {
                if(!this.getDataFolder().exists())
                    this.getDataFolder().mkdir();

                if(!this.getConfigFile().exists())
                    this.getConfigFile().createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }


            configData.saveConfig();
        }
        System.out.println("File loaded successfully");
    }
    
    public void registerElements() throws IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
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
        
        if (classes != null) {

            for (Class c : classes) {

                if(c.isAnnotationPresent(RegisterOptions.class)) {

                    Annotation ann = c.getAnnotation(RegisterOptions.class);
                    RegisterOptions ro = (RegisterOptions) ann;
                    int asd = 1;

                    if (ro.PluginDepend()!="None" && Bukkit.getPluginManager().getPlugin(ro.PluginDepend()) == null)
                        asd = 0;

                    if (asd == 0) {

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

            System.out.println("BunchSk successfully registered " + effcount + " effects, " + exprcount + " expressions, " + evtcount + " events, " + condcount + " conditions");
        }
    }

    public ConfigData getConfigData() {
        return configData;
    }

    public static BunchSk getBunchSk() {
        return bunchSk;
    }

    public CoreClient getCoreClient() {
        return coreClient;
    }

    public File getConfigFile() {
        return configFile;
    }
}
