import org.apache.commons.lang3.StringUtils;

public class Main {
    public static void main(String[] args) {
        System.out.println(Levenshtein.distance("big data", "bigdaa"));
        System.out.println(Levenshtein.distance("big data", "bigdaa") == StringUtils.getLevenshteinDistance("big data", "bigdaa"));
        System.out.println(Levenshtein.normalizedSimilarity("big data", "bigdaa"));
    }
}
