package fr.ariouz.ultimateutilities.utils;

import com.sun.javafx.scene.shape.PathUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ConfigUtils {

    private Plugin plugin;
    private YamlConfiguration yamlConfiguration;
    private final File file;

    public ConfigUtils(Plugin plugin, String fileName){
        file = new File(plugin.getDataFolder(), fileName);
        loadConfiguration(file);
    }

    public ConfigUtils(Plugin plugin, YamlConfiguration yamlConfiguration){
        this.yamlConfiguration = yamlConfiguration;
        this.file = new File(plugin.getDataFolder(), yamlConfiguration.getName());
    }

    public Object get(String path){
        if(yamlConfiguration.contains(path)){
            return yamlConfiguration.get(path);
        }
        return null;
    }

    public String getString(String path){
        if(yamlConfiguration.contains(path)){
            return yamlConfiguration.getString(path);
        }
        return null;
    }

    public Integer getInt(String path){
        if(yamlConfiguration.contains(path)){
            return yamlConfiguration.getInt(path);
        }
        return 0;
    }

    public Double getDouble(String path){
        if(yamlConfiguration.contains(path)){
            return yamlConfiguration.getDouble(path);
        }
        return 0.0;
    }

    public List<?> getList(String path){
        if(yamlConfiguration.contains(path)){
            return yamlConfiguration.getList(path);
        }
        return null;
    }

    public boolean getBoolean(String path){
        if(yamlConfiguration.contains(path)){
            return yamlConfiguration.getBoolean(path);
        }
        return false;
    }

    public void saveCalendar(String path, Calendar calendar){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String formatter = format.format(calendar.getTime());
        set(path, formatter);
    }

    public Calendar getStringToCalendar(String path){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        Date date = null;
        try {
            date = sdf.parse(getString(path));
        } catch (ParseException e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED +" Erreur lors de la recuperation de la date " + getString(path));
        }
        Calendar cal = Calendar.getInstance();

        assert date != null;
        cal.setTime(date);

        return cal;
    }

    public void saveLocation(String path, Location location){
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String locBuilder = decimalFormat.format(location.getX()) + ":" +
                decimalFormat.format(location.getY()) + ":" +
                decimalFormat.format(location.getZ()) + ":" +
                decimalFormat.format(location.getYaw()) + "f" + ":" +
                decimalFormat.format(location.getPitch()) + "f" + ":w-" + location.getWorld().getName();
        set(path, locBuilder);
        saveConfiguration();
    }

    public Location getLocationFromString(String path){
        String stringLoc = this.getYamlConfiguration().getString(path);

        String[] args = stringLoc.split(":");
        World world = null;
        double[] locs = new double[3];
        float[] yp = new float[2];

        for(int i = 0; i < args.length; i++){
            if(args[i].contains("w-")){
                world = Bukkit.getWorld(args[i].replaceFirst("w-", ""));
                continue;
            }
            if(args[i].contains("f")){
                yp[i-3] = Float.parseFloat(args[i].replaceAll(",", "."));
                continue;
            }
            locs[i] = Double.parseDouble(args[i].replaceAll(",", "."));
        }

        if(world == null){
            throw new NullPointerException("World is null");
        }

        return new Location(world, locs[0], locs[1], locs[2], yp[0], yp[1]);
    }

    public void set(String path, Object value){
        yamlConfiguration.set(path, value);
        saveConfiguration();
    }

    public void replace(String path, Object value){
        set(path ,value);
    }

    public void saveConfiguration(){
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadConfiguration(File file){
        yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public void reloadConfiguration(){
        yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public YamlConfiguration getYamlConfiguration() {
        return yamlConfiguration;
    }

    public File getFile() {
        return file;
    }
}
