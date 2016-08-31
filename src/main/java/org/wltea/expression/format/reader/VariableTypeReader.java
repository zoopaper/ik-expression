package org.wltea.expression.format.reader;

import org.wltea.expression.format.Element;
import org.wltea.expression.format.Element.ElementType;
import org.wltea.expression.format.ExpressionReader;
import org.wltea.expression.format.FormatException;

import java.io.IOException;

/**
 * 读取一个词段
 *
 * @author xxxx
 * @version 2.0
 *          Sep 21, 2008
 */
public class VariableTypeReader implements ElementReader {

    public static final String STOP_CHAR = "+-*/%^<>=&|!?:#$(),[]'\" \r\n\t";//词段的结束符

    public static final String TRUE_WORD = "true";
    public static final String FALSE_WORD = "false";
    public static final String NULL_WORD = "null";

    /**
     * @param reader
     * @return
     * @throws FormatException
     */
    private String readWord(ExpressionReader reader) throws FormatException, IOException {
        StringBuffer sb = new StringBuffer();
        boolean readStart = true;
        int b = -1;
        while ((b = reader.read()) != -1) {
            char c = (char) b;
            //单词停止符,并且忽略第一个字符
            if (STOP_CHAR.indexOf(c) >= 0 && !readStart) {
                reader.reset();
                return sb.toString();
            }
            if (!Character.isJavaIdentifierPart(c)) {
                throw new FormatException("名称不能为非法字符：" + c);
            }
            if (readStart) {
                if (!Character.isJavaIdentifierStart(c)) {
                    throw new FormatException("名称开头不能为字符：" + c);
                }
                readStart = false;
            }
            sb.append(c);
            reader.mark(0);
        }
        return sb.toString();
    }

    public Element read(ExpressionReader expressionReader) throws FormatException, IOException {
        int index = expressionReader.getCurrentIndex();
        String word = readWord(expressionReader);

        if (TRUE_WORD.equals(word) || FALSE_WORD.equals(word)) {
            return new Element(word, index, ElementType.BOOLEAN);
        } else if (NULL_WORD.equals(word)) {
            return new Element(word, index, ElementType.NULL);
        } else {
            return new Element(word, index, ElementType.VARIABLE);
        }
    }

}
