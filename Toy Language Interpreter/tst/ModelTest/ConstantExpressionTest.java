package ModelTest;

import Collections.Heap;
import Collections.IToyMap;
import Mocks.ToyMapMock;
import Model.Expressions.ConstantExpression;
import Model.Expressions.Expression;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bnorbert on 02.11.2016.
 * bnorbertcristian@gmail.com
 */
public class ConstantExpressionTest {

    Expression expression;
    IToyMap<String, Integer> table;
    Heap<Integer> heap;

    @Before
    public void setUp() {
        expression = new ConstantExpression(4);
        table = new ToyMapMock();
        heap=new Heap<>();
    }

    @Test
    public void evalTest() {

        try {
            Assert.assertEquals(4, expression.eval(table,heap));
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
        Assert.assertEquals(1, expression.toString().length());
    }

}
