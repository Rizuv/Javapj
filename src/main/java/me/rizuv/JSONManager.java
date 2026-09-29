package me.rizuv;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONManager {

    private File f;
    private JsonNode js;
    private final ObjectMapper mapper = new ObjectMapper();
    private final byte TYPE_STRING = 0x01;
    private final byte TYPE_ARRAY = 0x02;

    public JSONManager(String path){
        try {
            this.f = new File(path);
            js = mapper.readTree(f);
        } catch (IOException e) {
            System.out.println("Nieznaleziona sciezka pliku!");
        }
    }

    private byte[] read_data_bytes(String key){
        JsonNode node = js.get(key);
        if (node.isNull() || node == null) return new byte[0];

        byte type;
        String text;

        if(node.isArray()){
            type = TYPE_ARRAY;
            StringBuilder sb = new StringBuilder();
            for (JsonNode j : node){
                sb.append(j.asText());
            }
            text = sb.toString();
        } else {
            type = TYPE_STRING;
            text = node.asText();
        }
        byte[] data = text.getBytes();
        byte[] usable_data = new byte[data.length+1];
        usable_data[0] = type;
        System.arraycopy(data, 0, usable_data, 1, data.length);
        return usable_data;
    }

    public String read_data(String key){
        JsonNode node = js.get(key);
        String text;

        if (node.isArray()){
            StringBuilder sb = new StringBuilder();
            for(JsonNode j : node) sb.append(j.asText());
            text = sb.toString();
        } else{
            text = node.asText();
        }
        return text;
    }
}
