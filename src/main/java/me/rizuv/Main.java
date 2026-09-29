package me.rizuv;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        ConfigManager cfg = new ConfigManager("config.json");
        System.out.println(cfg.get_data("UUID-chars"));
    }
}