/**
 *
 */
package org.wltea.expression.format.reader;

import org.wltea.expression.format.Element;
import org.wltea.expression.format.ExpressionReader;
import org.wltea.expression.format.FormatException;

import java.io.IOException;

/**
 * @author xxxx
 * @version 2.0
 *          Oct 9, 2008
 */
public interface ElementReader {
    /**
     * @param expressionReader
     * @return
     * @throws FormatException
     * @throws IOException
     */
    public Element read(ExpressionReader expressionReader) throws FormatException, IOException;
}
