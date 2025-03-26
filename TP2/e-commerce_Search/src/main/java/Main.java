import org.apache.commons.codec.EncoderException;

public class Main {
    ECommerce eCommerce = new ECommerce();

    public static void main(String[] args) throws EncoderException {
        Main main = new Main();
        main.eCommerce.findProduct("porta      royo");
    }
}
