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
    private ObjectMapper objectMapper;


    public JsonScore() {
        JSONParser parser = new JSONParser();
        try {
            Reader readerScore = new FileReader("src/score.json");
            scoreInfo = (JSONObject) parser.parse(readerScore);
            itemScoreInfo = (JSONObject) scoreInfo.get("item");
            normalScoreInfo = (JSONObject) scoreInfo.get("normal");
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
        JSONObject tempObj;
        int[] info = {score, difficulty};
        if (mode == 0) {
            tempObj = normalScoreInfo;
        } else {
            tempObj = itemScoreInfo;
        }
        try {
            if (tempObj.containsKey(name)) {
                return 1;
            } else {
                FileWriter fw = new FileWriter("src/score.json");
                tempObj.put(name, info); // json 파일에 점수 저장
                if (mode == 0) {
                    scoreInfo.replace("normal", tempObj);
                } else {
                    scoreInfo.replace("item", tempObj);
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
    public HashMap<String, int[]> getList(int mode) {
        HashMap<String, int[]> returnScoreInfo = null;
        JSONObject tempObj;
        objectMapper = new ObjectMapper();
        if (mode == 0) {
            tempObj = objectMapper.convertValue(scoreInfo.get("normal"), JSONObject.class);
        } else {
            tempObj = objectMapper.convertValue(scoreInfo.get("item"), JSONObject.class);
        }
        try {
            returnScoreInfo = objectMapper.readValue(tempObj.toJSONString(), new TypeReference<Map<String, Object>>() {
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
            } else {
                scoreInfo.replace("item", resetScore);
            }
            gson.toJson(scoreInfo, fw);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
