

public class Main {
    public static void main(String[] args) {
        QGram qGram = new QGram(2);
        qGram.printTokens("alale");
        qGram.printTokens("salesal");
        System.out.println(qGram.similarity("salesal", "alale") == (double) 6 / 14);
    }

}
