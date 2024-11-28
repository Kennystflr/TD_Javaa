import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Playground {
    private ArrayList<Sprite> environment = new ArrayList<>();
    // definition of a list of sprites to store the environment

    public Playground(String pathName) {
        try {// we try to prevent an error by using try
            final Image imageTree = ImageIO.read(new File("./img/tree.png"));
            final Image imageGrass = ImageIO.read(new File("./img/grass.png"));
            final Image imageRock = ImageIO.read(new File("./img/rock.png"));
            final Image imageTrap = ImageIO.read(new File("./img/trap.png"));
            // charging the images
            final int imageTreeWidth = imageTree.getWidth(null);
            final int imageTreeHeight = imageTree.getHeight(null);

            final int imageGrassWidth = imageGrass.getWidth(null);
            final int imageGrassHeight = imageGrass.getHeight(null);

            final int imageRockWidth = imageRock.getWidth(null);
            final int imageRockHeight = imageRock.getHeight(null);
            // getting the different element's size
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName)); //creating a Buffered loader
            String line = bufferedReader.readLine();
            int lineNumber = 0;
            int columnNumber = 0;
            while (line != null) {
                for (byte element : line.getBytes(StandardCharsets.UTF_8)) {// going through a file and interpreting the characters
                    switch (element) {
                        case 'T':
                            environment.add(new SolidSprite(columnNumber * imageTreeWidth,
                                    lineNumber * imageTreeHeight, imageTree, imageTreeWidth, imageTreeHeight));//if the caracter is 'T' put a Tree in the environment
                            break;
                        case ' ':
                            environment.add(new Sprite(columnNumber * imageGrassWidth,
                                    lineNumber * imageGrassHeight, imageGrass, imageGrassWidth, imageGrassHeight));//if the caracter is ' ' put Grass
                            break;
                        case 'R':
                            environment.add(new SolidSprite(columnNumber * imageRockWidth,
                                    lineNumber * imageRockHeight, imageRock, imageRockWidth, imageRockHeight));//if the caracter is 'R' put a Rock
                            break;
                    }
                    columnNumber++;
                }
                columnNumber = 0;
                lineNumber++;
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Sprite> getSolidSpriteList() {
        ArrayList<Sprite> solidSpriteArrayList = new ArrayList<>();
        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite) solidSpriteArrayList.add(sprite);
        }
        return solidSpriteArrayList;
    }

    public ArrayList<Displayable> getSpriteList() {
        ArrayList<Displayable> displayableArrayList = new ArrayList<>();
        for (Sprite sprite : environment) {
            displayableArrayList.add((Displayable) sprite);
        }
        return displayableArrayList;
    }
}
