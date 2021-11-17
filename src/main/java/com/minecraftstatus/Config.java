package com.minecraftstatus;

import java.io.*;
import java.nio.file.*;
import java.util.HashMap;

import com.google.gson.Gson;

public class Config {

    private final static Gson gson = new Gson();
    private static HashMap<String, Object> contents;

    static {
        contents = new HashMap<>();
        checkFile();
        tryLoad();
        registerWatchService();
    }

    public static Object get(String key) {
        return contents.get(key);
    }

    public static String getString(String key) {
        return (String) get(key);
    }

    public static void set(String key, Object value) {
        contents.put(key, value);
        trySave();
    }

    private static void registerWatchService() {
        WatchKey watchKey = tryGetWatchKey();
        startWatchListener(watchKey);
    }

    private static void startWatchListener(WatchKey watchKey) {
        new Thread(() -> runWatchListener(watchKey)).start();
    }

    private static void runWatchListener(WatchKey watchKey) {
        while(true) {
            for (WatchEvent<?> event : watchKey.pollEvents()) {
                if(((Path) event.context()).equals(Path.of(Main.CONFIG_PATH))){
                    tryLoad();
                    break;
                }
            }
        }
    }

    private static WatchKey tryGetWatchKey() {
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            return Path.of("./").register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void checkFile() {
        try {
            File file = new File(Main.CONFIG_PATH);
            if(file.createNewFile())
                Console.info("Created New File");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void tryLoad() {
        try {
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void load() throws IOException {
        Console.info("Loading File");
        String data = new String(Files.readAllBytes(Path.of(Main.CONFIG_PATH)));
        contents = gson.fromJson(data, contents.getClass());
    }

    private static void trySave() {
        try {
            save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void save() throws FileNotFoundException {
        Console.info("Saving File");
        String data = gson.toJson(contents);
        PrintWriter writer = new PrintWriter(Main.CONFIG_PATH);
        writer.print(data);
        writer.close();
    }

    public static void close() {
        trySave();
    }
    
}
