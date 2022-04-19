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
        } else if (pageName.equals("Game Start - Normal Mode") || pageName.equals("Game Restart")||pageName.equals("Game Start - Item Mode")) {
            gamePage = new GamePage();
        }
        else if (pageName.equals("ScoreBoard - Normal")) {
             scorePage=new ScorePage(0);
        }
        else if (pageName.equals("ScoreBoard - Item")) {
            scorePage=new ScorePage(1);
        }
        else if (pageName.equals("Key Setting")){
            keySettingPage=new KeySettingPage();
        }
    }


}
