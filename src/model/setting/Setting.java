package model.setting;

import java.util.HashMap;

public interface Setting {

    HashMap<String, Integer> getKeyList(); // 키 정보 가져오기

    void setKeyList(HashMap<String, Integer> changeKey); // 키 정보 수정하기

    HashMap<String, Integer> getDefaultKeySet(); // 기본 키 설정 가져오기

    void setDefaultKeySet(); // 기본 키로 설정

    /**
     * return : key = width, height
     */
    HashMap<String, Integer> getDisplaySize();

    void setDisplaySize(int width, int height);
}
