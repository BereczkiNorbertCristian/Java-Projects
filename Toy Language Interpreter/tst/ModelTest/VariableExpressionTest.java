package ModelTest;

import Collections.IToyMap;
import Mocks.ToyMapMock;
import Model.Expressions.Expression;
import Model.Expressions.VariableExpression;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bnorbert on 02.11.2016.
 * bnorbertcristian@gmail.com
 */
public class VariableExpressionTest {

    Expression varExp;
    IToyMap<String, Integer> table;

    @Before
    public void setUp() {

        varExp = new VariableExpression("a");
        table = new ToyMapMock();
    }

    @Test
    public void evalTest() {

        try {
            Assert.assertEquals(3, varExp.eval(table));
        } catch (Exception e) {
            Assert.assertTrue(false);
        }

        Assert.assertEquals(1, varExp.toString().length());
    }

}
