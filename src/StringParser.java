import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Semen on 22-Jan-17.
 */
public class StringParser {
    public static List<String> getOneLineComments(List<String> sourceList) {
        List<String> oneLineComments = new ArrayList<>();
        Pattern oneLineCommentPattern = Pattern.compile("//.+");
        Matcher matcher;
        String tempString;
        Iterator<String> iterator = sourceList.iterator();
        while (iterator.hasNext()) {
            matcher = oneLineCommentPattern.matcher(iterator.next());
            if (matcher.find()) {
                tempString = matcher.group();
                oneLineComments.add(tempString.substring(2, tempString.length()));
            }
        }
        return oneLineComments;
    }

    @NotNull
    public static String listToString(List<String> strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : strings) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    public static List<String> getManyLinesComments(String source) {
        List<String> manyLinesComments = new ArrayList<>();
        int firstIndex = source.indexOf("/**");
        int lastIndex;
        while (firstIndex != -1) {
            firstIndex = source.indexOf("/**");
            if (firstIndex == -1) break;
            lastIndex = source.indexOf("*/", firstIndex);
            manyLinesComments.add(source.substring(firstIndex + 2, lastIndex - 1).replaceAll("\\*", ""));
            source = source.substring(0, firstIndex) + source.substring(lastIndex, source.length());
        }
        firstIndex = source.indexOf("/*");
        while (firstIndex != -1) {
            firstIndex = source.indexOf("/*");
            if (firstIndex == -1) break;
            lastIndex = source.indexOf("*/", firstIndex);
            manyLinesComments.add(source.substring(firstIndex + 1, lastIndex - 1).replaceAll("\\*", ""));
            source = source.substring(0, firstIndex) + source.substring(lastIndex, source.length());
        }
        return manyLinesComments;
    }

    public static boolean isInCircleBrackets(String source, int index) {
        String stringBeforeSymbol = source.substring(0, index);
        return stringBeforeSymbol.lastIndexOf("(") > stringBeforeSymbol.lastIndexOf(")") && source.indexOf(")", index) < source.indexOf("(", index);
    }

    public static boolean isInFigureBrackets(String source, int index) {
        String stringBeforeSymbol = source.substring(0, index);
        return stringBeforeSymbol.lastIndexOf("{") > stringBeforeSymbol.lastIndexOf("}") && source.indexOf("}") < source.indexOf("{");
    }

    public static boolean isInQuotes(String source, int index) {
        String stringBeforeSymbol = source.substring(0, index);
        int quotesCount = 0;
        int quoteIndex = 0;
        while (stringBeforeSymbol.indexOf("\"", quoteIndex) != -1) {
            quotesCount++;
            quoteIndex = stringBeforeSymbol.indexOf("\"", quoteIndex) + 1;
        }
        return quotesCount % 2 == 1;
    }

    public static int lastIndexOfCharacter(String source, int index, char character) {
        String cuttedString = source.substring(0, index);
        return cuttedString.lastIndexOf(character);
    }
}
