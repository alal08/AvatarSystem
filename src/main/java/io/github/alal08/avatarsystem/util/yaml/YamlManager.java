package io.github.alal08.avatarsystem.util.yaml;

import com.google.common.base.MoreObjects;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;

public class YamlManager {

    private final File file;
    private final String dir;

    public YamlManager(String dir, String name) {
        dir = System.getProperty("user.dir") + "\\" + dir;
        this.file = new File(dir + "\\" + name + ".yml");
        this.dir = dir;
        this.create();
    }

    private void create() {
        File folder = new File(this.dir);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException var3) {
                throw new RuntimeException(var3);
            }
        }

    }

    private void save(Map<String, Object> yamlMap) {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(this.file.getPath());
        } catch (IOException var5) {
            throw new RuntimeException(var5);
        }

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        yaml.dump(yamlMap, fileWriter);
    }

    public Map<String, Object> getValues() {
        FileReader fileReader;
        try {
            fileReader = new FileReader(this.file.getPath());
        } catch (FileNotFoundException var4) {
            throw new RuntimeException(var4);
        }

        Yaml yaml = new Yaml();
        Map<String, Object> yamlMap = yaml.load(fileReader);
        if (yamlMap == null) {
            yamlMap = new LinkedHashMap();
        }

        return yamlMap;
    }

    public Object get(String key) {
        return this.getValues().get(key);
    }

    public void put(String key, Object value) {
        Map<String, Object> yamlMap = new LinkedHashMap();
        if (this.getValues() != null) {
            yamlMap = this.getValues();
        }

        (yamlMap).put(key, value);
        this.save(yamlMap);
    }
}
