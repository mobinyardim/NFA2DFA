import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        ArrayList<String> alphabet = new ArrayList<>();
        alphabet.add("a");
        alphabet.add("b");

      /*  DFA dfa = new DFA(alphabet);

        ArrayList<State> states = new ArrayList<>();
        states.add(new State(alphabet, false, true));
        states.add(new State(alphabet, true, true));

        dfa.addStates(states);
        dfa.addTransition(states.get(0),"a",states.get(1));
        dfa.addTransition(states.get(0),"b",states.get(0));
        dfa.addTransition(states.get(1),"a",states.get(1));
        dfa.addTransition(states.get(1),"b",states.get(0));*/

        NFA nfa = new NFA(alphabet);

        ArrayList<State> states = new ArrayList<>();
        states.add(new State(alphabet, false, true));
        states.add(new State(alphabet, false, true));
        states.add(new State(alphabet, false, true));
        states.add(new State(alphabet, false, true));
        states.add(new State(alphabet, true, true));
        states.add(new State(alphabet, false, true));
        nfa.addStates(states);

        nfa.addTransition(states.get(0),"a",states.get(1));

        nfa.addTransition(states.get(1),"a",states.get(2));
        nfa.addTransition(states.get(1),"",states.get(4));

        nfa.addTransition(states.get(2),"a",states.get(5));
        nfa.addTransition(states.get(2),"b",states.get(5));

        nfa.addTransition(states.get(3),"a",states.get(1));

        nfa.addTransition(states.get(4),"a",states.get(2));
        nfa.addTransition(states.get(4),"b",states.get(3));
        nfa.addTransition(states.get(4),"",states.get(5));


        //System.out.print(nfa.isValidExpression("11"));

        System.out.println(nfa.isValidExpression("a"));
        System.out.println(nfa.isValidExpression("aba"));
        System.out.println(nfa.isValidExpression("aaa"));
        System.out.println(nfa.isValidExpression("abaaaabb"));
        System.out.println("        ******         ");
        DFA dfa = nfa.NFA2DFA();

        System.out.println(dfa.isValidExpression("a"));
        System.out.println(dfa.isValidExpression("aba"));
        System.out.println(dfa.isValidExpression("aaa"));
        System.out.println(dfa.isValidExpression("abaaaabb"));

    }
}
