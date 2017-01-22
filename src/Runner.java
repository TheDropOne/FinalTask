import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Semen on 21-Jan-17.
 */
public class Runner {
    public static void main(String[] args) {
        try {
            File inputFile1 = new File("input1.txt");
            File inputFile2 = new File("input2.txt");
            File sourceJavaFile, resultJavaFile;
            File outputFile1 = new File("output1.txt");
            File outputFile2 = new File("output2.txt");

            // После 4 часов дебага, гугления и прошерстки StackOverflow
            // получилось узнать в чем дело - входной файл input1.txt на моем компьютере был создан в кодировке UTF-8
            // первый байт в файле такой кодировки - Byte Order Mark, не был распознан самой кодировкой, т.е
            // чтение из файла производилось корректно, но в получаемой строке был символ Юникода, который не отображатся
            // в строке, и при дебаге и выводе на консоль его не заметно. Подробнее про сам баг -
            // http://bugs.java.com/view_bug.do?bug_id=4508058
            // Проблема решена созданием файла в ANSI.

            sourceJavaFile = new File(InputOutput.readStringFromFile(inputFile1));
            resultJavaFile = new File(InputOutput.readStringFromFile(inputFile2));

            List<String> sourceFileLines = InputOutput.readListOfStrings(sourceJavaFile);
            String sourceFileString = StringParser.listToString(sourceFileLines);
            List<String> comments = new ArrayList<>();

            comments.addAll(StringParser.getOneLineComments(sourceFileLines));
            comments.addAll(StringParser.getManyLinesComments(sourceFileString));

            //Task 1
            Collections.sort(comments, (o1, o2) -> o1.length() - o2.length());
            InputOutput.writeIterable(outputFile1, comments.iterator(), 2);

            //Task 2
            Map<String, Integer> commentCountMap = Hashtables.getMapFromList(comments);
            InputOutput.writeMap(outputFile2, commentCountMap);

            //Task 3
            sourceFileString = StringFormatter.trimString(sourceFileString);
            sourceFileString = StringFormatter.trimMultidefinition(sourceFileString);
            sourceFileString = StringFormatter.surroundOperatorBlocks(sourceFileString);
            sourceFileString = StringFormatter.addLineSeparators(sourceFileString);
            sourceFileString = StringFormatter.trimKeywords(sourceFileString);
            sourceFileString = StringFormatter.addTabulation(sourceFileString);
            sourceFileString = StringFormatter.surroundOperators(sourceFileString);
            sourceFileString = StringFormatter.renameVariables(sourceFileString);

            InputOutput.writeString(resultJavaFile, sourceFileString);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}







