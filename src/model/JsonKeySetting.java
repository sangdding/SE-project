package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class JsonKeySetting implements SettingInfo{

    private JSONObject keySet;
    private JSONObject defaultKeyset;
    private ObjectMapper objectMapper;

    public JsonKeySetting() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        try {
            Reader readerCustom = new FileReader("src/keySet.json");
            Reader readerDefault = new FileReader("src/defaultKeySet.json");
            keySet = (JSONObject) parser.parse(readerCustom);
            defaultKeyset = (JSONObject) parser.parse(readerDefault);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashMap<String, Integer> getKeyList() {
        objectMapper = new ObjectMapper();
        HashMap<String, Integer> returnKeySet = null;
        try {
            returnKeySet = objectMapper.readValue(keySet.toJSONString(), new TypeReference<Map<String, Integer>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnKeySet;
    }

    @Override
    public void setKeyList(HashMap<String, Integer> changeKey) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fw = new FileWriter("src/keySet.json");
            for(String key : changeKey.keySet()) {
                keySet.replace(key, changeKey.get(key)); // json 파일에 키 정보 저장 후
            }
            gson.toJson(keySet, fw); // 로컬에 저장
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashMap<String, Integer> getDefaultKeySet() {
        HashMap<String, Integer> returnDefaultKeySet = null;
        try {
            returnDefaultKeySet = objectMapper.readValue(defaultKeyset.toJSONString(), new TypeReference<Map<String, Integer>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnDefaultKeySet;
    }

    @Override
    public void setDefaultKeySet() {
        try {
            objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File("src/keySet.json"), defaultKeyset);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
