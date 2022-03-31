package model.setting;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class JsonSetting implements Setting {

    private JSONObject settingData;
    private JSONObject defaultKeyset;
    private JSONObject customKeyset;
    private JSONObject displaySet;
    private ObjectMapper objectMapper;

    public JsonSetting() {
        JSONParser parser = new JSONParser();
        try {
            Reader readerSetting = new FileReader("src/setting.json");
            settingData = (JSONObject) parser.parse(readerSetting);
        } catch (FileNotFoundException e) {
            System.out.println("파일이 존재하지 않습니다.");
        } catch (IOException e) {
            System.out.println("입출력 에러");
        } catch (ParseException e) {
            System.out.println("파싱 에러");
        }

        defaultKeyset = (JSONObject) settingData.get("defaultKey");
        customKeyset = (JSONObject) settingData.get("customKey");
        displaySet = (JSONObject) settingData.get("display");

    }

    @Override
    public HashMap<String, Integer> getKeyList() {
        objectMapper = new ObjectMapper();
        HashMap<String, Integer> returnKeySet = null;
        try {
            returnKeySet = objectMapper.readValue(customKeyset.toJSONString(), new TypeReference<Map<String, Integer>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnKeySet;
    }

    @Override
    public void setKeyList(HashMap<String, Integer> changeKey) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fw = new FileWriter("src/setting.json");
            for(String key : changeKey.keySet()) {
                customKeyset.replace(key, changeKey.get(key)); // json 파일에 키 정보 저장 후
            }
            gson.toJson(customKeyset, fw); // 로컬에 저장
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.out.println("입출력 에러");
        }
    }

    @Override
    public HashMap<String, Integer> getDefaultKeySet() {
        HashMap<String, Integer> returnDefaultKeySet = null;
        objectMapper = new ObjectMapper();
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
            objectMapper.writeValue(new File("src/setting.json"), defaultKeyset);
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
            FileWriter fw = new FileWriter("src/setting.json");
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
