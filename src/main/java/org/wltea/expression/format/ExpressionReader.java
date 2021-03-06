/**
 *
 */
package org.wltea.expression.format;

import org.wltea.expression.format.reader.ElementReader;
import org.wltea.expression.format.reader.ElementReaderFactory;

import java.io.IOException;
import java.io.StringReader;
import java.nio.CharBuffer;

/**
 * 表达式读取器
 *
 * @author xxxx
 * @version 2.0
 *          Sep 21, 2008
 */
public class ExpressionReader extends StringReader {
    /**
     * 词元间的忽略字符
     */
    private static final String IGNORE_CHAR = " \r\n\t";
    /**
     * 当前索引
     */
    private int currentIndex = 0;
    /**
     * 被标记后索引
     */
    private int markIndex = 0;
    /**
     * 与上一个读到的ElementToken之间是否有空格
     */
    private boolean prefixBlank = false;

    public ExpressionReader(String s) {
        super(s);
    }

    public static void main(String[] a) {
        ExpressionReader eReader = new ExpressionReader(" aa+\"A\n" + "B\\\\CD\"!=null&&[2008-1-1 12:9]-$max(aa,bb,\"cc\")>2l3f4d1");
        Element ele = null;
        try {
            while ((ele = eReader.readToken()) != null) {
                System.out.println(ele.getType() + "……" + ele.getText() + "……" + ele.getIndex());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取得当前位置
     *
     * @return
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Element之前是否有空格
     *
     * @return
     */
    public boolean isPrefixBlank() {
        return prefixBlank;
    }

    public void setPrefixBlank(boolean prefixBlank) {
        this.prefixBlank = prefixBlank;
    }

    @Override
    public int read() throws IOException {
        int c = super.read();
        if (c != -1) {
            currentIndex++;
            markIndex++;
        }
        return c;
    }

    @Override
    public int read(char[] cbuf) throws IOException {
        int c = super.read(cbuf);
        if (c > 0) {
            currentIndex += c;
            markIndex += c;
        }
        return c;
    }

    @Override
    public int read(CharBuffer target) throws IOException {
        int c = super.read(target);
        if (c > 0) {
            currentIndex += c;
            markIndex += c;
        }
        return c;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int c = super.read(cbuf, off, len);
        if (c > 0) {
            currentIndex += c;
            markIndex += c;
        }
        return c;
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        currentIndex = currentIndex - markIndex;
    }

    @Override
    public void mark(int readAheadLimit) throws IOException {
        super.mark(readAheadLimit);
        markIndex = 0;
    }

    /**
     * 以流形式读取ExpressionToken
     *
     * @return Element
     * @throws IOException
     * @throws FormatException
     */
    public Element readToken() throws IOException, FormatException {
        prefixBlank = false;
        while (true) {
            //去除空格
            mark(0);//标记
            int b = read();
            if (b == -1) {
                return null;
            }
            char c = (char) b;
            //去除开始的空格
            if (IGNORE_CHAR.indexOf(c) >= 0) {
                prefixBlank = true;
                continue;
            }
            //重置
            reset();
            //构造一个词元读取器
            ElementReader er = ElementReaderFactory.createElementReader(this);

            return er.read(this);
        }
    }
}
