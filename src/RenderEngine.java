import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RenderEngine extends JPanel implements Engine {
    private ArrayList<Displayable> renderList;
    private JFrame frame;

    public RenderEngine(JFrame jFrame) {
        renderList = new ArrayList<>();
        this.frame = jFrame;
    }

    public void addToRenderList(Displayable displayable) {
        if (!renderList.contains(displayable)) {
            renderList.add(displayable);
        }
    }
    //Method to add a displayable into renderlist by checking
    // if it's not already in it

    public void addToRenderList(ArrayList<Displayable> displayable) {
        if (!renderList.contains(displayable)) {
            renderList.addAll(displayable);
        }
    }
    //Method to add an arrayList of displayables into renderlist by checking
    // if it's not already in it

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Displayable renderObject : renderList) {
            renderObject.draw(g);
        }
    }
    public Boolean isInRenderList(Displayable displayable) {
        return renderList.contains(displayable);
    }

    @Override
    public void update() {
        this.repaint();
    }

    public void strPaint(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x,y,100, 50);
        label.setOpaque(true);
        this.frame.getContentPane().add(label);
        label.setLayout(null);

    }

    public void timePaint(int timeleft) {
        System.out.println("timeleft: " + timeleft);
        strPaint(timeleft + "s",50, 50);
    }
}
