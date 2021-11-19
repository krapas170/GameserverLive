package com.minecraftstatus.configs;

import java.util.HashMap;

import com.minecraftstatus.Console;

public class SettingsConfig extends DefaultConfig{

    public SettingsConfig() {
        super("config.json");
    }

    @Override
    protected HashMap<String, String> getDefaultValue(HashMap<String, String> map) {
        map.put("token", "Put your token here");
        map.put("prefix", "!");
        
        Console.warn("Created Config File");
        Console.warn("Please put the %red%Token%reset% into the %red%config file%reset%!");
        
        System.exit(0);

        return map;
    }

}
