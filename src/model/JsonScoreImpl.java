package model;

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
import java.util.Map;

public class JsonScoreImpl implements Score {

    private JSONObject scoreInfo;
    private ObjectMapper objectMapper;


    public JsonScoreImpl() {
        JSONParser parser = new JSONParser();
        try {
            Reader readerScore = new FileReader("src/scoreInfo.json");
            scoreInfo = (JSONObject) parser.parse(readerScore);
        } catch (FileNotFoundException e) {
            File file = new File("src/scoreInfo.json");
        } catch (IOException e) {
            System.out.println("입출력 에러");
        } catch (ParseException e) {
            System.out.println("파싱 에러");
        }
    }

    @Override
    public void save(String name, int score) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fw = new FileWriter("src/scoreInfo.json");
            scoreInfo.put("name", score); // json 파일에 점수 저장
            gson.toJson(scoreInfo, fw); // 로컬에 저장
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.out.println("입출력 에러");
        }
    }

    @Override
    public HashMap<String, Integer> getList() {
        HashMap<String, Integer> returnScoreInfo = null;
        try {
            returnScoreInfo = objectMapper.readValue(scoreInfo.toJSONString(), new TypeReference<Map<String, Integer>>(){});
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
        File deleteFile = new File("src/scoreInfo.json");
        if (deleteFile.exists()) {
            deleteFile.delete();
        } else {
            System.out.println("점수 정보가 없습니다.");
        }
    }
}
