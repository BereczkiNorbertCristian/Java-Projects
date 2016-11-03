package CollectionsTest;

import Collections.IToyList;
import Collections.ToyList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bnorbert on 02.11.2016.
 * bnorbertcristian@gmail.com
 */
public class ToyListTest {

    IToyList<String> outList;

    @Before
    public void setUp(){

        outList=new ToyList<String>();
    }

    @Test
    public void allMethodsTest(){

        outList.add("43");
        outList.add("2");

        Assert.assertEquals(2,outList.size());

        outList.add("5");

        Assert.assertEquals(3,outList.size());

        outList.remove(2);

        Assert.assertEquals(2,outList.size());

        Assert.assertEquals("2",outList.get(1));

        outList.clear();

        Assert.assertEquals(0,outList.size());

        String outString=outList.toString();
        Assert.assertEquals(0,outString.length());

        outList.add("43");
        outList.add("56");

        Assert.assertEquals(6,outList.toString().length());

    }



}
