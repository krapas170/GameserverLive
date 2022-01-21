package com.gameserverlive.configs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameserverlive.Console;
import com.google.gson.Gson;

public class DefaultConfig {

    private final Gson gson = new Gson();
    private HashMap<String, Object> contents;
    String configPath;
    private boolean fileChanged = false;

    /*
    *   Create a confic wrapper for the file in the path
    */
    public DefaultConfig(String path) {
        configPath = path;
        contents = new HashMap<>();
        checkFile();
        tryLoad();
        registerWatchService();
    }

    /*
    *   Create a confic wrapper for the file
    */
    public DefaultConfig(File file) {
        this(file.getAbsolutePath());
    }

    /*
    *   Get the config-value to the specified key
    */
    public Object get(String key) {
        return contents.get(key);
    }

    /*
    *   Get the config-value as string to the specified key
    *   Make sure that the value is a String, else the is an Error
    */
    public String getString(String key) {
        return (String) get(key);
    }

    /*
    *   Set the config-value for the specific key
    */
    public void set(String key, Object value) {
        contents.put(key, value);
        trySave();
    }

    private void registerWatchService() {
        WatchKey watchKey = tryGetWatchKey();
        startWatchListener(watchKey);
    }

    private void startWatchListener(WatchKey watchKey) {
        new Thread(() -> runWatchListener(watchKey)).start();
    }

    private void runWatchListener(WatchKey watchKey) {
        boolean first = true;
        while(true) {
            for (WatchEvent<?> event : watchKey.pollEvents()) {
                Console.test("File Changed");

                File file = ((Path) event.context()).toFile();
                if(file.equals(new File(configPath))){
                    if(fileChanged) {
                        fileChanged = false;
                        first = false;
                    } else if(first) {
                        Console.info("Config File Changed");
                        tryLoad();
                        first = false;
                    } else {
                        first = true;
                    }
                }
            }
        }
    }

    private WatchKey tryGetWatchKey() {
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            return Path.of("./").register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void checkFile() {
        if(!new File(configPath).exists()) {
            createFile();
        } else if (!isFileFormCorrect()) {
            new File(configPath).delete();
            createFile();
        }
    }

    private boolean isFileFormCorrect() {
        try {
            String json = new String(Files.readAllBytes(Path.of(configPath)));
            HashMap<String, String> temp = gson.fromJson(json, HashMap.class);
            if(temp == null || temp.isEmpty()) {
                return false;
            }
            else {
                return true;
            }
        } catch ( Exception e) {
            return false;
        }
    }

    private void createFile() {
        try {
            new File(configPath).createNewFile();
            fillFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillFile() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(configPath);
        writer.print(gson.toJson(getDefaultValue()));
        writer.close();
        if(shouldExitAfterFileFill())
            System.exit(0);
    }

    public boolean shouldExitAfterFileFill() {
        return false;
    }

    private void tryLoad() {
        try {
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load() throws IOException {
        Console.info("Loading File");
        String data = new String(Files.readAllBytes(Path.of(configPath)));
        contents = gson.fromJson(data, new HashMap<String, Object>().getClass());
    }

    private void trySave() {
        try {
            save();
        } catch (FileNotFoundException e) {
            Console.warn("Failed to save file");
            fileChanged = false;
            e.printStackTrace();
        }
    }

    private void save() throws FileNotFoundException {
        Console.info("Saving File");
        fileChanged = true;
        String json = gson.toJson(contents);
        PrintWriter writer = new PrintWriter(configPath);
        writer.print(prettifyJSON(json));
        writer.close();
    }
 
    public void close() {
        trySave();
    }

    private String prettifyJSON(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object obj = mapper.readValue(json, Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json.replaceAll("{", "{\n")
                .replaceAll("}", "\n}")
                .replaceAll(",", ",\n");
    }

    protected HashMap<String, String> getDefaultValue() {
        return getDefaultValue(new HashMap<>());
    }

    protected HashMap<String, String> getDefaultValue(HashMap<String, String> map) {
        return map;
    }
    
}
