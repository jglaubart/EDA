public class Main {
    public static void main(String[] args) {
        ExactSearch search = new ExactSearch();
        char[] target = "abracadabra".toCharArray();
        String[] querys = {"ra", "abra", "aba"};
        for(String query : querys) {
            System.out.println(search.indexOf(query.toCharArray(), target));
        }
    }
}