package model;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import static java.awt.event.KeyEvent.VK_L;
import static org.junit.jupiter.api.Assertions.*;

class JsonSettingTest {

    private JSONObject keySet;
    private JSONObject defaultKeyset;
    private JSONObject displaySet;

    @BeforeEach
    public void JsonKeySetting() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        try {
            Reader readerCustom = new FileReader("src/keySet.json");
            Reader readerDefault = new FileReader("src/defaultKeySet.json");
            Reader readerDisplay = new FileReader("src/displayInfo.json");
            keySet = (JSONObject) parser.parse(readerCustom);
            defaultKeyset = (JSONObject) parser.parse(readerDefault);
            displaySet = (JSONObject) parser.parse(readerDisplay);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void 키_목록_가져오기() {
        Assertions.assertEquals(keySet.keySet().size(), 9);
        Assertions.assertEquals(defaultKeyset.keySet().size(), 9);
    }

    @Test
    void 같은_키_중복입력() throws IOException, ParseException {
        JsonSetting jsonKeySetting = new JsonSetting();
        HashMap<String, Integer> changeKey = new HashMap<>();
        changeKey.put("up", KeyEvent.VK_9);
        changeKey.put("down", KeyEvent.VK_0);
        jsonKeySetting.setKeyList(changeKey);
        System.out.println(jsonKeySetting.getKeyList().get("up"));
        assertEquals(KeyEvent.VK_9, jsonKeySetting.getKeyList().get("up"));
        assertEquals(KeyEvent.VK_0, jsonKeySetting.getKeyList().get("down"));
    }

    @Test
    void getDefaultKeySet() throws IOException, ParseException {
        JsonSetting jsonKeySetting = new JsonSetting();
        jsonKeySetting.setDefaultKeySet();
        assertTrue(jsonKeySetting.getKeyList().equals(jsonKeySetting.getDefaultKeySet()));
    }

    @Test
    void 입력_형식_테스트() {
        HashMap<String, HashMap<String, Integer>> returnDisplayValue = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            returnDisplayValue = objectMapper.readValue(displaySet.toJSONString(), new TypeReference<Map<String, Map<String, Integer>>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(returnDisplayValue.get("small").get("width"));
    }
}