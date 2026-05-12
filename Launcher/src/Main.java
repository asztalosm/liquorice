import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;
import com.googlecode.lanterna.terminal.swing.*;
import com.googlecode.lanterna.input.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Main {
    public static int uiSize = 7;

    public static Screen createScreen(TerminalScreen terminal) throws IOException {
        terminal.startScreen();
        terminal.setCursorPosition(null);
        return terminal;
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        SwingTerminalFrame terminal = getSwingTerminalFrame(); //create terminal
        System.out.println(uiSize);
        //creates terminal screen inside window with formatter object for easier multi line editing
        Screen screen = createScreen(new TerminalScreen(terminal));
        TextGraphics tg = screen.newTextGraphics();
        TerminalSize size = screen.getTerminalSize();
        TextFormatter formatter = new TextFormatter(tg, size.getColumns());


        formatter.printMulti(2, """
                  ________________________________________________________________________ \s
                //                                                                        \\\\
                @@@      ,e,                                         ,e,                   \s
                @@@       "    e@@~-@@@  @@@  @@@   e@@~-_   @@@-~\\   "    e@@~~\\   e@@~~@e\s
                @@@      @@@  d@@@  @@@  @@@  @@@  d@@@   i  @@@     @@@  d@@@     d@@@  @@b
                @@@      @@@  @@@@  @@@  @@@  @@@  @@@@   |  @@@     @@@  @@@@     @@@@__@@@
                @@@      @@@  Y@@@  @@@  @@@  @@@  Y@@@   '  @@@     @@@  Y@@@     Y@@@    ,
                @@@____  @@@   "@@_-@@@  "@@_-@@@   "@@_-~   @@@     @@@   "@@__/   "@@___/\s
                                    @@@                                                    \s
                \\\\__________________@@@___________________________________________________//
                                    @@@                                                    \s
                                    @@                                                     \s
                                                                                  \s""", TextFormatter.PaddingAlignment.CENTER);
        formatter.printSingle(25, "Press the up and down arrows to enlarge/shrink the UI", TextFormatter.PaddingAlignment.CENTER);
        formatter.printSingle(26, "Press Enter to continue", TextFormatter.PaddingAlignment.CENTER);
        screen.refresh();

        while (true) {
            KeyStroke key = screen.readInput();
            if (key.getKeyType() == KeyType.Escape) break;
            if (key.getKeyType() == KeyType.ArrowDown) {
                screen.stopScreen();
                terminal.dispose();
                uiSize++;
                main(null);
            };
            if (key.getKeyType() == KeyType.ArrowUp) {
                screen.stopScreen();
                terminal.dispose();
                uiSize--;
                main(null);
            }
            screen.refresh();
        }

        screen.stopScreen();
        terminal.dispose();
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