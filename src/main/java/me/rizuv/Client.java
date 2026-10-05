package me.rizuv;

import java.util.Random;

public class Client {

    private final JSONManager cfg = new JSONManager("config.json");
    private final int UUID_length = Integer.parseInt(cfg.read_data(("UUID-length")));

    public Client(){
        create_uniqueid(UUID_length);
    }

    public Client(String UUID){

    }

    private String create_uniqueid(int length){
        char[] characters = cfg.read_data("UUID-chars").toCharArray();
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < length; i++){
            int index = r.nextInt(UUID_length);
            sb.append(characters[index]);
        }
        return sb.toString();
    }

    public String get_client(){
        return "3";
    }
}
