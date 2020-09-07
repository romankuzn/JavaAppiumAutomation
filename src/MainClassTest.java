import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    @Test
    public void testGetClassNumber() {
        MainClass main = new MainClass();
        Assert.assertTrue("getClassNumber returns value <= 45",main.getClass_number() > 45);
    }

    @Test
    public void testGetLocalNumber() {
        MainClass main = new MainClass();
        Assert.assertTrue("getLocalNumber does NOT return 14", main.getLocalNumber() == 14);
    }
}
