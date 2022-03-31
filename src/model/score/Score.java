package model.score;

import java.util.HashMap;

public interface Score {

    /**
     * @return
     * 1 : 중복된 이름으로 저장 실패
     * 2 : 저장 성공
     * -1 : 입출력 에러
     */
    int save(String name, int score);

    HashMap<String, Integer> getList(); // 점수 정보 불러오기

    /**
     * 파일을 완전히 삭제하면 나중에 새 파일을 만들고 읽어올 때 에러 발생
     * reset을 호출하면 "admin" : -1을 저장하고 다른 데이터는 다 삭제
     * 나중에 getList로 호춣하면 key값 admin을 제외하고 출력하시면 됩니다.
     */
    void resetList();
}
