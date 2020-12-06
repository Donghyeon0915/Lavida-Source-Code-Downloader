/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selenium;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Donghyeon <20183188>
 */
public class WindowControler {

    private Robot robot;

    public WindowControler() {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {

        }
    }

    public void releaseLeftMouse() {
        robot.delay(500);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

    }

    public void ctrl_A() {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_A);

        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_A);
        robot.delay(100);
    }

    public void ctrl_C() {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_C);

        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_C);
        robot.delay(100);
    }

    public String getClipBoard() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(clipboard);
        String pasteString = "";

        if (contents != null) {

            try {
                pasteString = (String) (contents.getTransferData(
                        DataFlavor.stringFlavor));

                return pasteString;
            } catch (UnsupportedFlavorException | IOException e) {
                System.out.println("클립보드 에러");
            }

        }
        return pasteString;
    }
    
        public void Thread_Sleep(int sec) {
        try {
            Thread.sleep(sec);
        } catch (InterruptedException ex) {
            Logger.getLogger(Selenium.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
