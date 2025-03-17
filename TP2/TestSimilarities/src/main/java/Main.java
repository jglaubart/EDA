public class Main {
    public static void main(String[] args) {
        Soundex soundex = new Soundex();
        System.out.println("Similarity between threshold and hold is: " + soundex.calculateSimilarity("threshold", "hold"));
        System.out.println("Similarity between phone and foun is: " + soundex.calculateSimilarity("phone", "foun"));
    }

}
