package driverManager;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

public class DriverManager {
    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    private static ThreadLocal<RemoteWebDriver> browserStackDriver = new ThreadLocal<>();
    private DriverManager(){
    }

    //region WebDriver "Chrome" section
    public static void initWebDriver(String browser) {
        WebDriver _webDriver = DriverCreator.getWebDriver(browser);
        webDriver.set(_webDriver);
    }
    public static void removeWebDriver(){
        getWebDriver().quit();
    }
    public static WebDriver getWebDriver() {
        return webDriver.get();
    }
    //endregion

    //region Appium BrowserStack "DeviceName" section
    public static void initBrowserStackDriver(String device, String osVersion) throws MalformedURLException {
        RemoteWebDriver _browserStackDriver = DriverCreator.getBrowserStackDriver(device, osVersion);
        browserStackDriver.set(_browserStackDriver);
    }
    public static RemoteWebDriver getBrowserStackDriver() {
        return browserStackDriver.get();
    }
    public static void quitBrowserStackDriver(){
        getBrowserStackDriver().quit();
    }
    //endregion
}
