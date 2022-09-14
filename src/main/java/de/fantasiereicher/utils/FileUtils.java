package eu.syplex.utils;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileUtils {
    private File file = new File("plugins/CoinsAPI/MySQL_login.yml");

    private YamlConfiguration cfg = YamlConfiguration.loadConfiguration(this.file);

    public FileUtils() {
        if (!this.file.exists())
            try {
                this.file.createNewFile();
            } catch (IOException iOException) {}
        this.cfg.options().header("Plugin CoinsAPI was coded by SYPLEXEU - ExceptionThread");
        this.cfg.addDefault("MySQL.hostname", "localhost");
        this.cfg.addDefault("MySQL.port", "3306");
        this.cfg.addDefault("MySQL.database", "coins");
        this.cfg.addDefault("MySQL.username", "root");
        this.cfg.addDefault("MySQL.password", "gixizixe");
        this.cfg.options().copyDefaults(true);
        this.cfg.options().copyHeader(true);
        saveFile();
    }

    public void saveFile() {
        try {
            this.cfg.save(this.file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public File getFile() {
        return this.file;
    }

    public YamlConfiguration getConfig() {
        return this.cfg;
    }

    public void setConfig(YamlConfiguration cfg) {
        this.cfg = cfg;
    }
}
