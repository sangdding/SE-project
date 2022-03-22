package config;

import model.JsonScore;
import model.Score;

public class AppConfig {

    public Score score() {
        return new JsonScore();
    }
}
