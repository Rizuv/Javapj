package me.rizuv;

import java.util.Random;

public class Client {

    private final ConfigManager cfg = new ConfigManager("config.json");
    private final int UUID_length = Integer.parseInt(cfg.get_data(("UUID-length")));

    public Client(){
        this.create_uniqueid(UUID_length);
    }

    public Client(String UUID){

    }

    private String create_uniqueid(int length){
        char[] characters = cfg.get_data("UUID-chars").toCharArray();
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < length; i++){
            int index = r.nextInt(14);
            sb.append(characters[index]);
        }
        return sb.toString();
    }

    public String get_client(){
        return "3";
    }
}
