package me.rizuv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

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

    public byte[] read_data_bytes(String key){
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

    public void write_data_bytes(byte[] data_stream){
        try{
        JsonNode json_data = mapper.readTree(data_stream);
        Iterator<String> fieldsIteratorInInputData = json_data.fieldNames();
        List<String> fields = new ArrayList<>();
        getAllKeysFromTree(js, fields);

        Map<String, Object> json = mapper.readValue(f, new TypeReference<Map<String, Object>>(){});
        Writer writer = new FileWriter(f, false);

        while(fieldsIteratorInInputData.hasNext()){
            String key = fieldsIteratorInInputData.next();
            if(fields.contains(key)){
                json.replace(key, json_data.get(key));
            }
        }
        String jsonString = mapper.writeValueAsString(json);
        System.out.println(jsonString);
        writer.write(jsonString);
        writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void getAllKeysFromTree(JsonNode node, List<String> keys){
        if(node.isObject()){
            Iterator<Entry<String, JsonNode>> fields = node.fields();
            fields.forEachRemaining(field -> {
                keys.add(field.getKey());
                getAllKeysFromTree((JsonNode) field.getValue(), keys);
            });
        } else if(node.isArray()){
            ArrayNode array = (ArrayNode) node;
            array.forEach(field -> {
                getAllKeysFromTree(field, keys);
            });
        }
        System.out.println(keys.toString());
    }
}
