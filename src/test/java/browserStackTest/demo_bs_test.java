package browserStackTest;

import org.testng.annotations.Test;
import setup.BrowserStackTestNGSetupBase;

public class demo_bs_test extends BrowserStackTestNGSetupBase {

    @Test(groups = {"group1"})
    public void getInfo(){
        getDriver().navigate().to("https://google.com");
    }

    @Test(groups = {"group2"})
    public void failedTest(){
        getDriver().findElementByClassName("abc");
    }
}
