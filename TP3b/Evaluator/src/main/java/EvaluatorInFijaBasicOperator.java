import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class EvaluatorInFijaBasicOperator {

    // opcion 1

    private static Map<String, Integer> mapping = new HashMap<String, Integer>()
    {   { put("+", 0); put("-", 1); put("*", 2); put("/", 3); put("^", 4); put("(", 5); put(")", 6); }
    };

    private static boolean[][] precedenceMatriz=
            {     //{  +       -      *      /     ^       (      ) }
                    { true,  true,  false, false, false, false, true},
                    { true,  true,  false, false, false, false, true},
                    { true,  true,  true,  true, false, false, true },
                    { true,  true,  true,  true, false, false, true },
                    { true,  true,  true,  true, false, false, true },
                    { false, false, false, false, false, false, true},
            };

    private boolean getPrecedence(String tope, String current)
    {
        Integer topeIndex;
        Integer currentIndex;

        if ((topeIndex= mapping.get(tope))== null)
            throw new RuntimeException(String.format("tope operator %s not found", tope));

        if ((currentIndex= mapping.get(current)) == null)
            throw new RuntimeException(String.format("current operator %s not found", current));

        return precedenceMatriz[topeIndex][currentIndex];
    }


    // opcion 2: asumo que _ no es parte de ningun operador posible
//
//    private static Map<String, Boolean> precendeceMap= new HashMap<String, Boolean>()
//    {	{
//        put("+_+", true); put("+_-", true); put("+_*", false); put("+_/", false);
//        put("-_+", true); put("-_-", true); put("-_*", false); put("-_/", false);
//        put("*_+", true); put("*_-", true); put("*_*", true); put("*_/", true);
//        put("/_+", true); put("/_-", true); put("/_*", true); put("/_/", true);
//        put("^_+", true); put("^_-", true); put("^_*", true); put("^_/", true);
//    }  };
//
//    private final static String extraSymbol= "_";
//
//    private boolean getPrecedence2(String tope, String current)
//    {
//        Boolean rta= precendeceMap.get(String.format("%s%s%s", tope, extraSymbol, current));
//        if (rta == null)
//            throw new RuntimeException(String.format("operator %s or %s not found", tope, current));
//
//        return rta;
//    }

    private Scanner scannerLine;

    public EvaluatorInFijaBasicOperator()  {
        Scanner input = new Scanner(System.in).useDelimiter("\\n");
        System.out.print("Introduzca la expresion en notacion infija: ");
        String line = input.nextLine();
        input.close();

        scannerLine = new Scanner(line).useDelimiter("\\s+");
    }

    public Double evaluate()
    {
        Stack<Double> auxi = new Stack<Double>();
        String exp = infijaToPostfija();
        scannerLine = new Scanner(exp).useDelimiter("\\s+");

        while( scannerLine.hasNext() )
        {
            String s = scannerLine.next();

            if ( isOperand(s) )
            {
                auxi.push(Double.valueOf(s));
            }
            else{	// operador o error
                if (!isOperator(s))
                    throw new RuntimeException("Invalid operator " + s);

                Double operand2;
                if (auxi.empty())
                    throw new RuntimeException("Operand missing");
                else
                    operand2= auxi.pop();

                Double operand1;
                if (auxi.empty())
                    throw new RuntimeException("Operand missing");
                else
                    operand1= auxi.pop();

                auxi.push(eval(s, operand1 , operand2) );
            }
        }
        double rta= auxi.pop();
        if (auxi.empty())
            return rta;

        throw new RuntimeException("Operator missing");
    }

    private boolean isOperand(String s)
    {
        try
        {
            Double.valueOf(s);
        }
        catch(NumberFormatException e)
        {
            return false;
        }
        return true;
    }

    private boolean isOperator(String s)
    {
        return s.matches("\\+|-|\\*|/|\\^");
    }

    private double eval(String op, double val1, double val2)
    {
        switch (op)
        {
            case "+": return val1 + val2;
            case "-": return val1 - val2;
            case "*": return val1 * val2;
            case "/": return val1 / val2;
            case "^": return Math.pow(val1, val2);
        };

        throw new RuntimeException("invalid operator" +  op);
    }

    private String infijaToPostfija() {
        String postfija = "";
        Stack<String> theStack= new Stack<>();

        while( scannerLine.hasNext() ){
            String current= scannerLine.next();

            if (isOperand(current)){
                postfija += String.format("%s ", current);
            } else {
                while(! theStack.empty() && getPrecedence(theStack.peek(), current)){
                    postfija += String.format("%s ", theStack.pop());
                }
                if(current.equals(")"))
                    if(!theStack.empty() && theStack.peek().equals("("))
                        theStack.pop();
                    else
                        throw new RuntimeException("( operator missing");
                else
                    theStack.push(current);
            }
        }
        while (!theStack.empty())
            if(theStack.peek().equals("("))
                throw new RuntimeException(") operator missing");
            else
                postfija += String.format("%s ", theStack.pop());


        System.out.println("Postfija= " + postfija);
        return postfija;
    }




    public static void main(String[] args) {
        EvaluatorInFijaBasicOperator e = new EvaluatorInFijaBasicOperator();
        System.out.println(e.evaluate());
    }

}
