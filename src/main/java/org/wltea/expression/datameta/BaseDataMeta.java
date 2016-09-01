/**
 *
 */
package org.wltea.expression.datameta;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 基础数据描述对象
 *
 * @author xxxx
 * @version 2.0
 *          2008-09-23
 */
public abstract class BaseDataMeta {

    /**
     * 数据类型
     */
    DataType dataType;
    /**
     * 值
     */
    Object dataValue;
    //引用类型标识
    private boolean isReference;

    public BaseDataMeta(DataType dataType, Object dataValue) {
        this.dataType = dataType;
        this.dataValue = dataValue;
        //参数类型校验
        verifyDataMeta();
    }

    public DataType getDataType() {
        if (isReference) {
            return this.getReference().getDataType();
        } else {
            return dataType;
        }
    }

    public Object getDataValue() {
        return dataValue;
    }


    @SuppressWarnings("unchecked")
    public String getDataValueText() {
        if (dataValue == null) {
            return null;
        } else if (DataType.DATE == this.dataType) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) dataValue);

        } else if (BaseDataMeta.DataType.LIST == this.dataType) {
            StringBuffer buff = new StringBuffer("[");
            List col = (List) dataValue;
            for (Object o : col) {
                if (o == null) {
                    buff.append("null, ");
                } else if (o instanceof Date) {
                    buff.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) o)).append(", ");
                } else {
                    buff.append(o.toString()).append(", ");
                }
            }
            buff.append("]");
            if (buff.length() > 2) {
                buff.delete(buff.length() - 3, buff.length() - 1);
            }
            return buff.toString();

        } else {
            return dataValue.toString();
        }
    }

    /**
     * 获取Token的字符窜类型值
     *
     * @return
     */
    public String getStringValue() {
        return getDataValueText();
    }

    /**
     * 获取Token的boolean类型值
     *
     * @return
     */
    public Boolean getBooleanValue() {
        if (DataType.BOOLEAN != this.dataType) {
            throw new UnsupportedOperationException("当前常量类型不支持此操作");
        }
        return (Boolean) dataValue;
    }

    /**
     * 获取Token的int类型值
     *
     * @return
     */
    public Integer getIntegerValue() {

        if (DataType.INT != this.dataType) {
            throw new UnsupportedOperationException("当前常量类型不支持此操作");
        }
        return (Integer) dataValue;
    }

    /**
     * 获取Token的long类型值
     *
     * @return
     */
    public Long getLongValue() {
        if (DataType.INT != this.dataType && DataType.LONG != this.dataType) {
            throw new UnsupportedOperationException("当前常量类型不支持此操作");
        }
        if (dataValue == null) {
            return null;
        }
        return Long.valueOf(dataValue.toString());
    }

    /**
     * 获取Token的float类型值
     *
     * @return
     */
    public Float getFloatValue() {
        if (DataType.INT != this.dataType && DataType.FLOAT != this.dataType && DataType.LONG != this.dataType) {
            throw new UnsupportedOperationException("当前常量类型不支持此操作");
        }
        if (dataValue == null) {
            return null;
        }
        return Float.valueOf(dataValue.toString());
    }

    /**
     * 获取Token的double类型值
     *
     * @return
     */
    public Double getDoubleValue() {
        if (DataType.INT != this.dataType
                && DataType.LONG != this.dataType
                && DataType.FLOAT != this.dataType
                && DataType.DOUBLE != this.dataType) {
            throw new UnsupportedOperationException("当前常量类型不支持此操作");
        }
        if (dataValue == null) {
            return null;
        }
        return Double.valueOf(dataValue.toString());
    }

    /**
     * 获取Token的Date类型值
     *
     * @return
     * @throws ParseException
     */
    public Date getDateValue() {
        if (DataType.DATE != this.dataType) {
            throw new UnsupportedOperationException("当前常量类型不支持此操作");
        }
        return (Date) dataValue;
    }

    /**
     * 获取数据的集合对象
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object> getCollection() {
        if (DataType.LIST != this.dataType) {
            throw new UnsupportedOperationException("当前常量类型不支持此操作");
        }
        return (List<Object>) dataValue;
    }

    /**
     * 获取Token的引用对象
     *
     * @return
     */
    public Reference getReference() {
        if (!this.isReference) {
            throw new UnsupportedOperationException("当前常量类型不支持此操作");
        }
        return (Reference) dataValue;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;

        } else if (o instanceof BaseDataMeta) {

            BaseDataMeta bdo = (BaseDataMeta) o;
            if (this.isReference() && bdo.isReference) {
                return this.getReference() == bdo.getReference();
            }

            if (bdo.dataType == dataType) {
                if (bdo.dataValue != null
                        && bdo.dataValue.equals(dataValue)) {
                    return true;
                } else if (bdo.dataValue == null
                        && dataValue == null) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    /**
     * 校验数据类型和值得合法性
     */
    protected void verifyDataMeta() {
        if (dataType != null && dataValue != null) {
            if (DataType.NULL == dataType && dataValue != null) {
                throw new IllegalArgumentException("数据类型不匹配; 类型：" + dataType + ",值不为空");

            } else if (DataType.BOOLEAN == dataType) {
                try {
                    getBooleanValue();
                } catch (UnsupportedOperationException e) {
                    throw new IllegalArgumentException("数据类型不匹配; 类型：" + dataType + ",值:" + dataValue);
                }

            } else if (DataType.DATE == dataType) {
                try {
                    getDateValue();
                } catch (UnsupportedOperationException e) {
                    throw new IllegalArgumentException("数据类型不匹配; 类型：" + dataType + ",值:" + dataValue);
                }

            } else if (DataType.DOUBLE == dataType) {
                try {
                    getDoubleValue();
                } catch (UnsupportedOperationException e) {
                    throw new IllegalArgumentException("数据类型不匹配; 类型：" + dataType + ",值:" + dataValue);
                }

            } else if (DataType.FLOAT == dataType) {
                try {
                    getFloatValue();
                } catch (UnsupportedOperationException e) {
                    throw new IllegalArgumentException("数据类型不匹配; 类型：" + dataType + ",值:" + dataValue);
                }

            } else if (DataType.INT == dataType) {
                try {
                    getIntegerValue();
                } catch (UnsupportedOperationException e) {
                    throw new IllegalArgumentException("数据类型不匹配; 类型：" + dataType + ",值:" + dataValue);
                }

            } else if (DataType.LONG == dataType) {
                try {
                    getLongValue();
                } catch (UnsupportedOperationException e) {
                    throw new IllegalArgumentException("数据类型不匹配; 类型：" + dataType + ",值:" + dataValue);
                }

            } else if (DataType.STRING == dataType) {
                try {
                    getStringValue();
                } catch (UnsupportedOperationException e) {
                    throw new IllegalArgumentException("数据类型不匹配; 类型：" + dataType + ",值:" + dataValue);
                }

            } else if (DataType.LIST == dataType) {
                try {
                    getCollection();
                } catch (UnsupportedOperationException e) {
                    throw new IllegalArgumentException("数据类型不匹配; 类型：" + dataType + ",值:" + dataValue);
                }

            } else if (this.isReference) {
                try {
                    getReference();
                } catch (UnsupportedOperationException e) {
                    throw new IllegalArgumentException("数据类型不匹配; 类型：" + dataType + ",值:" + dataValue);
                }

            } else if (DataType.OBJECT == dataType) {
                try {
                    getDataValue();
                } catch (UnsupportedOperationException e) {
                    throw new IllegalArgumentException("数据类型不匹配; 类型：" + dataType + ",值:" + dataValue);
                }

            }
        }
    }

    public Class<?> mapTypeToJavaClass() {

        if (BaseDataMeta.DataType.BOOLEAN == this.getDataType()) {
            return boolean.class;

        } else if (BaseDataMeta.DataType.DATE == this.getDataType()) {
            return Date.class;

        } else if (BaseDataMeta.DataType.DOUBLE == this.getDataType()) {
            return double.class;

        } else if (BaseDataMeta.DataType.FLOAT == this.getDataType()) {
            return float.class;

        } else if (BaseDataMeta.DataType.INT == this.getDataType()) {
            return int.class;

        } else if (BaseDataMeta.DataType.LONG == this.getDataType()) {
            return long.class;

        } else if (BaseDataMeta.DataType.STRING == this.getDataType()) {
            return String.class;

        } else if (BaseDataMeta.DataType.LIST == this.getDataType()) {
            return List.class;

        } else if (BaseDataMeta.DataType.OBJECT == this.getDataType()) {
            return Object.class;

        } else if (BaseDataMeta.DataType.NULL == this.getDataType()) {
            return null;

        }
        throw new RuntimeException("映射Java类型失败：无法识别的数据类型");
    }

    /**
     * 检查数据类型的兼容性
     * 类型相同，一定兼容
     * 类型不同，则可兼容的数据类型包括int ，long ，float ， double
     * null 类型与所有类型兼容
     *
     * @param another
     * @return
     */
    private boolean isCompatibleType(BaseDataMeta another) {

        if (DataType.NULL == this.getDataType()
                || DataType.NULL == another.getDataType()) {
            return true;

        } else if (this.getDataType() == another.getDataType()) {
            return true;

        } else if (DataType.INT != this.getDataType()
                && DataType.LONG != this.getDataType()
                && DataType.FLOAT != this.getDataType()
                && DataType.DOUBLE != this.getDataType()) {
            return false;

        } else if (DataType.INT != another.getDataType()
                && DataType.LONG != another.getDataType()
                && DataType.FLOAT != another.getDataType()
                && DataType.DOUBLE != another.getDataType()) {
            return false;

        } else {
            return true;
        }
    }

    /**
     * 获取两数的兼容类型
     * 如果两个数据类型无法兼容，返回null
     *
     * @param another
     * @return
     */
    public DataType getCompatibleType(BaseDataMeta another) {

        if (isCompatibleType(another)) {
            if (DataType.NULL == this.getDataType()) {
                return another.getDataType();

            } else if (DataType.NULL == another.getDataType()) {
                return this.getDataType();

            } else if (this.getDataType() == another.getDataType()) {
                return this.getDataType();

            } else if (DataType.DOUBLE == this.getDataType()
                    || DataType.DOUBLE == another.getDataType()) {
                return DataType.DOUBLE;

            } else if (DataType.FLOAT == this.getDataType()
                    || DataType.FLOAT == another.getDataType()) {
                return DataType.FLOAT;

            } else if (DataType.LONG == this.getDataType()
                    || DataType.LONG == another.getDataType()) {
                return DataType.LONG;

            } else {
                return DataType.INT;
            }
        } else {
            return null;
        }
    }

    /**
     * @throws ParseException
     */
    public Object toJavaObject() throws ParseException {
        if (null == this.dataValue) {
            return null;
        }

        if (BaseDataMeta.DataType.BOOLEAN == this.getDataType()) {
            return getBooleanValue();

        } else if (BaseDataMeta.DataType.DATE == this.getDataType()) {
            return getDateValue();

        } else if (BaseDataMeta.DataType.DOUBLE == this.getDataType()) {
            return getDoubleValue();

        } else if (BaseDataMeta.DataType.FLOAT == this.getDataType()) {
            return getFloatValue();

        } else if (BaseDataMeta.DataType.INT == this.getDataType()) {
            return getIntegerValue();

        } else if (BaseDataMeta.DataType.LONG == this.getDataType()) {
            return getLongValue();

        } else if (BaseDataMeta.DataType.STRING == this.getDataType()) {
            return getStringValue();

        } else if (BaseDataMeta.DataType.LIST == this.getDataType()) {
            return getCollection();

        } else if (BaseDataMeta.DataType.OBJECT == this.getDataType()) {
            return getDataValue();

        } else {
            throw new RuntimeException("映射Java类型失败：无法识别的数据类型");
        }
    }

    public boolean isReference() {
        return isReference;
    }

    void setReference(boolean isReference) {
        this.isReference = isReference;
    }

    /**
     * 数据类型
     */
    public enum DataType {

        /**
         * NULL类型
         */
        NULL,
        /**
         * 字符串
         */
        STRING,
        /**
         * 布尔类
         */
        BOOLEAN,
        /**
         * 整数
         */
        INT,
        /**
         * 长整数
         */
        LONG,
        /**
         * 浮点数
         */
        FLOAT,
        /**
         * 双精度浮点
         */
        DOUBLE,
        /**
         * 日期时间
         */
        DATE,
        /**
         * 集合对象
         */
        LIST,
        /**
         * 通用对象类型
         */
        OBJECT,;

    }

}
