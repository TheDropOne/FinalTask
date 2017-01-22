import java.io.*;
import java.util.*;

/**
 * Created by Semen on 21-Jan-17.
 */
public class InputOutput {
    public static void writeIterable(File way, Iterator<String> iterator, int lineSeparatorsCount) {
        try {
            FileWriter writer = new FileWriter(way);
            while (iterator.hasNext()) {
                writer.write(iterator.next());
                if (iterator.hasNext()) {
                    while (lineSeparatorsCount-- > 0) {
                        writer.write(System.lineSeparator());
                    }
                }
            }
            writer.close();
        } catch (IOException exception) {
            exception.getLocalizedMessage();
        }
    }


    public static void writeMap(File way, Map<String, Integer> map) {
        try {
            FileWriter writer = new FileWriter(way);
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                writer.write(key + " - " + map.get(key) + " times.");
                writer.write(System.lineSeparator());
            }
            writer.close();
        } catch (IOException exception) {
            exception.getLocalizedMessage();
        }
    }


    public static String readStringFromFile(File file) {
        BufferedReader br = null;
        String s = null;
        try {
            br = new BufferedReader(new FileReader(file));
            s = br.readLine();
        } catch (IOException ex) {
            System.out.println("Reading from file successfully failed. IOException");
            ex.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException | NullPointerException ex) {
                System.out.println("Stream didn't closed or not exists. Reading from file failed. ");
            }
        }
        return s;
    }

    public static List<String> readListOfStrings(File file) {
        BufferedReader br = null;
        List<String> listOfStrings = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(file));
            String tempString = br.readLine();
            while (tempString != null) {
                listOfStrings.add(tempString);
                tempString = br.readLine();
            }
        } catch (IOException ex) {
            System.out.println("Reading from file successfully failed. IOException");
            ex.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException | NullPointerException ex) {
                System.out.println("Stream didn't closed or not exists. Reading from file failed. ");
            }
        }
        return listOfStrings;
    }
}
