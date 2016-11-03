package ModelTest;

import Collections.IToyList;
import Collections.IToyMap;
import Mocks.ToyMapMock;
import Model.ConstantExpression;
import Model.Expression;
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

    @Before
    public void setUp() {
        expression = new ConstantExpression(4);
        table = new ToyMapMock();
    }

    @Test
    public void evalTest() {

        try {
            Assert.assertEquals(4, expression.eval(table));
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
        Assert.assertEquals(1, expression.toString().length());
    }

}
