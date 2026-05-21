import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.swing.*;
import com.googlecode.lanterna.input.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Main {
    public static int uiSize = 8;
    public static String scene;
    //global variables so that they can be accessed without being passed as arguments to functions
    public static SwingTerminalFrame terminal;
    public static Screen screen;
    public static TextGraphics tg;
    public static TextFormatter formatter;


    public static Screen createScreen(TerminalScreen terminal) throws IOException {
        terminal.startScreen();
        terminal.setCursorPosition(null);
        return terminal;
    }

    public static void exit() throws IOException {
        screen.stopScreen();
        terminal.dispose();
        System.exit(0);
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        //creates terminal screen inside window with formatter object for easier multi line editing
        terminal = getSwingTerminalFrame();

        //initializes global variables
        screen = createScreen(new TerminalScreen(terminal));
        tg = screen.newTextGraphics();
        formatter = new TextFormatter(tg, screen.getTerminalSize().getColumns());

        //title screen with UI resize
        SceneController.loadScene("Initialization");
    }


    private static SwingTerminalFrame getSwingTerminalFrame() throws IOException { //Terminal creation function

        //makes font size universal on all device screens, calculates cols and rows to be used in terminal
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int fontSize = screenSize.width / (16*uiSize);
        int charWidth = (int)(fontSize * 0.6);
        int cols = screenSize.width / charWidth;
        int rows = screenSize.height / (fontSize);

        SwingTerminalFontConfiguration fontConfig = SwingTerminalFontConfiguration.newInstance(new Font("Monospaced", Font.PLAIN, fontSize));
        SwingTerminalFrame terminal = new SwingTerminalFrame("Liquorice", new TerminalSize(cols, rows), null,fontConfig, null, TerminalEmulatorAutoCloseTrigger.CloseOnExitPrivateMode);

        //Borderless fullscreen
        terminal.setUndecorated(true);
        Image icon = ImageIO.read(new File("liquorice.png"));
        terminal.setIconImage(icon);
        terminal.setVisible(true);
        terminal.setExtendedState(JFrame.MAXIMIZED_BOTH);
        terminal.setCursorVisible(false);

        //stops IDE process when alt+f4 is pressed
        terminal.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        return terminal;
    }
}