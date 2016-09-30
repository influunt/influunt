import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by rodrigosol on 9/30/16.
 */
public class InterView {

    private JPanel InterViewPanel;

    private JButton abrirButton;

    private JButton atualizarButton;

    private JPanel drawPanel;

    private static class RectDraw extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawRect(230,80,10,10);
            g.setColor(Color.RED);
            g.fillRect(230,80,10,10);
        }
    }

    public void init(){
        RectDraw r = new RectDraw();
        drawPanel.paint(r.getGraphics());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("InterView");
        InterView interView = new InterView();
        frame.setContentPane(interView.InterViewPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(new Dimension(1024,400));
        frame.setVisible(true);
        interView.init();

    }

}
