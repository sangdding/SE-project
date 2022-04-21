package model.score;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonScore implements Score {

    private JSONObject scoreInfo;
    private JSONObject itemScoreInfo;
    private JSONObject normalScoreInfo;
    private JSONObject itemDifficultyInfo;
    private JSONObject normalDifficultyInfo;
    private ObjectMapper objectMapper;


    public JsonScore() {
        JSONParser parser = new JSONParser();
        try {
            Reader readerScore = new FileReader("src/score.json");
            scoreInfo = (JSONObject) parser.parse(readerScore);
            itemScoreInfo = (JSONObject) scoreInfo.get("item");
            normalScoreInfo = (JSONObject) scoreInfo.get("normal");
            itemDifficultyInfo = (JSONObject) scoreInfo.get("itemDifficulty");
            normalDifficultyInfo = (JSONObject) scoreInfo.get("normalDifficulty");
            readerScore.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("입출력 에러");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int save(String name, int score, int mode, int difficulty) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JSONObject tempObj1;
        JSONObject tempObj2;
        int[] info = {score, difficulty};
        if (mode == 0) {
            tempObj1 = normalScoreInfo;
            tempObj2 = normalDifficultyInfo;
        } else {
            tempObj1 = itemScoreInfo;
            tempObj2 = itemDifficultyInfo
        }
        try {
            if (tempObj1.containsKey(name)) {
                return 1;
            } else {
                FileWriter fw = new FileWriter("src/score.json");
                tempObj1.put(name, score); // json 파일에 점수 저장
                tempObj2.put(name, difficulty);
                if (mode == 0) {
                    scoreInfo.replace("normal", tempObj1);
                    scoreInfo.replace("normalDifficulty", tempObj2);
                } else {
                    scoreInfo.replace("item", tempObj1);
                    scoreInfo.replace("itemDifficulty", tempObj2);
                }
                gson.toJson(scoreInfo, fw); // 로컬에 저장
                fw.flush();
                fw.close();
                return 0;
            }
        } catch (IOException e) {
            return -1;
        }
    }

    @Override
    public HashMap<String, Integer> getList(int mode) {
        HashMap<String, Integer> returnScoreInfo = null;
        JSONObject tempObj1;
        JSONObject tempObj2;
        objectMapper = new ObjectMapper();
        if (mode == 0) {
            tempObj1 = objectMapper.convertValue(scoreInfo.get("normal"), JSONObject.class);
            tempObj2 = objectMapper.convertValue(scoreInfo.get("normalDifficulty"), JSONObject.class);
        } else {
            tempObj1 = objectMapper.convertValue(scoreInfo.get("item"), JSONObject.class);
            tempObj2 = objectMapper.convertValue(scoreInfo.get("itemDifficulty"), JSONObject.class);
        }
        try {
            returnScoreInfo = objectMapper.readValue(tempObj1.toJSONString(), new TypeReference<Map<String, Integer>>() {
            });
            returnScoreInfo = objectMapper.readValue(tempObj2.toJSONString(), new TypeReference<Map<String, Integer>>() {
            });
        } catch (JsonMappingException e) {
            System.out.println("JsonMapping 에러");
        } catch (JsonParseException e) {
            System.out.println("Json 파싱 에러");
        } catch (IOException e) {
            System.out.println("입출력 에러");
        }
        return returnScoreInfo;
    }

    @Override
    public void resetList(int mode) {
        HashMap<String, Integer> resetScore = new HashMap<>();
        resetScore.put("admin", -1);
        JSONObject tempObj = new JSONObject(resetScore);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fw = new FileWriter("src/score.json");
            if (mode == 0) {
                scoreInfo.replace("normal", resetScore);
                scoreInfo.replace("normalDifficulty", resetScore);
            } else {
                scoreInfo.replace("item", resetScore);
                scoreInfo.replace("itemDifficulty", resetScore);
            }
            gson.toJson(scoreInfo, fw);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
