package extensions;

import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;

import config.ConfigLoader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import log.LogHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class Utils {
    public static final String OS_VERSION = "os_version";
    public static final String DEVICE = "device";
    public static final String REAL_MOBILE = "real_mobile";
    public static final String BS_LOCAL = "browserstack.local";
    public static final String BS_DEBUG = "browserstack.debug";
    public static final String URL = "https://" + ConfigLoader.getConfig("username_bs") + ":" + ConfigLoader.getConfig("password_bs") + "@hub-cloud.browserstack.com/wd/hub";


    //Get the game name and change it to normal name
    public static String changeGameNameToNormalGameName(String gameName) {
        if (gameName != null) {
            return StringUtils.capitalize(gameName);
        } else {
            return "please input game name";
        }
    }

    //Sleep function
    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            LogHelper.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    //get Random string base on formatted String
    public static String getRandomString() {
        String RANDOM = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder random = new StringBuilder();
        Random rnd = new Random();
        while (random.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * RANDOM.length());
            random.append(RANDOM.charAt(index));
        }
        return random.toString();

    }

    //get Random string base on formatted String
    public static String getRandomSpecialString() {
        String RANDOM = "BinhLe1234";
        StringBuilder random = new StringBuilder();
        Random rnd = new Random();
        while (random.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * RANDOM.length());
            random.append(RANDOM.charAt(index));
        }
        return random.toString();

    }

    //get Random String in the random 1->9
    public static String getRandomNumber() {
        String RANDOM = "1234567890";
        StringBuilder random = new StringBuilder();
        Random rnd = new Random();
        while (random.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * RANDOM.length());
            random.append(RANDOM.charAt(index));
        }
        return random.toString();
    }

    /**
     * Generates a random alphabetic string of the specified length.
     *
     * @param index The length of the random string to generate
     * @return A randomly generated alphabetic string of the given length
     */
    public static String getLongString(int index) {
        return RandomStringUtils.randomAlphabetic(index);
    }

    /**
     * Splits a string on a given character and returns the substring at the specified indeString jsonFilePath = FileExtensions.getRootProject() + FileReaderManager.getConfigReader().getMobileDataTestJsonFilePath("MenuCriticalCheckingNewAccount");x.
     *
     * @param inputtedString The original string to split
     * @param character      The character to split the string on
     * @param index          The index of the substring to return
     * @return The substring from the split inputtedString at the given index
     */
    public static String splitStringOnUsingCharacterAndIndex(String inputtedString, String character, int index) {
        String[] splitString = inputtedString.split(character);
        return splitString[index];
    }

    //get Random number for 1 to 80
    public static Integer getRandomNumberFrom1To80() {
        Random random = new Random();
        int low = 1;
        int high = 80;
        return random.nextInt(high - low) + low;
    }

    //get datetime base on the formatted
    public static String getDateFormat() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd.HHmmss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    //Get String data from JSON file by attribute

    /**
     * Gets a specific attribute value from a JSON file.
     *
     * @param jsonFilePath Path of the JSON file
     * @param attribute    Name of the attribute to extract
     * @return The attribute value as a string, empty string if not found
     */
    public static String getDataFromJsonFile(String jsonFilePath, String attribute) {
        JSONParser parser = new JSONParser();
        String data = "";
        try {
            // Read the JSON file
            Object obj = parser.parse(new FileReader(jsonFilePath));
            JSONObject jsonObject = (JSONObject) obj;
            // Extract the required data
            data = (String) jsonObject.get(attribute);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Switches the webdriver control to a newly opened tab/window.
     *
     * @param driver The webdriver instance
     */
    public static void switchingToNewTab(WebDriver driver) {
        ArrayList<String> newTab = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(newTab.get(1));
    }

    /**
     * Randomly selects an element from a list.
     *
     * @param listElement           The list to select an element from
     * @param numberOfPickedElement The number of elements to randomly select
     * @return A randomly selected element from the list
     */
    public static String getElementInGame(List<String> listElement, int numberOfPickedElement) {
        String randomElement = "";
        Random rand = new Random();
        List<String> givenList = listElement;
        for (int i = 0; i < numberOfPickedElement; i++) {
            int randomIndex = rand.nextInt(givenList.size());
            randomElement = givenList.get(randomIndex);
        }
        return randomElement;
    }

    /**
     * Removes all non-alphanumeric characters from the given string.
     *
     * @param inputtedString The original string
     * @return The string with all special characters removed
     */
    public static String replaceAllSpecialCharacter(String inputtedString) {
        return inputtedString.replaceAll("[^a-zA-Z0-9]", "");
    }

    /**
     * Opens the most recently downloaded file on the user's machine.
     *
     * @throws Exception If file open fails
     */
    public static void openTheLatestDownloadedFile() throws Exception {
        File downloadsFolder = new File(System.getProperty("user.home") + "/Downloads");
        File[] files = downloadsFolder.listFiles();
        File latestFile = Arrays.stream(files != null ? files : new File[0]).filter(File::isFile).max(Comparator.comparing(File::lastModified)).orElse(null);
        LogHelper.info("\nFile:" + (latestFile != null ? latestFile.getName() : null));
        Desktop.getDesktop().open(latestFile);
    }

    /**
     * Extracts the user token from a given URL.
     *
     * @param url The URL to extract the token from
     * @return The decoded user token string
     * @throws Exception If URL is invalid or token cannot be extracted
     */
    public static String getUserToken(String url) throws Exception {
        String[] parts = url.split("UserToken=");
        String[] temps = parts[1].split("&SessionId");
        return URLDecoder.decode(temps[0], "UTF-8");
    }

    public static String getFakeUSCreditCardNumber(CreditCardType creditCardType) {
        Faker fakerWithLocales = new Faker(Locale.US);
        return fakerWithLocales.finance().creditCard(creditCardType).replace("-", "");
    }

    public static void writeToJSONFile(String objectName, String value, String folderType, String fileName) {
        String fileDirectory;
        if (folderType.equals("web")) {
            fileDirectory = String.format("/src/test/resources/jsonFiles/jsonTestDataFile/webTest/%s.json", fileName);
        } else {
            fileDirectory = String.format("/src/test/resources/jsonFiles/jsonTestDataFile/mobileTest/%s.json", fileName);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(objectName, value);
        try {
            FileWriter file = new FileWriter(FileExtensions.getRootProject() + fileDirectory);
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            throw new RuntimeException("Error when processing the JSON file");
        }
    }

    public static AppiumDriver BrowserStack(AppiumDriver driver, String device) throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        //iOS devices
        if (device.matches("iPhone 11 iOS13")) {
            caps.setCapability(OS_VERSION, "13.0");
            caps.setCapability(DEVICE, "iPhone 11");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 11 iOS14")) {
            caps.setCapability(OS_VERSION, "14");
            caps.setCapability(DEVICE, "iPhone 11");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 11 Pro iOS13")) {
            caps.setCapability(OS_VERSION, "13.0");
            caps.setCapability(DEVICE, "iPhone 11 Pro");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 11 Pro iOS15")) {
            caps.setCapability(OS_VERSION, "15.0");
            caps.setCapability(DEVICE, "iPhone 11 Pro");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 11 Pro Max iOS13")) {
            caps.setCapability(OS_VERSION, "13.0");
            caps.setCapability(DEVICE, "iPhone 11 Pro Max");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 11 Pro Max iOS14")) {
            caps.setCapability(OS_VERSION, "14.0");
            caps.setCapability(DEVICE, "iPhone 11 Pro Max");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 12 Mini iOS14")) {
            caps.setCapability(OS_VERSION, "14.0");
            caps.setCapability(DEVICE, "iPhone 12 Mini");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 12 Pro Max iOS14")) {
            caps.setCapability(OS_VERSION, "14.0");
            caps.setCapability(DEVICE, "iPhone 12 Pro Max");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 12 Pro iOS14")) {
            caps.setCapability(OS_VERSION, "14.0");
            caps.setCapability(DEVICE, "iPhone 12 Pro");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 12 iOS14")) {
            caps.setCapability(OS_VERSION, "14.0");
            caps.setCapability(DEVICE, "iPhone 12");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 13 Mini iOS15")) {
            caps.setCapability(OS_VERSION, "15.0");
            caps.setCapability(DEVICE, "iPhone 13 Mini");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 13 Pro Max iOS15")) {
            caps.setCapability(OS_VERSION, "15.0");
            caps.setCapability(DEVICE, "iPhone 13 Pro Max");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 13 Pro iOS15")) {
            caps.setCapability(OS_VERSION, "15.0");
            caps.setCapability(DEVICE, "iPhone 13 Pro");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 13 iOS15")) {
            caps.setCapability(OS_VERSION, "15.0");
            caps.setCapability(DEVICE, "iPhone 13");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone XS Max iOS12")) {
            caps.setCapability(OS_VERSION, "12.0");
            caps.setCapability(DEVICE, "iPhone XS Max");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            ////caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone XS iOS12")) {
            caps.setCapability(OS_VERSION, "12.0");
            caps.setCapability(DEVICE, "iPhone XS");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone XS iOS13")) {
            caps.setCapability(OS_VERSION, "13.0");
            caps.setCapability(DEVICE, "iPhone XS");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone XS iOS14")) {
            caps.setCapability(OS_VERSION, "14.0");
            caps.setCapability(DEVICE, "iPhone XS");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone XS iOS15")) {
            caps.setCapability(OS_VERSION, "15.0");
            caps.setCapability(DEVICE, "iPhone XS");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone XR iOS12")) {
            caps.setCapability(OS_VERSION, "12.0");
            caps.setCapability(DEVICE, "iPhone XR");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone X iOS11")) {
            caps.setCapability(OS_VERSION, "11.0");
            caps.setCapability(DEVICE, "iPhone X");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 8 Plus iOS12")) {
            caps.setCapability(OS_VERSION, "12.0");
            caps.setCapability(DEVICE, "iPhone 8 Plus");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 8 Plus iOS11")) {
            caps.setCapability(OS_VERSION, "11.0");
            caps.setCapability(DEVICE, "iPhone 8 Plus");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 8 iOS12")) {
            caps.setCapability(OS_VERSION, "12.0");
            caps.setCapability(DEVICE, "iPhone 8");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 8 iOS11")) {
            caps.setCapability(OS_VERSION, "11.0");
            caps.setCapability(DEVICE, "iPhone 8");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 8 iOS13")) {
            caps.setCapability(OS_VERSION, "13.0");
            caps.setCapability(DEVICE, "iPhone 8");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 8 iOS15")) {
            caps.setCapability(OS_VERSION, "15.0");
            caps.setCapability(DEVICE, "iPhone 8");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 7 iOS10")) {
            caps.setCapability(OS_VERSION, "10.0");
            caps.setCapability(DEVICE, "iPhone 7");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 7 iOS12")) {
            caps.setCapability(OS_VERSION, "12.0");
            caps.setCapability(DEVICE, "iPhone 7");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 6S Plus iOS11")) {
            caps.setCapability(OS_VERSION, "11.0");
            caps.setCapability(DEVICE, "iPhone 6S Plus");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 6S iOS12")) {
            caps.setCapability(OS_VERSION, "12.0");
            caps.setCapability(DEVICE, "iPhone 6S");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 6S iOS11")) {
            caps.setCapability(OS_VERSION, "11.0");
            caps.setCapability(DEVICE, "iPhone 6S");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone 6 iOS11")) {
            caps.setCapability(OS_VERSION, "11.0");
            caps.setCapability(DEVICE, "iPhone 6");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone SE 2020 iOS13")) {
            caps.setCapability(OS_VERSION, "13.0");
            caps.setCapability(DEVICE, "iPhone SE 2020");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPhone SE iOS11")) {
            caps.setCapability(OS_VERSION, "11.0");
            caps.setCapability(DEVICE, "iPhone SE");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPad Pro 9_7 2016 iOS11")) {
            caps.setCapability(OS_VERSION, "11.0");
            caps.setCapability(DEVICE, "iPad Pro 9.7 2016");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            ////caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPad Pro 12_9 2021 iOS14")) {
            caps.setCapability(OS_VERSION, "14.0");
            caps.setCapability(DEVICE, "iPad Pro 12.9 2021");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPad Pro 12_9 2020 iOS14")) {
            caps.setCapability(OS_VERSION, "14.0");
            caps.setCapability(DEVICE, "iPad Pro 12.9 2020");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPad Pro 12_9 2020 iOS13")) {
            caps.setCapability(OS_VERSION, "13.0");
            caps.setCapability(DEVICE, "iPad Pro 12.9 2020");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPad Pro 12_9 2018 iOS15")) {
            caps.setCapability(OS_VERSION, "15.0");
            caps.setCapability(DEVICE, "iPad Pro 12.9 2018");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPad Pro 12_9 2018 iOS13")) {
            caps.setCapability(OS_VERSION, "13.0");
            caps.setCapability(DEVICE, "iPad Pro 12.9 2018");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPad Pro 12_9 2018 iOS12")) {
            caps.setCapability(OS_VERSION, "12.0");
            caps.setCapability(DEVICE, "iPad Pro 12.9 2018");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPad Pro 12_9 iOS11")) {
            caps.setCapability(OS_VERSION, "11.0");
            caps.setCapability(DEVICE, "iPad Pro 12.9");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPad Pro 11 2021 iOS14")) {
            caps.setCapability(OS_VERSION, "14.0");
            caps.setCapability(DEVICE, "iPad Pro 11 2021");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPad Pro 11 2020 iOS13")) {
            caps.setCapability(OS_VERSION, "13.0");
            caps.setCapability(DEVICE, "iPad Pro 11 2020");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPad Pro 11 2018 iOS12")) {
            caps.setCapability(OS_VERSION, "12.0");
            caps.setCapability(DEVICE, "iPad Pro 11 2018");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPad Mini 4 iOS11")) {
            caps.setCapability(OS_VERSION, "11.0");
            caps.setCapability(DEVICE, "iPad Mini 4");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPad Mini 2019 iOS13")) {
            caps.setCapability(OS_VERSION, "13.0");
            caps.setCapability(DEVICE, "iPad Mini 2019");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPad Mini 2019 iOS12")) {
            caps.setCapability(OS_VERSION, "12.0");
            caps.setCapability(DEVICE, "iPad Mini 2019");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPad Air 4 iOS14")) {
            caps.setCapability(OS_VERSION, "14.0");
            caps.setCapability(DEVICE, "iPad Air 4");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPad Air 2019 iOS13")) {
            caps.setCapability(OS_VERSION, "13.0");
            caps.setCapability(DEVICE, "iPad Air 2019");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPad Air 2019 iOS12")) {
            caps.setCapability(OS_VERSION, "12.0");
            caps.setCapability(DEVICE, "iPad Air 2019");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPad 9th iOS15")) {
            caps.setCapability(OS_VERSION, "15.0");
            caps.setCapability(DEVICE, "iPad 9th");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPad 8th iOS14")) {
            caps.setCapability(OS_VERSION, "14.0");
            caps.setCapability(DEVICE, "iPad 8th");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPad 7th iOS13")) {
            caps.setCapability(OS_VERSION, "13.0");
            caps.setCapability(DEVICE, "iPad 7th");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPad 6th iOS11")) {
            caps.setCapability(OS_VERSION, "11.0");
            caps.setCapability(DEVICE, "iPad 6th");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }
        if (device.matches("iPad 5th iOS11")) {
            caps.setCapability(OS_VERSION, "11.0");
            caps.setCapability(DEVICE, "iPad 5th");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            //caps.setCapability("browserstack.appium_version", "1.21.0");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new IOSDriver(new URL(URL), caps);
        }

        //Android devices
        if (device.matches("Google Pixel 6 OS12")) {
            caps.setCapability(OS_VERSION, "12.0");
            caps.setCapability(DEVICE, "Pixel 6");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Google Pixel 6 Pro OS12")) {
            caps.setCapability(OS_VERSION, "12.0");
            caps.setCapability(DEVICE, "Pixel 6 Pro");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Vivo Y50 OS10")) {
            caps.setCapability(OS_VERSION, "10.0");
            caps.setCapability(DEVICE, "Vivo Y50");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Google Nexus 5 OS44")) {
            caps.setCapability(OS_VERSION, "4.4");
            caps.setCapability(DEVICE, "Google Nexus 5");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Google Nexus 6 OS6")) {
            caps.setCapability(OS_VERSION, "6.0");
            caps.setCapability(DEVICE, "Google Nexus 6");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Google Nexus 6 OS5")) {
            caps.setCapability(OS_VERSION, "5.0");
            caps.setCapability(DEVICE, "Google Nexus 6");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Google Nexus 9 OS51")) {
            caps.setCapability(OS_VERSION, "5.1");
            caps.setCapability(DEVICE, "Google Nexus 9");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Google Pixel 2 OS9")) {
            caps.setCapability(OS_VERSION, "9.0");
            caps.setCapability(DEVICE, "Google Pixel 2");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Google Pixel 2 OS8")) {
            caps.setCapability(OS_VERSION, "8.0");
            caps.setCapability(DEVICE, "Google Pixel 2");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Google Pixel 3 XL OS9")) {
            caps.setCapability(OS_VERSION, "9.0");
            caps.setCapability(DEVICE, "Google Pixel 3 XL");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Google Pixel 3a XL OS9")) {
            caps.setCapability(OS_VERSION, "9.0");
            caps.setCapability(DEVICE, "Google Pixel 3a XL");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Google Pixel 3a OS9")) {
            caps.setCapability(OS_VERSION, "9.0");
            caps.setCapability(DEVICE, "Google Pixel 3a");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Google Pixel 3 OS9")) {
            caps.setCapability(OS_VERSION, "9.0");
            caps.setCapability(DEVICE, "Google Pixel 3");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Google Pixel 3 OS10")) {
            caps.setCapability(OS_VERSION, "10.0");
            caps.setCapability(DEVICE, "Google Pixel 3");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Google Pixel 4 XL OS10")) {
            caps.setCapability(OS_VERSION, "10.0");
            caps.setCapability(DEVICE, "Google Pixel 4 XL");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Google Pixel 4 OS10")) {
            caps.setCapability(OS_VERSION, "10.0");
            caps.setCapability(DEVICE, "Google Pixel 4");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Google Pixel 4 OS11")) {
            caps.setCapability(OS_VERSION, "11.0");
            caps.setCapability(DEVICE, "Google Pixel 4");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Google Pixel 5 OS11")) {
            caps.setCapability(OS_VERSION, "11.0");
            caps.setCapability(DEVICE, "Google Pixel 5");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Google Pixel 5 OS12")) {
            caps.setCapability(OS_VERSION, "12.0");
            caps.setCapability(DEVICE, "Google Pixel 5");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Google Pixel OS8")) {
            caps.setCapability(OS_VERSION, "8.0");
            caps.setCapability(DEVICE, "Google Pixel");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Huawei P30 OS9")) {
            caps.setCapability(OS_VERSION, "9.0");
            caps.setCapability(DEVICE, "Huawei P30");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Motorola Moto G 2nd Gen OS5")) {
            caps.setCapability(OS_VERSION, "5.0");
            caps.setCapability(DEVICE, "Motorola Moto G 2nd Gen");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Motorola Moto X 2nd Gen OS6")) {
            caps.setCapability(OS_VERSION, "6.0");
            caps.setCapability(DEVICE, "Motorola Moto X 2nd Gen");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Motorola Moto G7 Play OS9")) {
            caps.setCapability(OS_VERSION, "9.0");
            caps.setCapability(DEVICE, "Motorola Moto G7 Play");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Motorola Moto G9 Play OS10")) {
            caps.setCapability(OS_VERSION, "10.0");
            caps.setCapability(DEVICE, "Motorola Moto G9");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Xiaomi Redmi Note 8 OS9")) {
            caps.setCapability(OS_VERSION, "9.0");
            caps.setCapability(DEVICE, "Xiaomi Redmi Note 8");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Redmi Note 7 OS9")) {
            caps.setCapability(OS_VERSION, "9.0");
            caps.setCapability(DEVICE, "Xiaomi Redmi Note 7");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Xiaomi Redmi Note 9 OS10")) {
            caps.setCapability(OS_VERSION, "10.0");
            caps.setCapability(DEVICE, "Xiaomi Redmi Note 9");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("OnePlus 6T OS9")) {
            caps.setCapability(OS_VERSION, "9.0");
            caps.setCapability(DEVICE, "OnePlus 6T");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("OnePlus 8 OS10")) {
            caps.setCapability(OS_VERSION, "10.0");
            caps.setCapability(DEVICE, "OnePlus 8");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("OnePlus 7T OS10")) {
            caps.setCapability(OS_VERSION, "10.0");
            caps.setCapability(DEVICE, "OnePlus 7T");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("OnePlus 7 OS9")) {
            caps.setCapability(OS_VERSION, "9.0");
            caps.setCapability(DEVICE, "OnePlus 7");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("OnePlus 9 OS11")) {
            caps.setCapability(OS_VERSION, "11.0");
            caps.setCapability(DEVICE, "OnePlus 9");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Oppo Reno 3 Pro OS10")) {
            caps.setCapability(OS_VERSION, "10.0");
            caps.setCapability(DEVICE, "Oppo Reno 3 Pro");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy A8 OS71")) {
            caps.setCapability(OS_VERSION, "7.1");
            caps.setCapability(DEVICE, "Samsung Galaxy A8");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy A10 OS9")) {
            caps.setCapability(OS_VERSION, "9.0");
            caps.setCapability(DEVICE, "Samsung Galaxy A10");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy A51 OS10")) {
            caps.setCapability(OS_VERSION, "10.0");
            caps.setCapability(DEVICE, "Samsung Galaxy A51");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy A11 OS10")) {
            caps.setCapability(OS_VERSION, "10.0");
            caps.setCapability(DEVICE, "Samsung Galaxy A11");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy J7 Prime OS8")) {
            caps.setCapability(OS_VERSION, "8.1");
            caps.setCapability(DEVICE, "Samsung Galaxy J7 Prime");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy Note 10 Plus OS9")) {
            caps.setCapability(OS_VERSION, "9.0");
            caps.setCapability(DEVICE, "Samsung Galaxy Note 10 Plus");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy Note 10 OS9")) {
            caps.setCapability(OS_VERSION, "9.0");
            caps.setCapability(DEVICE, "Samsung Galaxy Note 10");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy Note 20 Ultra OS10")) {
            caps.setCapability(OS_VERSION, "10.0");
            caps.setCapability(DEVICE, "Samsung Galaxy Note 20 Ultra");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy Note 20 OS10")) {
            caps.setCapability(OS_VERSION, "10.0");
            caps.setCapability(DEVICE, "Samsung Galaxy Note 20");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy Note 9 OS81")) {
            caps.setCapability(OS_VERSION, "8.1");
            caps.setCapability(DEVICE, "Samsung Galaxy Note 9");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy Note 8 OS71")) {
            caps.setCapability(OS_VERSION, "7.1");
            caps.setCapability(DEVICE, "Samsung Galaxy Note 8");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability("browserstack.selenium_version", "3.5.2");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy S10 Plus OS9")) {
            caps.setCapability(OS_VERSION, "9.0");
            caps.setCapability(DEVICE, "Samsung Galaxy S10 Plus");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy S10e OS9")) {
            caps.setCapability(OS_VERSION, "9.0");
            caps.setCapability(DEVICE, "Samsung Galaxy S10e");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy S10 OS9")) {
            caps.setCapability(OS_VERSION, "9.0");
            caps.setCapability(DEVICE, "Samsung Galaxy S10");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy S20 OS10")) {
            caps.setCapability(OS_VERSION, "10.0");
            caps.setCapability(DEVICE, "Samsung Galaxy S20");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy S20 Plus OS10")) {
            caps.setCapability(OS_VERSION, "10.0");
            caps.setCapability(DEVICE, "Samsung Galaxy S20 Plus");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy S20 Ultra OS10")) {
            caps.setCapability(OS_VERSION, "10.0");
            caps.setCapability(DEVICE, "Samsung Galaxy S20 Ultra");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy S22 OS12")) {
            caps.setCapability(OS_VERSION, "12.0");
            caps.setCapability(DEVICE, "Samsung Galaxy S22");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy S22 Plus OS12")) {
            caps.setCapability(OS_VERSION, "12.0");
            caps.setCapability(DEVICE, "Samsung Galaxy S22 Plus");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy S22 Ultra OS12")) {
            caps.setCapability(OS_VERSION, "12.0");
            caps.setCapability(DEVICE, "Samsung Galaxy S22 Ultra");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy S21 OS11")) {
            caps.setCapability(OS_VERSION, "11.0");
            caps.setCapability(DEVICE, "Samsung Galaxy S21");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy S21 Plus OS11")) {
            caps.setCapability(OS_VERSION, "11.0");
            caps.setCapability(DEVICE, "Samsung Galaxy S21 Plus");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy S21 Ultra OS11")) {
            caps.setCapability(OS_VERSION, "11.0");
            caps.setCapability(DEVICE, "Samsung Galaxy S21 Ultra");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy S7 OS6")) {
            caps.setCapability(OS_VERSION, "6.0");
            caps.setCapability(DEVICE, "Samsung Galaxy S7");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy S6 OS5")) {
            caps.setCapability(OS_VERSION, "5.0");
            caps.setCapability(DEVICE, "Samsung Galaxy S6");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy S8 OS7")) {
            caps.setCapability(OS_VERSION, "7.0");
            caps.setCapability(DEVICE, "Samsung Galaxy S8");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy S8 Plus OS7")) {
            caps.setCapability(OS_VERSION, "7.0");
            caps.setCapability(DEVICE, "Samsung Galaxy S8 Plus");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy S8 Plus OS9")) {
            caps.setCapability(OS_VERSION, "9.0");
            caps.setCapability(DEVICE, "Samsung Galaxy S8 Plus");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy S9 OS8")) {
            caps.setCapability(OS_VERSION, "8.0");
            caps.setCapability(DEVICE, "Samsung Galaxy S9");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy S9 Plus OS8")) {
            caps.setCapability(OS_VERSION, "8.0");
            caps.setCapability(DEVICE, "Samsung Galaxy S9 Plus");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy S9 Plus OS9")) {
            caps.setCapability(OS_VERSION, "9.0");
            caps.setCapability(DEVICE, "Samsung Galaxy S9 Plus");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy Tab 4 OS44")) {
            caps.setCapability(OS_VERSION, "4.4");
            caps.setCapability(DEVICE, "Samsung Galaxy Tab 4");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(BS_DEBUG, "true");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy Tab S7 OS10")) {
            caps.setCapability(OS_VERSION, "10.0");
            caps.setCapability(DEVICE, "Samsung Galaxy Tab S7");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy Tab S6 OS9")) {
            caps.setCapability(OS_VERSION, "9.0");
            caps.setCapability(DEVICE, "Samsung Galaxy Tab S6");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy Tab S5e OS9")) {
            caps.setCapability(OS_VERSION, "9.0");
            caps.setCapability(DEVICE, "Samsung Galaxy Tab S5e");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy Tab S4 OS8")) {
            caps.setCapability(OS_VERSION, "8.1");
            caps.setCapability(DEVICE, "Samsung Galaxy Tab S4");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }
        if (device.matches("Samsung Galaxy Tab S3 OS8")) {
            caps.setCapability(OS_VERSION, "8.0");
            caps.setCapability(DEVICE, "Samsung Galaxy Tab S3");
            caps.setCapability(REAL_MOBILE, "true");
            caps.setCapability(BS_LOCAL, "false");
            caps.setCapability(CapabilityType.ROTATABLE, "true");
            return driver = new AndroidDriver(new URL(URL), caps);
        }

        return new AppiumDriver(new URL(URL), caps);
    }

    public static void takeScreenshotOnBS(AppiumDriver driver) {
        ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    }

    public static void rotatePortraitOnBS(AppiumDriver driver) {
        ((AppiumDriver) driver).rotate(ScreenOrientation.PORTRAIT);
    }

    public static void rotateLandscapeOnBS(AppiumDriver driver) {
        ((AppiumDriver) driver).rotate(ScreenOrientation.LANDSCAPE);
    }

    public static void clearCacheChrome(WebDriver driver){
        driver.get("chrome://settings/clearBrowserData");
        driver.findElement(By.xpath("//settings-ui")).sendKeys(Keys.TAB);
        driver.findElement(By.xpath("//settings-ui")).sendKeys(Keys.ENTER);
    }

    public static String separate() {
        return System.getProperty("file.separator");
    }

    public static void openHTMLFile(String url) throws IOException {
        File htmlFile = new File(url);
        Desktop.getDesktop().browse(htmlFile.toURI());
    }

    public static String getLast4Digit(String creditCardNumber) {
        if (creditCardNumber.length() >= 4) {
            return "ending in " + creditCardNumber.substring(creditCardNumber.length() - 4);
        } else {
            return "Please input correct creditCardNumber";
        }
    }
}
