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

            sourceJavaFile = new File(InputOutput.readStringFromFile(inputFile1));
            resultJavaFile = new File(InputOutput.readStringFromFile(inputFile2));

            //System.setProperty()
            System.out.println(sourceJavaFile.getAbsolutePath());

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



        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
