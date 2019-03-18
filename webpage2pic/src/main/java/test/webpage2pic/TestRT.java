package test.webpage2pic;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.webpage2pic.util.Config;

public class TestRT {
    private final GraphicsDevice screenDevice;
    private final Robot robot;
    private final Runtime currRuntime;
    private final Clipboard clipboard;
    private final String chromeFileStr;
    private final String htmlDir;

    private final String exportDir;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final int gap = 10;
    private final FilenameFilter htmlFF = new FilenameFilter() {

        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(".html");
        }
    };

    public TestRT() throws AWTException {
        this.screenDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.robot = new Robot(screenDevice);
        this.currRuntime = Runtime.getRuntime();
        this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        this.chromeFileStr = Config.get("chromeFileStr");
        this.htmlDir = Config.get("htmlDir");
        this.exportDir = Config.get("exportDir");
    }

    public void run() throws Exception {
        logger.info("run start");
        File htmlFile = new File(htmlDir);
        File exportFile = new File(exportDir);
        if (htmlFile.exists() && htmlFile.isDirectory() && exportFile.exists() && exportFile.isDirectory()) {
            String[] htmlFileStrs = htmlFile.list(htmlFF);
            Process chromeProcess = startChromeProcess();
            for (String htmlFileStr : htmlFileStrs) {
                logger.info("run : " + htmlFileStr);
                runChromeTab(htmlFileStr);
            }
            endChromeProcess(chromeProcess);
        }
        logger.info("run end");
    }

    private Process startChromeProcess() throws IOException, InterruptedException {
        Process process = currRuntime.exec(chromeFileStr);
        Thread.sleep(1500);
        robot.keyPress(KeyEvent.VK_F11);
        robot.keyRelease(KeyEvent.VK_F11);
        Thread.sleep(100);
        robot.keyPress(KeyEvent.VK_F11);
        robot.keyRelease(KeyEvent.VK_F11);
        Thread.sleep(100);
        return process;
    }

    private void endChromeProcess(Process process) throws IOException, InterruptedException {
        process.waitFor();
        process.destroy();
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_F4);
        robot.keyRelease(KeyEvent.VK_ALT);
    }

    private void runChromeTab(String htmlFileStr) throws IOException, InterruptedException {
        robot.keyPress(KeyEvent.VK_F6);
        robot.keyRelease(KeyEvent.VK_F6);
        Thread.sleep(1000);

        clipboard.setContents(new StringSelection(htmlDir + htmlFileStr), null);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        robot.keyPress(KeyEvent.VK_F11);
        robot.keyRelease(KeyEvent.VK_F11);
        Thread.sleep(2000);

        savePic(htmlFileStr);
        robot.keyPress(KeyEvent.VK_F11);
        robot.keyRelease(KeyEvent.VK_F11);
    }

    private void savePic(String htmlFileStr) throws IOException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int xSize = screenSize.width / 4;
        int ySize = screenSize.height / 4;

        Rectangle screenRectangle = new Rectangle(0, 0, xSize, ySize);
        BufferedImage image = robot.createScreenCapture(screenRectangle);

        int[] xRGB = new int[xSize];
        int[] yRGB = new int[ySize];
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                xRGB[x] += image.getRGB(x, y);
                yRGB[y] += image.getRGB(x, y);
            }
        }

        int def = image.getRGB(0, 0);
        int xDef = def * ySize;
        int yDef = def * xSize;
        int xMin = -1;
        int yMin = -1;
        int xMax = -1;
        int yMax = -1;

        for (int i = 0; i < xRGB.length; i++) {
            if (xRGB[i] != xDef) {
                xMin = i;
                break;
            }
        }
        for (int i = xRGB.length - 1; i >= 0; i--) {
            if (xRGB[i] != xDef) {
                xMax = i;
                break;
            }
        }
        for (int i = 0; i < yRGB.length; i++) {
            if (yRGB[i] != yDef) {
                yMin = i;
                break;
            }
        }
        for (int i = yRGB.length - 1; i >= 0; i--) {
            if (yRGB[i] != yDef) {
                yMax = i;
                break;
            }
        }
        screenRectangle = new Rectangle((xMin > gap ? (xMin - gap) : 0), (yMin > gap ? (yMin - gap) : 0), (xMax - xMin + 2 * gap), (yMax - yMin + 2 * gap));
//        StringBuilder sb = new StringBuilder();
//        sb.append(screenRectangle.getX()).append(",").append(screenRectangle.getY()).append(",").append(screenRectangle.getWidth()).append(",")
//                .append(screenRectangle.getHeight());
//        System.out.println(sb.toString());

        File f = new File(exportDir, htmlFileStr.replace(".html", ".png"));
        ImageIO.write(robot.createScreenCapture(screenRectangle), "png", f);
    }

//    private void de() {
//        System.setProperty("webdriver.chrome.driver", "/chrome.exe");
//
//        String urlStr = tempDir + "test3.html";
//        URL url = new URL("file:///" + urlStr);
//
//        ChromeDriverService chromeDriverService = ChromeDriverService.createDefaultService();
//        ReflectionUtils.invockField(DriverService.class, "url", chromeDriverService, url);
//        System.out.println(chromeDriverService.getUrl());
//        ChromeOptions chromeOptions = new ChromeOptions();
//        WebDriver driver = new ChromeDriver(chromeDriverService, chromeOptions);
//        driver.manage().window().maximize();
//        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
//        driver.get(urlStr);
//        System.out.println("tittle : " + driver.getTitle());
//        driver.quit();
//    }

    public static void main(String[] args) throws Exception {
        Config.init();
        new TestRT().run();
    }
}
