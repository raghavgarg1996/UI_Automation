package org.example;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {
    public static void main(String[] args) throws Throwable {
        final String IMAGES_PATH = System.getProperty("user.dir") + "/UI_Automation/src/main/resources/Images/";

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.warnerbros.com/movies/shazam-fury-gods");

        // Used to create a base image
        // takeSnapShot(driver, IMAGES_PATH+"Expected.png");

        spotTheDiffernce(driver, IMAGES_PATH + "Expected.png", IMAGES_PATH + "Actual.png", IMAGES_PATH + "Difference.png");
        driver.quit();
    }

    public static void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception {
        TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(fileWithPath);
        FileUtils.copyFile(SrcFile, DestFile);
    }

    public static void spotTheDiffernce(WebDriver webdriver, String expectedImagePath,
                                        String actualImageScreenshotPath,
                                        String DiffernceImagePath) throws Throwable {
        BufferedImage expectedImage = ImageIO.read(new File(expectedImagePath));
        takeSnapShot(webdriver, actualImageScreenshotPath);
        BufferedImage actualImage = ImageIO.read(new File(actualImageScreenshotPath));
        ImageDiffer imgDiff = new ImageDiffer();
        ImageDiff diff = imgDiff.makeDiff(actualImage, expectedImage);
        BufferedImage diffImage = diff.getDiffImage();
        ImageIO.write(diff.getMarkedImage(), "PNG", new File(DiffernceImagePath));
        System.out.println("\n diffImage= " + diffImage.getColorModel());
    }
}