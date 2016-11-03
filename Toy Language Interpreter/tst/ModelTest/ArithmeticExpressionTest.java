package ModelTest;

import Collections.IToyList;
import Collections.IToyMap;
import Exceptions.DivisionByZeroException;
import Model.ArithmeticExpression;
import Model.ConstantExpression;
import Model.Expression;
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

    @Before
    public void setUp() {

        arithExp1 = new ArithmeticExpression(new ArithmeticExpression(new ConstantExpression(3), new ConstantExpression(4), 3),
                new ArithmeticExpression(new ConstantExpression(4), new ConstantExpression(3), 4), 1);
        arithExp2 = new ArithmeticExpression(new ConstantExpression(4), new ConstantExpression(3), 2);
        arithExp3 = new ArithmeticExpression(new ConstantExpression(5), new ConstantExpression(0), 4);
    }

    @Test
    public void evalTest() {

        try {
            Assert.assertEquals(13, arithExp1.eval(table));
            Assert.assertEquals(1, arithExp2.eval(table));
        } catch (Exception e) {
            Assert.assertTrue(false);
        }

        try {
            arithExp3.eval(table);
            Assert.assertTrue(false);
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(7, arithExp1.toString().length());

    }
}
