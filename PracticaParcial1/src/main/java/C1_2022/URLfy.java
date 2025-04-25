package C1_2022;

public class URLfy {

    public static void main(String[] args) {

        URLfy urlfy = new URLfy();
        
        char [] ejemplo = new char[] { 'e', 's', ' ', 'u', 'n', ' ', 'e', 'j', 'e', 'm', 'p', 'l', 'o', '\0', '\0', '\0', '\0'};
        urlfy.reemplazarEspacios(ejemplo);
        System.out.println(ejemplo);

    
       ejemplo= new char [] {'a', ' ', 'b', ' ', 'c', ' ', 'd', ' ', 'e', ' ', 'f', ' ', 'g', ' ', 'h', 'o', 'l', 'a', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0'};
       urlfy.reemplazarEspacios(ejemplo);
       System.out.println(ejemplo);

    
       ejemplo= new char [] {' ', ' ', 'e', 's', 'p', 'a', 'c', 'i', 'o', 's', ' ', ' ', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0'};
       urlfy.reemplazarEspacios(ejemplo);
       System.out.println(ejemplo);

    }

    public void reemplazarEspacios(char [] str) {  //Recorro de atras para adelante
        int i = str.length - 1;
        int totalCount = 0;
        int currentCount = 0;

        while(i >= 0){
            if(str[i] == '\0'){  // cantidad de logares que voy a tener que mover
                totalCount++;
            } else {
                int pos = i + totalCount - currentCount*2;
                if(str[i] == ' '){
                    str[pos] = '0';
                    str[pos - 1] = '2';
                    str[pos - 2] = '%';
                    currentCount++;
                } else{
                    str[pos] = str[i];
                }
            }
            i--;
        }
    }
}