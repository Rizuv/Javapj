package me.rizuv;

import java.util.Random;
import me.rizuv.*;

public class Client {

    private ConfigManager cfg = new ConfigManager("config.json");
    private int UUID_length = Integer.parseInt(cfg.read_data("UUID-length").toString());

    public Client(){
        this.create_uniqueid(UUID_length);
    }

    public Client(String UUID){

    }

    private String create_uniqueid(int length){
        char[] characters = cfg.read_data("UUID-chars").toString().toCharArray();
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
