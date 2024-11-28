import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine implements Engine, KeyListener {
    DynamicSprite hero;
    private static GameState gameState;
    private int timeleft = 10;

    public GameEngine(DynamicSprite hero) {
        this.hero = hero;
    }

    @Override
    public void update() {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (gameState) {
            case INGAME:
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        hero.setDirection(Direction.NORTH);
                        break;
                    case KeyEvent.VK_DOWN:
                        hero.setDirection(Direction.SOUTH);
                        break;
                    case KeyEvent.VK_LEFT:
                        hero.setDirection(Direction.WEST);
                        break;
                    case KeyEvent.VK_RIGHT:
                        hero.setDirection(Direction.EAST);
                        break;
                    case KeyEvent.VK_SPACE:
                        System.out.println("Pause: " );
                        setGameState(GameState.PAUSE);
                        break;
                    case KeyEvent.VK_CONTROL:
                        if(hero.getSpeed()==5) {
                            hero.run(10);
                            System.out.println("JAVA trop bien !!");
                        }
                        else if(hero.getSpeed()==10) {
                            hero.run(5);
                            System.out.println("JAVA plus...");
                        }
                }
                break;
            case STARTUP:
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    System.out.println("Let's Play: " );
                    setGameState(GameState.INGAME);
                }
                break;
            case PAUSE:
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    System.out.println("Back to the Game: " );
                    setGameState(GameState.INGAME);
                }
                break;
            default:

        }


    }


    @Override
    public void keyReleased(KeyEvent e) {

    }
    public GameState getGameState() {
        return gameState;
    }
    public void setGameState(GameState newState) {
        gameState = newState;
    }

    public void count() {
        if(timeleft>0){
            timeleft--;//
        }else{
            gameState = GameState.NOTIME;
        }
    }

    public int getTimeleft() {
        return timeleft;
    }

    public void resetTimeleft() {
        timeleft = 40;
    }
}
