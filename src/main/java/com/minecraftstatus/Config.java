package com.minecraftstatus;

import java.io.*;
import java.nio.file.*;
import java.util.HashMap;

import com.google.gson.Gson;

public class Config {

    private final Gson gson = new Gson();
    private HashMap<String, Object> contents;
    String configPath;

    /*
    *   Create a confic wrapper for the file in the path
    */
    public Config(String path) {
        configPath = path;
        contents = new HashMap<>();
        checkFile();
        tryLoad();
        registerWatchService();
    }

    /*
    *   Create a confic wrapper for the file
    */
    public Config(File file) {
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
        while(true) {
            for (WatchEvent<?> event : watchKey.pollEvents()) {
                Console.test("File Changed");

                File file = ((Path) event.context()).toFile();
                Console.test("Changed file: " + file.getAbsolutePath());
                Console.test("Config file:" + new File(configPath).getAbsolutePath());
                if(file.equals(new File(configPath))){
                    Console.test("Config File Changed");
                    tryLoad();
                    break;
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
            HashMap temp = gson.fromJson(json, HashMap.class);
            if(temp == null || temp.isEmpty())
                return false;
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void createFile() {
        try {
            new File(configPath).createNewFile();
            fillFile();
            Console.warn("Created New File");
            Console.warn("Please put the %red%Token%reset% into the %red%config file%reset%!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private void fillFile() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(configPath);
        HashMap<String, String> map = new HashMap<>();
        map.put("token", "put your token here");
        writer.print(gson.toJson(map));
        writer.close();
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
            e.printStackTrace();
        }
    }

    private void save() throws FileNotFoundException {
        Console.info("Saving File");
        String data = gson.toJson(contents);
        PrintWriter writer = new PrintWriter(configPath);
        writer.print(data);
        writer.close();
    }
 
    public void close() {
        trySave();
    }

    private String prettifyJSON(String json) {
        return json.replaceAll("{", "{\n")
                .replaceAll("}", "\n}")
                .replaceAll(",", ",\n");
    }
    
}
