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

public class JsonSetting implements SettingInfo{

    private JSONObject keySet;
    private JSONObject defaultKeyset;
    private JSONObject displaySet;
    private ObjectMapper objectMapper;

    public JsonSetting() {
        JSONParser parser = new JSONParser();
        try {
            Reader readerCustom = new FileReader("src/setting/keySet.json");
            Reader readerDefault = new FileReader("src/setting/defaultKeySet.json");
            Reader readerDisplay = new FileReader("src/setting/displayInfo.json");
            keySet = (JSONObject) parser.parse(readerCustom);
            defaultKeyset = (JSONObject) parser.parse(readerDefault);
            displaySet = (JSONObject) parser.parse(readerDisplay);
        } catch (FileNotFoundException e) {
            System.out.println("파일이 존재하지 않습니다.");
        } catch (IOException e) {
            System.out.println("입출력 에러");
        } catch (ParseException e) {
            System.out.println("파싱 에러");
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
            FileWriter fw = new FileWriter("src/setting/keySet.json");
            for(String key : changeKey.keySet()) {
                keySet.replace(key, changeKey.get(key)); // json 파일에 키 정보 저장 후
            }
            gson.toJson(keySet, fw); // 로컬에 저장
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.out.println("입출력 에러");
        }
    }

    @Override
    public HashMap<String, Integer> getDefaultKeySet() {
        HashMap<String, Integer> returnDefaultKeySet = null;
        try {
            returnDefaultKeySet = objectMapper.readValue(defaultKeyset.toJSONString(), new TypeReference<Map<String, Integer>>() {
            });
        } catch (IOException e) {
            System.out.println("입출력 에러");
        }
        return returnDefaultKeySet;
    }

    @Override
    public void setDefaultKeySet() {
        try {
            objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File("src/setting/keySet.json"), defaultKeyset);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashMap<String, Integer> getDisplaySize() {
        objectMapper = new ObjectMapper();
        HashMap<String, Integer> returnDisplayValue = new HashMap<>();
        try {
            returnDisplayValue = objectMapper.readValue(displaySet.toJSONString(), new TypeReference<Map<String, Integer>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnDisplayValue;
    }

    @Override
    public void setDisplaySize(int width, int height) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fw = new FileWriter("src/setting/displayInfo.json");
            displaySet.replace("width", width);
            displaySet.replace("height", height);
            gson.toJson(displaySet, fw); // 로컬에 저장
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.out.println("입출력 에러");
        }
    }
}
