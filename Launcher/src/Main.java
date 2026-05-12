import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Arrays;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;
import com.googlecode.lanterna.terminal.swing.*;
import com.googlecode.lanterna.input.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) throws IOException {
        SwingTerminalFrame terminal = getSwingTerminalFrame();

        //creates terminal screen inside window
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        TextGraphics tg = screen.newTextGraphics();
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        System.out.println("fonts: " + Arrays.toString(fonts));
        tg.putString(50, 19, "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz");
        tg.putString(50, 20, "$$\\       $$\\                       $$\\            sdsd         \n" +
                "$$ |      \\__|                                        \\__|                    \n" +
                "$$ |      $$\\  $$$$$$\\  $$\\   $$\\  $$$$$$\\   $$$$$$\\  $$\\  $$$$$$$\\  $$$$$$\\  \n" +
                "$$ |      $$ |$$  __$$\\ $$ |  $$ |$$  __$$\\ $$  __$$\\ $$ |$$  _____|$$  __$$\\ \n" +
                "$$ |      $$ |$$ /  $$ |$$ |  $$ |$$ /  $$ |$$ |  \\__|$$ |$$ /      $$$$$$$$ |\n" +
                "$$ |      $$ |$$ |  $$ |$$ |  $$ |$$ |  $$ |$$ |      $$ |$$ |      $$   ____|\n" +
                "$$$$$$$$\\ $$ |\\$$$$$$$ |\\$$$$$$  |\\$$$$$$  |$$ |      $$ |\\$$$$$$$\\ \\$$$$$$$\\ \n" +
                "\\________|\\__| \\____$$ | \\______/  \\______/ \\__|      \\__| \\_______| \\_______|\n" +
                "                    $$ |                                                      \n" +
                "                    $$ |                                                      \n" +
                "                    \\__|                                                      ");
        tg.putString(30, 5, "Press any key...");
        screen.refresh();

        while (true) {
            KeyStroke key = screen.readInput();
            if (key.getKeyType() == KeyType.Escape) break;

            tg.putString(0, 1, "                    ");
            tg.putString(0, 1, "You pressed: " + key);
            screen.refresh();
        }

        screen.stopScreen();
        terminal.dispose();
    }

    private static SwingTerminalFrame getSwingTerminalFrame() {
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int fontSize = screenHeight / 63;
        SwingTerminalFontConfiguration fontConfig = SwingTerminalFontConfiguration.newInstance(new Font("Monospaced", Font.PLAIN, fontSize));
        SwingTerminalFrame terminal = new SwingTerminalFrame("Liquorice", new TerminalSize(512, 48), null,fontConfig, null, TerminalEmulatorAutoCloseTrigger.CloseOnExitPrivateMode);

        //Borderless fullscreen
        terminal.setUndecorated(true);
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