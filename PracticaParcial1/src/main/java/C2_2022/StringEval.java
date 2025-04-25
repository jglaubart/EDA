package C2_2022;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class StringEval {

    public String evaluate(String expression){
        String[] tokens = expression.split(" ");
        Stack<String> stack = new Stack<>();
        for(String token:tokens){
            if(isOperand(token)){
                stack.push(token);
            }else{
                if(!isOperator(token)){
                    throw new RuntimeException("Invalid operator " + token);
                }

                String operand2;
                if(stack.empty()) {
                    throw new RuntimeException("Operand missing");
                } else operand2 = stack.pop();

                String operand1;
                if(stack.empty()) {
                    throw new RuntimeException("Operand missing");
                } else operand1 = stack.pop();

                stack.push(calculate(token, operand1, operand2));
            }
        }

        String ans = stack.pop();
        if(!stack.empty()) {
            throw new RuntimeException("Operator missing");
        }
        return ans;
    }

    private boolean isOperand(String s) {
        return s.matches("[a-zA-Z]+");
    }

    private boolean isOperator(String s) {
        return s.matches("\\+|-|\\*|/|\\^");
    }

    private String calculate(String op, String val1, String val2) {
        switch (op) {
            case "+":
                return val1 + val2;

                case "-":
                int idx = val1.indexOf(val2);
                if(idx == -1) return val1;
                return val1.substring(0, idx) + val1.substring(idx + val2.length());

                case "*":
                StringBuilder ans = new StringBuilder();
                for(int i=0, j=0; i < val1.length() && j < val2.length(); i++, j++){
                    ans.append(val1.charAt(i));
                    ans.append(val2.charAt(j));
                }
                if(val1.length() > val2.length()){
                    ans.append(val1.substring(val2.length()));
                }else if(val1.length() < val2.length()){
                    ans.append(val2.substring(val1.length()));
                }
                return ans.toString();

                case "/":
                    Set<Character> toRemove = new HashSet<>();
                    for (char c : val2.toCharArray()) {
                        toRemove.add(c);
                    }

                    StringBuilder ans2 = new StringBuilder();
                    for (char c : val1.toCharArray()) {
                        if (!toRemove.contains(c)) {
                            ans2.append(c);
                        }
                    }
                    return ans2.toString();

                case "^":
                    int times = val2.length();
                    StringBuilder ans3 = new StringBuilder();
                    for(int i = 0; i < times; i++){
                        ans3.append(val1);
                        ans3.append(val2, 0, i+1);
                    }
                    return ans3.toString();

                default:
                    throw new RuntimeException("Invalid operator " + op);
        }
    }

    public static void main(String[] args) {
        StringEval evaluator = new StringEval();
        System.out.println(evaluator.evaluate("AAAAA BBBBB +"));
        System.out.println(evaluator.evaluate("AAAAABBCCBB BB -"));
        System.out.println(evaluator.evaluate("AAA BBBBB *"));
        System.out.println(evaluator.evaluate("AAAAABBBCCCDDDAAA AB /"));
        System.out.println(evaluator.evaluate("EE ABCD ^"));
        System.out.println(evaluator.evaluate("AA BB CC DEF ^ * AE / + BC -"));
        System.out.println(evaluator.evaluate("HOLA QUE + TAL COMO ^ ESTAS / BIEN * + BIEN -"));

    }
}
