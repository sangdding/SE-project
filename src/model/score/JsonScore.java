package model.score;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.score.Score;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class JsonScore implements Score {

    private JSONObject scoreInfo;
    private ObjectMapper objectMapper;


    public JsonScore() {
        JSONParser parser = new JSONParser();
        try {
            Reader readerScore = new FileReader("src/score.json");
            scoreInfo = (JSONObject) parser.parse(readerScore);
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
    public int save(String name, int score) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            if (scoreInfo.containsKey(name)) {
                return 1;
            } else {
                FileWriter fw = new FileWriter("src/score.json");
                scoreInfo.put(name, score); // json 파일에 점수 저장
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
    public HashMap<String, Integer> getList() {
        HashMap<String, Integer> returnScoreInfo = null;
        objectMapper = new ObjectMapper();
        try {
            returnScoreInfo = objectMapper.readValue(scoreInfo.toJSONString(), new TypeReference<Map<String, Integer>>() {
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
    public void resetList() {
        HashMap<String, Integer> resetScore = new HashMap<>();
        resetScore.put("admin", -1);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fw = new FileWriter("src/score.json");
            gson.toJson(resetScore, fw);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
