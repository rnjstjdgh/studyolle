package com.soungho.studyolle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SimpleJavaTest {

    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver"; // 드라이버 ID
    public static final String WEB_DRIVER_PATH = "/Users/frank/study/infran/studyolle/studyolle/src/main/resources/chromedriver"; // 드라이버 경로

    public static void main(String[] args) throws InterruptedException {
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://naver.com");

        Thread.sleep(100000000);
        driver.quit();
    }
}
