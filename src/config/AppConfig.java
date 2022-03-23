package config;

import model.JsonScoreImpl;
import model.Score;

public class AppConfig {

    public Score score() {
        return new JsonScoreImpl();
    }
}
