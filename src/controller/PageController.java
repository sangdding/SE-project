package controller;

import view.*;

public class PageController {

    private MainPage mainPage;
    private SettingPage settingPage;
    private GamePage gamePage;
    private ScorePage scorePage;
    private GameEndPage gameEndPage;
    private KeySettingPage keySettingPage;

    public PageController(String pageName) {
        if (pageName.equals("Setting")) {
            settingPage = new SettingPage();
        } else if (pageName.equals("Main")) {
            mainPage = new MainPage();
        } else if (pageName.equals("Game Start") || pageName.equals("Game Restart")) {
            gamePage = new GamePage();
        }
        else if (pageName.equals("ScoreBoard")) {
             scorePage=new ScorePage();
        }
        else if (pageName.equals("Key Setting")){
            keySettingPage=new KeySettingPage();
        }
    }


}
