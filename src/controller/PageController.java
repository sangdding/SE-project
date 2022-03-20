package controller;

import view.GamePage;
import view.MainPage;
import view.ScorePage;
import view.SettingPage;

public class PageController {

    private MainPage mainPage;
    private SettingPage settingPage;
    private GamePage gamePage;
    private ScorePage scorePage;

    public PageController(String pageName) {
        if (pageName.equals("Setting")) {
            settingPage = new SettingPage();
        } else if (pageName.equals("Main")) {
            mainPage = new MainPage();
        } else if (pageName.equals("Game Start")) {
            gamePage = new GamePage();
        }
        else if (pageName.equals("ScoreBoard")) {
             scorePage=new ScorePage();
        }
    }

    private void setButtonClickController(){
        
    }
}
