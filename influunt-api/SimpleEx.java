import javax.swing.*;
import java.awt.*;

public class SimpleEx extends JFrame {

    public SimpleEx() {

        initUI();
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            SimpleEx ex = new SimpleEx();
            ex.setVisible(true);
        });
    }

    private void initUI() {

        setTitle("Simple example");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}