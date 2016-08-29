package org.wltea.expression.test;

import org.junit.Test;
import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.datameta.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * User: shijingui
 * Date: 2016/8/29
 */
public class ExpressionTest {

    @Test
    public void test1() {
        String expression = "count > \"100\"";
        String expression2 = "223 == count";
        List<Variable> variableList = new ArrayList<Variable>();
        variableList.add(Variable.createVariable("count", new Double(223.0)));
        Object obj = ExpressionEvaluator.evaluate(expression2, variableList);
        System.out.println(obj);
    }
}
