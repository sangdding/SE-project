package config;

import model.Score;
import model.ScoreImpl;

public class AppConfig {

    public Score score() {
        return new ScoreImpl();
    }
}
