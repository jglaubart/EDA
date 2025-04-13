import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class EvaluatorWithVariables {
    // opcion 1
    private static Map<String, Integer> mapping = new HashMap<String, Integer>() {
        { put("+", 0); put("-", 1); put("*", 2); put("/", 3); put("^", 4); put("(", 5); put(")", 6); }
    };

    private static boolean[][] precedenceMatriz= {
            { true,  true,  false, false, false,  false,  true  },
            { true,  true,  false, false, false,  false,  true  },
            { true,  true,  true,  true,  false,  false,  true  },
            { true,  true,  true,  true,  false,  false,  true  },
            { true,  true,  true,  true,  false,  false,  true  },
            { false, false, false, false, false,  false,  false },
    };
    private Scanner scannerLine;
    private Map<String, Double> vbles = new HashMap<String, Double>();


    private boolean getPrecedence(String tope, String current) {
        Integer topeIndex;
        Integer currentIndex;

        if ((topeIndex= mapping.get(tope))== null)
            throw new RuntimeException(String.format("tope operator %s not found", tope));

        if ((currentIndex= mapping.get(current)) == null)
            throw new RuntimeException(String.format("current operator %s not found", current));

        return precedenceMatriz[topeIndex][currentIndex];
    }

    public EvaluatorWithVariables(Map<String, Double> vbles) {
        Scanner input = new Scanner(System.in).useDelimiter("\\n");
        System.out.print("Introduzca la expresión en notación infija: ");
        String line= input.nextLine();
        input.close();
        scannerLine = new Scanner(line).useDelimiter("\\s+");
        this.vbles = vbles;
    }

    public Double evaluate() {
        Stack<Double> auxi= new Stack<Double>();
        String exp = infijaToPostfija();
        scannerLine = new Scanner(exp).useDelimiter("\\s+");

        while(scannerLine.hasNext()){
            String s = scannerLine.next();
            if (isOperand(s) == 1){
                auxi.push(Double.valueOf(s));
            } else if (isOperand(s) == 2) {
                auxi.push(vbles.get(s));
            } else{	// operador o error
                System.out.println(s);
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
                auxi.push(eval(s, operand1 , operand2));
            }
        }
        double rta= auxi.pop();
        if (auxi.empty())
            return rta;

        throw new RuntimeException("Operator missing");
    }

    private int isOperand(String s) {
        try
        {
            Double.valueOf(s);
        }
        catch(NumberFormatException e){
            if(vbles.containsKey(s))
                return 2;
            return 0;
        }
        return 1;
    }

    private boolean isOperator(String s) {
        return s.matches("\\+|-|\\*|/|\\^");
    }

    private double eval(String op, double val1, double val2){
        switch (op){
            case "+": return val1 + val2;
            case "-": return val1 - val2;
            case "*": return val1 * val2;
            case "/": return val1 / val2;
            case "^": return Math.pow(val1, val2);
        };

        throw new RuntimeException("invalid operator" +  op);
    }

    private String infijaToPostfija(){
        String postfija= "";
        Stack<String> theStack= new Stack<String>();

        while(scannerLine.hasNext()) {
            String current = scannerLine.next();

            if (isOperand(current) > 0) {
                postfija += String.format("%s ", current);
            } else {
                while (!theStack.empty() && getPrecedence(theStack.peek(), current))
                    postfija += String.format("%s ", theStack.pop());

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
        Map<String, Double> vbles = new HashMap<String, Double>(){{ put("x", 0.2); put("y", -2.0); put("z", 2.0);}};
        EvaluatorWithVariables e = new EvaluatorWithVariables(vbles);
        System.out.println(e.evaluate());
    }
}
