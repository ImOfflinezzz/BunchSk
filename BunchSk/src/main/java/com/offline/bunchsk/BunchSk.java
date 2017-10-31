package com.offline.bunchsk;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import com.offline.bunchsk.utils.ClassFinder;
import com.offline.bunchsk.utils.DocsGenerator;
import com.offline.bunchsk.utils.RegisterOptions;
import com.wh1lec0d3r_.bunchsk.core.client.CoreClient;
import com.wh1lec0d3r_.bunchsk.core.client.config.ConfigData;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

//import com.wh1lec0d3r_.bunchsk.core.client.CoreClient;
//import com.wh1lec0d3r_.bunchsk.core.client.config.ConfigData;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BunchSk extends JavaPlugin {

    static BunchSk bunchSk;
    
    private static boolean DEBUG_MODE = true;

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
                System.out.println("█ BunchSk couldn't register stuff");
            }
        if (!DEBUG_MODE) {
            this.coreClient = new CoreClient(this.configData);
        }
        this.loadConfig();

    }

    @Override
    public void onDisable() {

    }

    private void loadConfig() {

        if (this.getConfigFile().exists()) {

            System.out.println("█ Config » Loading data from config file");
            configData = new ConfigData();
            configData = configData.readConfig(ConfigData.class);

        } else {

            System.out.println("█ Client » Creating config file");
            configData = new ConfigData();

            try {
                if (!this.getDataFolder().exists()) {
                    this.getDataFolder().mkdir();
                }

                if (!this.getConfigFile().exists()) {
                    this.getConfigFile().createNewFile();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            configData.saveConfig();
        }

        System.out.println("█ Client » Config loaded successefully");
    }

    public void registerElements() throws IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        int effectCount = 0,
                expressionCount = 0,
                eventCount = 0,
                conditionCount = 0,
                asd = 0;
        DocsGenerator.createDocs();
        System.out.println("BunchSK » Starting » Registering stuff");

        Method method = JavaPlugin.class.getDeclaredMethod("getFile");
        method.setAccessible(true);
        File file = (File) method.invoke(this);

        Set<Class<?>> classes = ClassFinder.getClasses(file, "com.offline.bunchsk");

        if (classes != null) {

            for (Class clazz : classes) {

                if (clazz.isAnnotationPresent(RegisterOptions.class)) {

                    Annotation annotation = clazz.getAnnotation(RegisterOptions.class);

                    RegisterOptions registerOptions = (RegisterOptions) annotation;
                    asd = 1;

                    if (!Objects.equals(registerOptions.PluginDepend(), "None") && Bukkit.getPluginManager().getPlugin(registerOptions.PluginDepend()) == null) {
                        asd = 0;
                    }

                    if (asd == 0) {

                        switch (registerOptions.RegType()) {

                            case "EFFECT":
                                Skript.registerEffect(clazz, registerOptions.Syntaxes());
                                effectCount++;
                                DocsGenerator.writeDocs(registerOptions.Name(), registerOptions.Syntaxes(), "Effect");
                                break;

                            case "CONDITION":
                                Skript.registerCondition(clazz, registerOptions.Syntaxes());
                                conditionCount++;
                                DocsGenerator.writeDocs(registerOptions.Name(), registerOptions.Syntaxes(), "Condition");
                                break;

                            case "EVENT":
                                Skript.registerEvent(registerOptions.Name(), SimpleEvent.class, clazz, registerOptions.Syntaxes());
                                eventCount++;
                                DocsGenerator.writeDocs(registerOptions.Name(), registerOptions.Syntaxes(), "Event");
                                break;

                            case "EXPRESSION":
                                Skript.registerExpression(clazz, registerOptions.ExprClass(), registerOptions.ExprType(), registerOptions.Syntaxes());
                                expressionCount++;
                                DocsGenerator.writeDocs(registerOptions.Name(), registerOptions.Syntaxes(), "Expression");
                                break;
                        }
                    }
                }
            }

            System.out.println("█ BunchSK » Register » Effects: " + effectCount);
            System.out.println("█ BunchSK » Register » Expressions: " + expressionCount);
            System.out.println("█ BunchSK » Register » Events: " + eventCount);
            System.out.println("█ BunchSK » Register » Conditions: " + conditionCount);
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
