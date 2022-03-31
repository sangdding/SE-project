package config;

import model.score.JsonScore;
import model.score.Score;
import model.setting.JsonSetting;
import model.setting.Setting;

public class AppConfig {

    public Score score() {
        return new JsonScore();
    }

    public Setting setting() {
        return new JsonSetting();
    }
}
