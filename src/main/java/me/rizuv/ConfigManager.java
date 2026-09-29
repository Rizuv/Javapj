package me.rizuv;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class ConfigManager {

    private File f;
    private JsonNode js;
    private final ObjectMapper mapper = new ObjectMapper();

    public ConfigManager(String path){
        try {
            this.f = new File(path);
            js = mapper.readTree(f);
        } catch (IOException e) {
            System.out.println("Nieznaleziona sciezka pliku!");
        }
    }

    private byte[] read_data(String key){
        Object obj = (Object) js.get(key);
        List<Byte> data = new ArrayList<>();
        if(obj instanceof String){
            data.add((byte)0x01);
            byte[] object_data = ((String) obj).getBytes();
            for(byte b : object_data){
                data.add(b);
            }
        } else if(obj instanceof ArrayNode){
            data.add((byte)0x02);
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while(i < ((ArrayNode) obj).size()){
                sb.append(((ArrayNode) obj).get(i).toString());
                i++;
            }
            byte[] object_data = sb.toString().replaceAll("\"", "").getBytes();
            for(byte b : object_data){
                data.add(b);
            }
        }
        byte[] returned_data = new byte[data.size()];
        for(int i = 0; i < data.size(); i++){
            returned_data[i] = data.get(i);
        }
        return returned_data;
    }

    public String get_data(String key){
        byte[] bytes_data = read_data(key);
        StringBuilder to_return = new StringBuilder();
        switch (bytes_data[0]){
            case 0x01:
                to_return.append(new String(bytes_data, 1, bytes_data.length - 1, StandardCharsets.UTF_8));
            case 0x02:
                to_return.append(new String(bytes_data, 1, bytes_data.length - 1, StandardCharsets.UTF_8));
        }
        return to_return.toString();
    }
}
