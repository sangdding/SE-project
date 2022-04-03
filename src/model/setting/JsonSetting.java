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
    private JSONObject difficulty;
    private JSONObject gameMode;
    private JSONObject displayMode;
    private ObjectMapper objectMapper;

    public JsonSetting() {
        JSONParser parser = new JSONParser();
        objectMapper = new ObjectMapper();
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
            settingData.replace("customKey", customKeyset);
            gson.toJson(settingData, fw); // 로컬에 저장
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.out.println("입출력 에러");
        }
    }

    @Override
    public HashMap<String, Integer> getDefaultKeySet() {
        HashMap<String, Integer> returnDefaultKeySet = new HashMap<>();
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
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fw = new FileWriter("src/setting.json");
            settingData.replace("customKey", defaultKeyset);
            gson.toJson(settingData, fw); // 로컬에 저장
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.out.println("입출력 에러");
        }
    }

    @Override
    public HashMap<String, Integer> getDisplaySize() {
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
            settingData.replace("display", displaySet);
            gson.toJson(settingData, fw); // 로컬에 저장
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.out.println("입출력 에러");
        }
    }

    @Override
    public int getDifficulty() {
        if(settingData.get("difficulty").equals("normal")){
            return 0;
        } else if(settingData.get("difficulty").equals("easy")) {
            return 1;
        } else if(settingData.get("difficulty").equals("hard")) {
            return 2;
        } else {
            return -1;
        }
    }

    @Override
    public void setDifficulty(int difficultyValue)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fw = new FileWriter("src/setting.json");
            if(difficultyValue == 0){
                settingData.replace("difficulty", "normal");
            } else if(difficultyValue == 1) {
                settingData.replace("difficulty", "easy");
            } else if(difficultyValue == 2) {
                settingData.replace("difficulty", "hard");
            }
            gson.toJson(settingData, fw); // 로컬에 저장
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.out.println("입출력 에러");
        }
    }

    @Override
    public int getGameMode() {
        if(settingData.get("gameMode").equals("normal")) {
            return 0;
        } else if (settingData.get("gameMode").equals("item")) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public void setGameMode(int gameModeValue) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fw = new FileWriter("src/setting.json");
            if(gameModeValue == 0){
                settingData.replace("gameMode", "normal");
            } else if(gameModeValue == 1) {
                settingData.replace("gameMode", "item");
            }
            gson.toJson(settingData, fw); // 로컬에 저장
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.out.println("입출력 에러");
        }
    }

    @Override
    public int getDisplayMode() {
        if (settingData.get("displayMode").equals("normal")) {
            return 0;
        } else if (settingData.get("displayMode").equals("blind")) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public void setDisplayMode(int displayModeValue) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fw = new FileWriter("src/setting.json");
            if(displayModeValue == 0){
                settingData.replace("displayMode", "normal");
            } else if(displayModeValue == 1) {
                settingData.replace("displayMode", "blind");
            }
            gson.toJson(settingData, fw); // 로컬에 저장
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.out.println("입출력 에러");
        }

    }
}