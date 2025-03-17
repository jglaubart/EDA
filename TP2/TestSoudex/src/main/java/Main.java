public class Main {
    public static void main(String[] args) {
        String[] tests = new String[]{"threshold", "hold", "phone", "foun"};
        Soundex soundex = new Soundex();
        for (String test : tests) {
            String code = soundex.encode(test);
            System.out.println("Soudex encode for " + test + "is: " + code);
        }
    }
}
