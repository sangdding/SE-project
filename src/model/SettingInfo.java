package model;

import java.util.HashMap;

public interface SettingInfo {

    HashMap<String, Integer> getKeyList(); // 키 정보 가져오기

    void setKeyList(HashMap<String, Integer> changeKey); // 키 정보 수정하기

    HashMap<String, Integer> getDefaultKeySet(); // 기본 키 설정 가져오기

    void setDefaultKeySet(); // 기본 키로 설정
}
