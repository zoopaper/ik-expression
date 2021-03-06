/**
 *
 */
package org.wltea.expression.format.reader;

import org.wltea.expression.format.Element;
import org.wltea.expression.format.Element.ElementType;
import org.wltea.expression.format.ExpressionReader;
import org.wltea.expression.format.FormatException;

import java.io.IOException;

/**
 * 读取分割符类型
 *
 * @author xxxx
 * @version 2.0
 *          Sep 21, 2008
 */
public class SeparateTypeReader implements ElementReader {

    public static final String SPLITOR_CHAR = "(),";//所有分割符

    /**
     * 从流中读取分割符类型的ExpressionToken
     *
     * @param expressionReader
     * @return
     * @throws FormatException 不是合法的分割符类型时抛出
     * @throws IOException
     */
    public Element read(ExpressionReader expressionReader) throws FormatException, IOException {
        int index = expressionReader.getCurrentIndex();
        int b = expressionReader.read();
        char c = (char) b;
        if (b == -1 || SPLITOR_CHAR.indexOf(c) == -1) {
            throw new FormatException("不是有效的分割字符");
        }
        return new Element(Character.toString(c), index, ElementType.SEPARATE);
    }
}
