import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Semen on 22-Jan-17.
 */
public class StringFormatter {
    public static String trimString(String s) {
        s = s.trim();
        while (s.contains("  ") || s.contains("\t") || s.contains("\n")) {
            s = s.replaceAll("\t", " ");
            s = s.replaceAll("\r", " ");
            s = s.replaceAll("\n", " ");
            s = s.replaceAll("  ", " ");
        }
        return s;
    }

    public static String addLineSeparators(String s) {
        char[] stringArray = s.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < stringArray.length; i++) {
            stringBuilder.append(stringArray[i]);
            switch (stringArray[i]) {
                case ';':
                    if (StringParser.isInCircleBrackets(s, i) || StringParser.isInQuotes(s, i)) {
                        break;
                    } else {
                        stringBuilder.append(System.lineSeparator());
                    }
                    break;
                case '{':
                    stringBuilder.append(System.lineSeparator());
                    break;
                case '}':
                    stringBuilder.append(System.lineSeparator());
                    break;
                case '/':
                    if (stringArray[i - 1] == '*') {
                        stringBuilder.append(System.lineSeparator());
                    }
                    break;
            }
        }
        return stringBuilder.toString();
    }

    public static String addTabulation(String s) {
        int nestingLevel = 0;
        char[] stringArray = s.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < stringArray.length; i++) {
            stringBuilder.append(stringArray[i]);
            switch (stringArray[i]) {
                case '{':
                    nestingLevel++;
                    break;
                case '}':
                    nestingLevel--;
                    break;
                case '\n':
                    if (i + 2 < stringArray.length) {
                        if (stringArray[i + 1] == '}' || stringArray[i + 2] == '}') {
                            for (int j = 0; j < nestingLevel - 1; j++) {
                                stringBuilder.append('\t');
                            }
                            break;
                        } else {
                            for (int j = 0; j < nestingLevel; j++) {
                                stringBuilder.append('\t');
                            }
                        }
                    }
                    break;
            }
        }
        return stringBuilder.toString().replaceAll("\t ", "\t").replaceAll(" \t", "\t");
    }

    public static String trimKeywords(String source) {
        String[] keywords = "catch finally".split(" ");
        for (String keyword : keywords) {
            int index = source.indexOf(keyword, 0);
            while (index != -1) {
                source = source.substring(0, StringParser.lastIndexOfCharacter(source, index, '}') + 1) + source.substring(index, source.length());
                index = source.indexOf(keyword, index + 1);
            }
        }
        return source;
    }

    public static String surroundOperators(String source) {
        String[] separators = "\\+ - \\* / = \\+= -= \\*= /= == << >> < >".split(" ");
        Pattern pattern;
        Matcher matcher;
        String tempString;
        for (int i = 0; i < separators.length; i++) {
            pattern = Pattern.compile("\\w" + separators[i]);
            matcher = pattern.matcher(source);
            while (matcher.find()) {
                tempString = matcher.group();
                source = source.replace(tempString, tempString.charAt(0) + " " + tempString.charAt(1));
            }
            pattern = Pattern.compile(separators[i] + "\\w");
            matcher = pattern.matcher(source);
            while (matcher.find()) {
                tempString = matcher.group();
                char first = tempString.charAt(0);
                if (first == '+' || first == '*') {
                    tempString = "\\" + tempString;
                }
                source = source.replace(tempString, tempString.charAt(0) + " " + tempString.charAt(1));
            }
        }
        return source;
    }

    public static String renameVariables(String source) {
        //variables names to lower case
        Matcher matcher;
        Pattern variablePattern = Pattern.compile("(int|double|long|short|boolean|char|float|byte|void)\\s\\w.");
        matcher = variablePattern.matcher(source);
        while (matcher.find()) {
            String s = matcher.group();
            source = source.replace(s, s.substring(0, s.length() - 2) + Character.toLowerCase(s.charAt(s.length() - 2)) + s.charAt(s.length() - 1));
        }
        //class names to UPPER case
        Pattern classPattern = Pattern.compile("(class)\\s\\w.");
        matcher = classPattern.matcher(source);
        while (matcher.find()) {
            String s = matcher.group();
            source = source.replace(s, s.substring(0, s.length() - 2) + Character.toUpperCase(s.charAt(s.length() - 2)) + s.charAt(s.length() - 1));
        }
        return source;
    }

    public static String surroundOperatorBlocks(String source) {
        Pattern pattern = Pattern.compile("(for|if|else)\\s*\\(;?;?;?[^\\{;]+\\)[^\\{;]+;");
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            String match = matcher.group();
            int closeBracketIndex = match.indexOf(")");
            source = source.replace(match, match.substring(0, closeBracketIndex + 1) + "{" + match.substring(closeBracketIndex + 1, match.length()) + "}");
        }
        return source;
    }

    public static String trimMultidefinition(String source) {
        Pattern variablesMultidefinitionPattern = Pattern.compile("(;|\\{)[^;\\{]+,[^;\\{]+;");
        Matcher matcher = variablesMultidefinitionPattern.matcher(source);
        while (matcher.find()) {
            List<String> variablesNames = new ArrayList<>();
            String s = matcher.group();
            if (!StringParser.isInQuotes(s, s.indexOf(","))) {
                int separatorIndex = StringParser.lastIndexOfCharacter(s, s.indexOf(","), ' ');
                String specificator = s.substring(1, separatorIndex + 1);
                String allVariables = s.substring(separatorIndex + 1, s.length());
                int beginIndex = 0;
                int lastIndex = allVariables.indexOf(",");
                while (lastIndex != -1) {
                    variablesNames.add(allVariables.substring(beginIndex, lastIndex));
                    beginIndex = lastIndex + 1;
                    lastIndex = allVariables.indexOf(",", beginIndex);
                }
                variablesNames.add(allVariables.substring(allVariables.lastIndexOf(",") + 1, allVariables.length() - 1));
                String result = "";
                for (String variable : variablesNames) {
                    result += specificator + variable + ";";
                }
                source = source.replace(s.substring(1, s.length()), result);
            }
        }
        return source;
    }
}
