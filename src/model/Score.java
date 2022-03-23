package model;

import java.util.HashMap;
import java.util.Map;

public interface Score {

    /**
     * HashMap의 형태로 저장되기 때문에 name이 중복되면 Duplicate key error 발생.
     * save로 저장하기 전에, 먼저 getList로 score정보 가져온 다음 키가 중복되지 않는지 확인하고
     * 중복되지 않으면 save 호출해서 저장하면 됨.
     */
    void save(String name, int score);

    HashMap<String, Integer> getList(); // 점수 정보 불러오기

    /**
     * 파일을 완전히 삭제하면 나중에 새 파일을 만들고 읽어올 때 에러 발생
     * reset을 호출하면 "admin" : -1을 저장하고 다른 데이터는 다 삭제
     * 나중에 getList로 호춣하면 key값 admin을 제외하고 출력하시면 됩니다.
     */
    void resetList();
}
