package CollectionsTest;

import Collections.IToyMap;
import Collections.ToyMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bnorbert on 02.11.2016.
 * bnorbertcristian@gmail.com
 */
public class ToyMapTest {

    IToyMap<String,Integer> table;

    @Before
    public void setUp(){
        table=new ToyMap<String,Integer>();
    }

    @Test
    public void lookupRemovePutTest(){

        table.put("a",3);
        table.put("b",4);
        try {
            Assert.assertEquals(3,table.lookup("a").intValue());
            Assert.assertEquals(4,table.lookup("b").intValue());
        }
        catch (Exception e){
            Assert.assertTrue(false);
        }
        table.remove("a");
        try{
            table.lookup("a");
            Assert.assertTrue(false);
        }
        catch (Exception e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void sizeToStringTest(){

        table.put("a",3);
        table.put("b",4);

        Assert.assertEquals(2,table.size());

        String outToString=table.toString();
        String toBeTested="b = 4\na = 3\n";
        Assert.assertEquals(toBeTested,outToString);

    }


}
