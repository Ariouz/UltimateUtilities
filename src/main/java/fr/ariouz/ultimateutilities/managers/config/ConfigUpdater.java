package fr.ariouz.ultimateutilities.managers.config;

import fr.ariouz.ultimateutilities.UltimateUtilities;
import fr.ariouz.ultimateutilities.utils.ConfigUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ConfigUpdater {

    private final UltimateUtilities ultimateUtilities;

    public ConfigUpdater(UltimateUtilities ultimateUtilities){
        this.ultimateUtilities = ultimateUtilities;
    }

    public void update(String path){
        if (!new File(ultimateUtilities.getDataFolder() + "/"+path).exists()) {
            ultimateUtilities.saveResource(path, false);
        }

        File file = new File(ultimateUtilities.getDataFolder() + "/"+path);
        YamlConfiguration externalYamlConfig = YamlConfiguration.loadConfiguration(file);

        InputStreamReader defConfigStream = new InputStreamReader(ultimateUtilities.getResource(path), StandardCharsets.UTF_8);
        YamlConfiguration internalConfig = YamlConfiguration.loadConfiguration(defConfigStream);

        for (String string : internalConfig.getKeys(true)) {
            if (!externalYamlConfig.contains(string)) {
                System.out.println("[UltimateUtilities] Updated \"" + string + "\" in " + path);
                externalYamlConfig.set(string, internalConfig.get(string));
            }
        }
        try {
            externalYamlConfig.save(file);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

}
