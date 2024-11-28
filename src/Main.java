import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {

    JFrame displayZoneFrame;

    RenderEngine renderEngine;
    GameEngine gameEngine;
    PhysicEngine physicEngine;

    DynamicSprite hero;

    Timer renderTimer;
    Timer gameTimer;
    Timer physicTimer;
    Timer timeCount;
    private GameState laststate;//to store the


    public Main() throws Exception {
        displayZoneFrame = new JFrame("Java Labs");
        displayZoneFrame.setSize(400, 600);
        displayZoneFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        hero = new DynamicSprite(200, 300,
                ImageIO.read(new File("./img/heroTileSheetLowRes.png")), 48, 50);

        gameEngine = new GameEngine(hero);
        renderEngine = new RenderEngine(displayZoneFrame);
        physicEngine = new PhysicEngine();

        renderTimer = new Timer(50, (time) -> renderEngine.update());
        gameTimer = new Timer(50, (time) -> {
            if (laststate != gameEngine.getGameState()) {
                try {
                    setState();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                gameEngine.update();
            }
        });

        physicTimer = new Timer(50, (time) -> physicEngine.update());
        timeCount = new Timer(1000, (time) -> {
            gameEngine.count();
            renderEngine.timePaint(gameEngine.getTimeleft());
        });

        gameEngine.setGameState(GameState.STARTUP);


        gameTimer.start();
        renderTimer.start();
        physicTimer.start();
        timeCount.start();


        displayZoneFrame.setVisible(true);
        displayZoneFrame.addKeyListener(gameEngine);
        displayZoneFrame.setFocusable(true);// Pour que la JFrame continue a capturer les événements clavier
        displayZoneFrame.requestFocus();

    }

    public static void main(String[] args) throws Exception {
        // write your code here
        Main main = new Main();
    }

    public void setState() throws Exception {
        // Mise à jour de l'etat
        switch (gameEngine.getGameState()) {//Using the new gamestate to update the GUI
            case STARTUP:
                renderTimer.stop();
                physicTimer.stop();
                timeCount.stop();
                ImageIcon Background = new ImageIcon("./img/MenuBackground.png");
                JLabel background = new JLabel(Background);
                JButton playButton = new JButton("Play!");
                playButton.setBounds(150, 300, 100, 50);

                playButton.addActionListener(e -> {
                    gameEngine.setGameState(GameState.INGAME);
                });
                background.setLayout(null);
                background.add(playButton);
                displayZoneFrame.getContentPane().add(background);
                break;

            case INGAME:
                timeCount.restart();
                renderTimer.restart();
                physicTimer.restart();
                displayZoneFrame.getContentPane().removeAll();
                if(laststate == GameState.STARTUP ) {
                    Playground level = new Playground("./data/level1.txt");
                    //SolidSprite testSprite = new DynamicSprite(100,100,test,0,0);
                    renderEngine.addToRenderList(level.getSpriteList());
                    renderEngine.addToRenderList(hero);
                    physicEngine.addToMovingSpriteList(hero);
                    physicEngine.setEnvironment(level.getSolidSpriteList());
                }
                    displayZoneFrame.getContentPane().add(renderEngine);


                break;
            case NOTIME:
                renderTimer.stop();
                physicTimer.stop();
                timeCount.stop();
                displayZoneFrame.getContentPane().removeAll();
                ImageIcon GameOver= new ImageIcon("./img/GameOver.png");
                background = new JLabel(GameOver);

                JButton replayButton = new JButton("retry ?");
                replayButton.setBounds(150, 300, 100, 50);
                replayButton.addActionListener(e -> {
                    gameEngine.setGameState(GameState.INGAME);
                    gameEngine.resetTimeleft();
                });

                background.setLayout(null);
                background.add(replayButton);
                displayZoneFrame.getContentPane().add(background);
                break;
            case PAUSE:
                timeCount.stop();
                renderTimer.stop();
                physicTimer.stop();
                renderEngine.strPaint("Paused",150,300);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + laststate);
        }
        //update of displayZoneFrame
        this.laststate = gameEngine.getGameState();//Update of the laststate for the main
        displayZoneFrame.revalidate();
        displayZoneFrame.repaint();

    }


}
