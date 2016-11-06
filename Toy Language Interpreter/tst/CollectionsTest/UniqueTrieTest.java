package CollectionsTest;

import Collections.UniqueTrie;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.PrintStream;

/**
 * Created by bnorbert on 06.11.2016.
 * bnorbertcristian@gmail.com
 */
public class UniqueTrieTest {


    @Test
    public void uniqueTrieTest(){

        UniqueTrie uniqueTrie=new UniqueTrie(8);
        try {

            System.setOut(new PrintStream("/dev/null"));
        }
        catch (Exception e){
            Assert.assertTrue(false);
        }
        System.out.println(uniqueTrie.uniqueValue());
        System.out.println(uniqueTrie.uniqueValue());
        System.out.println(uniqueTrie.uniqueValue());
        System.out.println(uniqueTrie.uniqueValue());
        System.out.println(uniqueTrie.uniqueValue());
        System.out.println(uniqueTrie.uniqueValue());

        System.out.println(uniqueTrie.uniqueValue());
        System.out.println(uniqueTrie.uniqueValue());
        System.out.println("________");
        Assert.assertTrue(uniqueTrie.removeValue(3));
        Assert.assertTrue(uniqueTrie.removeValue(5));
        Assert.assertTrue(uniqueTrie.removeValue(7));

        System.out.println(uniqueTrie.uniqueValue());
        System.out.println(uniqueTrie.uniqueValue());
        System.out.println(uniqueTrie.uniqueValue());

    }

}
