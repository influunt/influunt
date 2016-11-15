package os72c.client.device;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import engine.IntervaloGrupoSemaforico;
import javafx.scene.layout.Pane;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.apache.commons.lang.ArrayUtils;
import os72c.client.exceptions.HardwareFailureException;
import protocol.*;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by rodrigosol on 11/4/16.
 */
public class ConsoleDevice implements DeviceBridge {



    public ConsoleDevice() throws IOException {
// Setup terminal and screen layers
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        Table<String> table = new Table<String>("Grupo", "Cor");
        table.getTableModel().addRow("1", "VERDE");
        table.getTableModel().addRow("2", "VERMELHO");
        table.getTableModel().addRow("3", "AZUL");
        table.getTableModel().addRow("4", "VERDE");
        table.getTableModel().addRow("5", "VERMELHO");
        table.getTableModel().addRow("6", "AZUL");
        table.getTableModel().addRow("7", "VERDE");
        table.getTableModel().addRow("8", "VERMELHO");
        table.getTableModel().addRow("9", "AZUL");
        table.getTableModel().addRow("10", "VERDE");
        table.getTableModel().addRow("11", "VERMELHO");
        table.getTableModel().addRow("12", "AZUL");
        table.getTableModel().addRow("13", "AZUL");
        table.getTableModel().addRow("14", "AZUL");
        table.getTableModel().addRow("15", "AZUL");
        table.getTableModel().addRow("16", "AZUL");




        // Create panel to hold components
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        Panel cabecalho = new Panel();


        cabecalho.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        Label labelControlador = new Label("Controlador:");
        Label labelId = new Label("1231-123123-1231-123123-1231");
        cabecalho.addComponent(labelControlador);
        cabecalho.addComponent(labelId);
        panel.addComponent(cabecalho.withBorder(Borders.singleLine("Dados do Controlador")));

        Panel central = new Panel();
        central.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        central.addComponent(table.withBorder(Borders.singleLine("Estados")));

        Panel detectores = new Panel(new LinearLayout(Direction.VERTICAL));
        detectores.addComponent(new Button("DV 1"));
        detectores.addComponent(new Button("DV 2"));
        detectores.addComponent(new Button("DV 3"));
        detectores.addComponent(new Button("DV 4"));
        detectores.addComponent(new Button("DV 5"));
        detectores.addComponent(new Button("DV 6"));
        detectores.addComponent(new Button("DV 7"));
        detectores.addComponent(new Button("DV 8"));
        detectores.addComponent(new Button("DP 1"));
        detectores.addComponent(new Button("DP 2"));
        detectores.addComponent(new Button("DP 3"));
        detectores.addComponent(new Button("DP 4"));
        central.addComponent(detectores.withBorder(Borders.singleLine("Detectores")));
        Panel right = new Panel();
        right.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        Panel falhas = new Panel(new LinearLayout(Direction.VERTICAL));

        TerminalSize size = new TerminalSize(14,10);
        RadioBoxList<String> radioBoxList = new RadioBoxList<String>(size);
        radioBoxList.addItem("Falha");
        radioBoxList.addItem("Alarme");
        falhas.addComponent(radioBoxList);
        falhas.addComponent(new Label("Codigo"));
        falhas.addComponent(new TextBox());
        falhas.addComponent(new Button("Gerar"));
        right.addComponent(falhas.withBorder(Borders.singleLine("Falhas")));
        central.addComponent(right);
        Panel log = new Panel();
        TextBox txtLog = new TextBox(new TerminalSize(20,10));
        txtLog.setText("jfshdf asdhf kjashdfk ja dlf\n alsjdaflsdflj asdfl\nadlfahsdf adslkdjaslkd lakjsd");



        //txtLog.setSize();
        log.addComponent(txtLog);
        central.addComponent(log.withBorder(Borders.singleLine("Log")));
        panel.addComponent(central);




//        panel.setLayoutManager(new GridLayout(2));
//
//        panel.addComponent(new Label("Forename"));
//        panel.addComponent(new TextBox());
//
//        panel.addComponent(new Label("Surname"));
//        panel.addComponent(new TextBox());
//
//        panel.addComponent(new EmptySpace(new TerminalSize(0,0))); // Empty space underneath labels
//        panel.addComponent(new Button("Submit"));

        // Create window to hold the panel
        BasicWindow window = new BasicWindow();
        window.setComponent(panel);

        // Create gui and start gui
        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
        gui.addWindowAndWait(window);
    }


    @Override
    public void sendEstagio(IntervaloGrupoSemaforico intervaloGrupoSemaforico) {

    }

    public static void main(String[] args) throws IOException {
        new ConsoleDevice();
    }
}
