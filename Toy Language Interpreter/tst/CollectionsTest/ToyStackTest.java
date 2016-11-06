package CollectionsTest;

import Collections.IToyStack;
import Collections.ToyStack;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bnorbert on 04.11.2016.
 * bnorbertcristian@gmail.com
 */
public class ToyStackTest {

    IToyStack<String> stk;

    @Before
    public void setUp(){

        stk=new ToyStack<>();
        stk.push("First");
        stk.push("Second");
    }

    @Test
    public void allMethodsTest(){

        try {
            Assert.assertEquals("Second", stk.pop());
        }
        catch (Exception e){
            Assert.assertTrue(false);
        }
        Assert.assertEquals("First",stk.peek());
        Assert.assertEquals("First",stk.toString().trim());
        Assert.assertEquals(false,stk.empty());
        Assert.assertEquals(1,stk.size());

    }

}
