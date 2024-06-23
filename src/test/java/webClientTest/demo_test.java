package webClientTest;

import config.ConfigLoader;
import org.testng.Assert;
import org.testng.annotations.Test;
import setup.WebTestNGSetupBase;

public class demo_test extends WebTestNGSetupBase{
    @Test
    public void checkInfo(){
        System.out.println(ConfigLoader.getConfig("username_bs"));
        System.out.println(ConfigLoader.getConfig("password_bs"));
        System.out.println(ConfigLoader.getConfig("browser"));
    }

    @Test()
    public void checkinfo2(){
        Assert.fail("Write your custom error message");
    }
}
