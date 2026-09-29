package me.rizuv;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        JSONManager cfg = new JSONManager("config.json");
        System.out.println(cfg.read_data(("UUID-chars")));
    }
}