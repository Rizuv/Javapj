package me.rizuv;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JSONManager {

    private File f;
    private volatile JsonNode js;
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

    public synchronized byte[] read_data_bytes(String key){
        JsonNode node = js.get(key);
        if (node.isNull()) return new byte[0];

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

    public synchronized String read_data(String key){
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

    public synchronized void update(byte[] data) throws IOException {
        JsonNode changes = mapper.readTree(data);
        JsonNode root = mapper.readTree(f);      
        merge(root, changes);                    
        mapper.writerWithDefaultPrettyPrinter().writeValue(f, root); 
        this.js = root;                          
    }

    public synchronized void update(String jsoncontent) throws IOException{
        JsonNode changes = mapper.readTree(jsoncontent);
        JsonNode root = mapper.readTree(f);      
        merge(root, changes);                    
        mapper.writerWithDefaultPrettyPrinter().writeValue(f, root); 
        this.js = root;
    }

    //Szukanie odpowiedniego pola idac od poczatku drzewa jsona glebiej po wezlach
    private static void merge(JsonNode target, JsonNode changes) {
        if (!target.isObject() || !changes.isObject()) return;
        ObjectNode obj = (ObjectNode) target;

    changes.fields().forEachRemaining(e -> {
        JsonNode oldVal = obj.get(e.getKey());
        if (oldVal == null) return;                       
        if (oldVal.isObject() && e.getValue().isObject()) {
            merge(oldVal, e.getValue());                  
        } else {
            obj.set(e.getKey(), e.getValue());            
        }
    });
    }

    public synchronized void changeValue(String key, String value) throws IOException{
        String json = "{\"" + key + "\":\"" + value + "\"}";
        update(json);
    }
}

