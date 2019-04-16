package com.boroborome.stledtor.util;

public class CharIterator extends AbstractBufferIterator<Character> {
    private static final String LIKE = "like";
    private String buffer;
    private int nextIndex;

    public CharIterator(String buffer) {
        this.buffer = buffer;
        if (buffer == null) {
            throw new IllegalArgumentException("CharIterator constructer is not accept null paramter");
        }
    }

    @Override
    public boolean hasNext() {
        return nextIndex < buffer.length();
    }

    @Override
    public Character next() {
        if (nextIndex < buffer.length()) {
            curItem = Character.valueOf(buffer.charAt(nextIndex));
            nextIndex++;
        } else {
            curItem = null;
        }
        return curItem;
    }

    @Override
    public void remove() {
    }

    /**
     * 忽略所有制表符
     */
    public void skipSpace() {
        if (curItem == null || Character.isWhitespace(curItem.charValue())) {
            while (hasNext()) {
                if (!Character.isWhitespace(next().charValue())) {
                    return;
                }
            }
            next();
        }
    }

    /**
     * 操作符是由如下符号做成的字符串=/-*%!^&|&gt;&lt;
     *
     * @param ch
     * @return
     */
    public static boolean isOperation(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' ||
                ch == '&' || ch == '|' || ch == '!' || ch == '^' ||
                ch == '>' || ch == '<' || ch == '%' || ch == '=';
    }

    private static interface IValueCondition {
        boolean isHeadMatch(char ch);

        boolean isPartMatch(char ch);
    }

    private String readValue(IValueCondition ivc) {
        skipSpace();
        if (curItem == null || !ivc.isHeadMatch(curItem.charValue())) {
            return null;
        }
        int startIndex = nextIndex - 1;
        while (hasNext()) {
            if (!ivc.isPartMatch(next().charValue())) {
                return buffer.substring(startIndex, nextIndex - 1);
            }
        }
        next();//已经到了末尾，去掉最后的currentValue值
        return buffer.substring(startIndex, nextIndex);
    }

    private static IValueCondition operatorCondition = new IValueCondition() {
        @Override
        public boolean isHeadMatch(char ch) {
            return isOperation(ch);
        }

        @Override
        public boolean isPartMatch(char ch) {
            return isOperation(ch);
        }
    };

    /**
     * 读取一个操作符<br>
     * 操作符是由如下符号做成的字符串=/-*%!^&|&gt;&lt;
     *
     * @return
     */
    public String readOperator() {
        String operStr = readValue(operatorCondition);
        if (operStr == null) {
            if (buffer.length() - nextIndex + 1 > LIKE.length()) {
                operStr = buffer.substring(nextIndex - 1, nextIndex + LIKE.length() - 1);
                if (LIKE.equalsIgnoreCase(operStr)) {
                    nextIndex += (LIKE.length() - 1);
                    next();
                }
            }
        }
        return operStr;
    }

    /**
     * 读取一个Java标识符<br>
     * 自动跳过前面的制表符，如果后面没有标识符则返回null.
     *
     * @return
     */
    public String readIdentifier() {
        return readValue(identifierCondition);
    }

    private static IValueCondition identifierCondition = new IValueCondition() {
        @Override
        public boolean isHeadMatch(char ch) {
            return Character.isJavaIdentifierStart(ch);
        }

        @Override
        public boolean isPartMatch(char ch) {
            return Character.isJavaIdentifierPart(ch);
        }
    };

    /**
     * 从字符流中读取一个数字<br>
     * 自动跳过前面的制表符，如果后面没有数字会抛出NumberFormatException
     *
     * @return
     */
    public long readLong() {
        String longStr = readValue(longCondition);
        return Long.parseLong(longStr);
    }

    private static IValueCondition longCondition = new IValueCondition() {
        @Override
        public boolean isHeadMatch(char ch) {
            return Character.isDigit(ch) || ch == '-';
        }

        @Override
        public boolean isPartMatch(char ch) {
            return Character.isDigit(ch);
        }
    };

    /**
     * 从字符流中读取一个字符串<br>
     * 自动跳过前面的制表符，如果后面没有字符串则返回null<br>
     * 暂时不支持转意
     *
     * @return
     */
    public String readString() {
        skipSpace();
        if ('\"' != curItem.charValue()) {
            return null;
        }
        next();//跳过第一个引号
        int startIndex = nextIndex - 1;
        for (; hasNext() && next().charValue() != '\"'; ) ;
        String result = buffer.substring(startIndex, nextIndex - 1);
        next();//跳过最后的引号
        return result;
    }
}
