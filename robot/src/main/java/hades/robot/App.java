package hades.robot;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class App {
    private GraphicsDevice screenDevice;
    private Robot robot;

    public App() throws AWTException {
        screenDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        robot = new Robot(screenDevice);
    }

    public Process runProcess(String cmd) throws IOException {
        return Runtime.getRuntime().exec("notepad");
    }

    public void runKey() {
        robot.keyPress(KeyEvent.VK_T);
        robot.keyRelease(KeyEvent.VK_T);
        robot.keyPress(KeyEvent.VK_E);
        robot.keyRelease(KeyEvent.VK_E);
        robot.keyPress(KeyEvent.VK_S);
        robot.keyRelease(KeyEvent.VK_S);
        robot.keyPress(KeyEvent.VK_T);
        robot.keyRelease(KeyEvent.VK_T);
    }

    public void runPaste(String text) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }

    public void runMouse() {
        robot.mouseMove(0, 0);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.mousePress(InputEvent.BUTTON2_MASK);
        robot.mouseRelease(InputEvent.BUTTON2_MASK);
    }

    public void getScreen() throws Exception {
        Thread.sleep(2000);
        GraphicsConfiguration graphicsConfiguration = screenDevice.getDefaultConfiguration();
        Rectangle bounds = graphicsConfiguration.getBounds();
        System.out.println(bounds);
        BufferedImage bufferedImage = robot.createScreenCapture(bounds);

        File imgFile = new File("D:/screen.png");
        ImageIO.write(bufferedImage, "png", imgFile);
    }

    public void getCheck() throws Exception {
        Thread.sleep(2000);
        GraphicsConfiguration graphicsConfiguration = screenDevice.getDefaultConfiguration();
        Rectangle bounds = graphicsConfiguration.getBounds();
        BufferedImage bufferedImage = robot.createScreenCapture(bounds);

        File imgFile = new File("D:/check.png");
        ImageIO.write(bufferedImage.getSubimage(0, 0, 20, 20), "png", imgFile);
    }

    public void checkScreen() throws IOException {
        GraphicsConfiguration graphicsConfiguration = screenDevice.getDefaultConfiguration();
        Rectangle bounds = graphicsConfiguration.getBounds();
        System.out.println(bounds);
        BufferedImage bufferedImage = robot.createScreenCapture(bounds);
        // for (int x = 0; x < bufferedImage.getWidth(); x++) {
        // for (int y = 0; y < bufferedImage.getHeight(); y++) {
        // }
        // }
        BufferedImage checkBufferedImage = ImageIO.read(new File("D:/check.png"));
        int diff = 0;
        for (int x = 0; x < checkBufferedImage.getWidth(); x++) {
            for (int y = 0; y < checkBufferedImage.getHeight(); y++) {
                System.out.println(x + "," + y + ":" + Integer.toHexString(checkBufferedImage.getRGB(x, y)) + ","
                        + Integer.toHexString(bufferedImage.getRGB(x, y)));
                if (checkBufferedImage.getRGB(x, y) != bufferedImage.getRGB(x, y)) {
                    diff++;
                }
            }
        }
        System.out.println(diff);
    }

    public void s() throws Exception {
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_ALT);
        Thread.sleep(10);
    }

    public void test() throws Exception {
        BufferedImage checkBufferedImage = ImageIO.read(new File("D:/check.png"));
        W: while (true) {
            Thread.sleep(2000);
            System.out.println("check");
            GraphicsConfiguration graphicsConfiguration = screenDevice.getDefaultConfiguration();
            Rectangle bounds = graphicsConfiguration.getBounds();
            BufferedImage bufferedImage = robot.createScreenCapture(bounds);
            for (int x = 0; x < checkBufferedImage.getWidth(); x++) {
                for (int y = 0; y < checkBufferedImage.getHeight(); y++) {
                    if (checkBufferedImage.getRGB(x, y) != bufferedImage.getRGB(x, y)) {
                        continue W;
                    }
                }
            }
            break;
        }
        runPaste("test success");
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        // app.runProcess("notepad");
        // app.runKey();
        // app.runPaste("test");
        // app.runMouse();
        // app.getScreen();

        Thread.sleep(3000);
        // app.getCheck();
        // app.checkScreen();
        app.test();
    }
}
