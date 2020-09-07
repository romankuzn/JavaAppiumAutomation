import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    @Test
    public void testGetClassString() {
        MainClass main = new MainClass();
        String returnedString = main.getClass_string();
        boolean isFound = returnedString.contains("Hello") || returnedString.contains("hello");
        Assert.assertTrue("Hello or hello is NOT returned by getClassString", isFound);
    }

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
