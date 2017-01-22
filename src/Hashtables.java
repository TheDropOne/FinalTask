import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Semen on 22-Jan-17.
 */
public class Hashtables {
    public static Map<String, Integer> getMapFromList(List<String> sourceList) {
        Map<String, Integer> commentsCountMap = new HashMap<>();
        for (int i = 0; i < sourceList.size(); i++) {
            String comment = sourceList.get(i);
            if (commentsCountMap.containsKey(comment)) {
                Integer count = commentsCountMap.get(comment);
                commentsCountMap.replace(comment, ++count);
            } else {
                commentsCountMap.put(comment, 1);
            }
        }
        return commentsCountMap;
    }
}
