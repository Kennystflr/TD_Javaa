import java.awt.*;

public class Sprite implements Displayable {
    protected final Image image;
    protected final double width;
    protected final double height;
    protected double x;
    protected double y;

    public Sprite(double x, double y, Image image, double width, double height) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.width = width;
        this.height = height;
    }
    // Creation of The sprite class implementing displayable and initialisation of a constructor

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, null);
    }
}
