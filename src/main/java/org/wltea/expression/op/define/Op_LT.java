/**
 *
 */
package org.wltea.expression.op.define;

import org.wltea.expression.IllegalExpressionException;
import org.wltea.expression.datameta.BaseDataMeta;
import org.wltea.expression.datameta.Constant;
import org.wltea.expression.datameta.Reference;
import org.wltea.expression.op.IOperatorExecution;
import org.wltea.expression.op.Operator;

/**
 * 逻辑小于操作
 *
 * @author xxxx
 * @version 2.0
 *          Sep 27, 2008
 */
public class Op_LT implements IOperatorExecution {

    public static final Operator THIS_OPERATOR = Operator.LT;


    public Constant execute(Constant[] args) throws IllegalExpressionException {
        if (args == null || args.length != 2) {
            throw new IllegalArgumentException("操作符\"" + THIS_OPERATOR.getToken() + "参数个数不匹配");
        }

        Constant first = args[1];
        if (null == first || null == first.getDataValue()) {
            //抛NULL异常
            throw new NullPointerException("操作符\"" + THIS_OPERATOR.getToken() + "\"参数为空");
        }

        Constant second = args[0];
        if (null == second || null == second.getDataValue()) {
            //抛NULL异常
            throw new NullPointerException("操作符\"" + THIS_OPERATOR.getToken() + "\"参数为空");
        }

        //如果第一参数为引用，则执行引用
        if (first.isReference()) {
            Reference firstRef = (Reference) first.getDataValue();
            first = firstRef.execute();
        }
        //如果第二参数为引用，则执行引用
        if (second.isReference()) {
            Reference secondRef = (Reference) second.getDataValue();
            second = secondRef.execute();
        }

        if (BaseDataMeta.DataType.DATE == first.getDataType() && BaseDataMeta.DataType.DATE == second.getDataType()) {
            //日期类型比较
            int result = first.getDateValue().compareTo(second.getDateValue());
            if (result < 0) {
                return new Constant(BaseDataMeta.DataType.BOOLEAN, Boolean.TRUE);
            } else {
                return new Constant(BaseDataMeta.DataType.BOOLEAN, Boolean.FALSE);
            }

        } else if (BaseDataMeta.DataType.STRING == first.getDataType() && BaseDataMeta.DataType.STRING == second.getDataType()) {
            //字窜类型比较
            int result = first.getStringValue().compareTo(second.getStringValue());
            if (result < 0) {
                return new Constant(BaseDataMeta.DataType.BOOLEAN, Boolean.TRUE);
            } else {
                return new Constant(BaseDataMeta.DataType.BOOLEAN, Boolean.FALSE);
            }

        } else if ((BaseDataMeta.DataType.DOUBLE == first.getDataType()
                || BaseDataMeta.DataType.FLOAT == first.getDataType()
                || BaseDataMeta.DataType.LONG == first.getDataType()
                || BaseDataMeta.DataType.INT == first.getDataType())
                &&
                (BaseDataMeta.DataType.DOUBLE == second.getDataType()
                        || BaseDataMeta.DataType.FLOAT == second.getDataType()
                        || BaseDataMeta.DataType.LONG == second.getDataType()
                        || BaseDataMeta.DataType.INT == second.getDataType())

                ) {
            //数值类型比较，全部转换成double
            int result = Double.compare(first.getDoubleValue(), second.getDoubleValue());
            if (result < 0) {
                return new Constant(BaseDataMeta.DataType.BOOLEAN, Boolean.TRUE);
            } else {
                return new Constant(BaseDataMeta.DataType.BOOLEAN, Boolean.FALSE);
            }
        } else {
            //LT操作不支持其他类型，抛异常
            throw new IllegalArgumentException("操作符\"" + THIS_OPERATOR.getToken() + "\"参数类型错误");

        }
    }

    /* (non-Javadoc)
     * @see org.wltea.expression.op.IOperatorExecution#verify(int, org.wltea.expression.ExpressionToken[])
     */
    public Constant verify(int opPositin, BaseDataMeta[] args)
            throws IllegalExpressionException {

        if (args == null) {
            throw new IllegalArgumentException("运算操作符参数为空");
        }
        if (args.length != 2) {
            //抛异常
            throw new IllegalExpressionException("操作符\"" + THIS_OPERATOR.getToken() + "\"参数个数不匹配"
                    , THIS_OPERATOR.getToken()
                    , opPositin
            );
        }

        BaseDataMeta first = args[1];
        BaseDataMeta second = args[0];
        if (first == null || second == null) {
            throw new NullPointerException("操作符\"" + THIS_OPERATOR.getToken() + "\"参数为空");
        }

        if (BaseDataMeta.DataType.DATE == first.getDataType()
                && BaseDataMeta.DataType.DATE == second.getDataType()) {
            //日期类型比较
            return new Constant(BaseDataMeta.DataType.BOOLEAN, Boolean.FALSE);

        } else if (BaseDataMeta.DataType.STRING == first.getDataType()
                && BaseDataMeta.DataType.STRING == second.getDataType()) {
            //字窜类型比较
            return new Constant(BaseDataMeta.DataType.BOOLEAN, Boolean.FALSE);

        } else if ((BaseDataMeta.DataType.DOUBLE == first.getDataType()
                || BaseDataMeta.DataType.FLOAT == first.getDataType()
                || BaseDataMeta.DataType.LONG == first.getDataType()
                || BaseDataMeta.DataType.INT == first.getDataType())
                &&
                (BaseDataMeta.DataType.DOUBLE == second.getDataType()
                        || BaseDataMeta.DataType.FLOAT == second.getDataType()
                        || BaseDataMeta.DataType.LONG == second.getDataType()
                        || BaseDataMeta.DataType.INT == second.getDataType())

                ) {
            //数值类型比较
            return new Constant(BaseDataMeta.DataType.BOOLEAN, Boolean.FALSE);

        } else {
            //LT操作不支持其他类型，抛异常
            throw new IllegalExpressionException("操作符\"" + THIS_OPERATOR.getToken() + "\"参数类型错误");

        }
    }
}
