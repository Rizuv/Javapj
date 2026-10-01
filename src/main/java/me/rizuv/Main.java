package me.rizuv;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        JSONManager cfg = new JSONManager("config.json");
        //System.out.println(cfg.read_data(("UUID-chars")));
        byte[] data = "{\"UUID-length\":\"dala\", \"UUID-chars\":[\"1\", \"2\"]}".getBytes();
        cfg.update(data);
        cfg.changeValue("UUID-length", "15");
        //System.out.println(cfg.read_data("UUID-chars"));
    }
}