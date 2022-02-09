package com.gameserverlive.configs;

import java.util.HashMap;

import com.gameserverlive.Console;

public class SettingsConfig extends DefaultConfig {

    public SettingsConfig() {
        super("config.json");
        Console.info("config.json geladen");
    }

    // public final static DefaultConfig CONFIG = new DefaultConfig("config.json");

    @Override
    protected HashMap<String, String> getDefaultValue(HashMap<String, String> map) {
        map.put("token", "Put your token here");
        map.put("prefix", "!");
        map.put("min_exit_time", "10");
        map.put("exit_time", "10");

        Console.info("Created Config File");
        Console.info("Please put the %red%Token%reset% into the %red%config file%reset%!");

        return map;
    }

    @Override
    public boolean shouldExitAfterFileFill() {
        return true;
    }
}
