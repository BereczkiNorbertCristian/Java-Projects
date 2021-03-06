package ModelTest;

import Collections.Heap;
import Collections.IToyMap;
import Collections.ToyMap;
import Model.Expressions.ArithmeticExpression;
import Model.Expressions.ConstantExpression;
import Model.Expressions.Expression;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bnorbert on 02.11.2016.
 * bnorbertcristian@gmail.com
 */
public class ArithmeticExpressionTest {

    Expression arithExp1, arithExp2, arithExp3;
    IToyMap<String, Integer> table;
    Heap<Integer> heap;

    @Before
    public void setUp() {

        arithExp1 = new ArithmeticExpression(new ArithmeticExpression(new ConstantExpression(3), new ConstantExpression(4), "*"),
                new ArithmeticExpression(new ConstantExpression(4), new ConstantExpression(3), "/"), "+");
        arithExp2 = new ArithmeticExpression(new ConstantExpression(4), new ConstantExpression(3), "-");
        arithExp3 = new ArithmeticExpression(new ConstantExpression(5), new ConstantExpression(0), "/");
        heap=new Heap<>();
        table=new ToyMap<String,Integer>();
    }

    @Test
    public void evalTest() {

        try {
            Assert.assertEquals(13, arithExp1.eval(table,heap));
            Assert.assertEquals(1, arithExp2.eval(table,heap));
        } catch (Exception e) {
            Assert.assertTrue(false);
        }

        try {
            arithExp3.eval(table,heap);
            Assert.assertTrue(false);
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(7, arithExp1.toString().length());

    }
}
