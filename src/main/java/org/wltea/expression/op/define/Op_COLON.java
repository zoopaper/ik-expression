/**
 *
 */
package org.wltea.expression.op.define;

import org.wltea.expression.IllegalExpressionException;
import org.wltea.expression.datameta.BaseDataMeta;
import org.wltea.expression.datameta.Constant;
import org.wltea.expression.op.IOperatorExecution;
import org.wltea.expression.op.Operator;

/**
 * @author xxxx
 * @version 2.0
 *          2009-02-06
 */
public class Op_COLON implements IOperatorExecution {

    public static final Operator THIS_OPERATOR = Operator.COLON;


    public Constant execute(Constant[] args) {
        throw new UnsupportedOperationException("操作符\"" + THIS_OPERATOR.getToken() + "不支持该方法");
    }

    public Constant verify(int opPosition, BaseDataMeta[] args) throws IllegalExpressionException {
        throw new UnsupportedOperationException("操作符\"" + THIS_OPERATOR.getToken() + "不支持该方法");
    }


}
