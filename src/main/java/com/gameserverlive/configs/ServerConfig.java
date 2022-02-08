package com.gameserverlive.configs;

import java.util.HashMap;

import com.gameserverlive.Console;

public class ServerConfig extends DefaultConfig {

    public ServerConfig() {
        super("server.json");
        Console.info("gameserver.db geladen");
    }
}