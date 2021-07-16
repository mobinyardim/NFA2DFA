import com.sun.istack.internal.NotNull;

import java.util.ArrayList;

 class DFA {

    private ArrayList<String> alphabet;
    private ArrayList<State> states;
    private State start;

    DFA(@NotNull ArrayList<String> alphabet) {
        this.alphabet = alphabet;
        states = new ArrayList<>();
    }

    void addState(State state) {
        if (states.size() == 0) {
            start = state;
        }
        states.add(state);
    }

    void addStates(ArrayList<State> states){
        if (this.states.size() == 0 && states.size() > 0) {
            start = states.get(0);
        }
        this.states.addAll(states);
    }

    void addTransition(State from, String with, State to) {
        if (states.contains(from)) {
            from.addTransmission(with, to);
        } else {
            System.out.print("this state is not available in this DFA");
        }
    }

    Boolean isValidExpression(String str) {

        StringBuilder sb = new StringBuilder(str);
        if (str.trim().isEmpty()) {
            System.out.print("this is empty Expression");
            return false;
        }
        State now = start;
        while (sb.length() > 0) {
            if(alphabet.contains(sb.substring(0,1))) {
                ArrayList<State> forward = now.forward(sb.substring(0, 1));
                if (forward.size() == 0) {
                    System.out.print("Trap");
                    return false;
                }
                sb.deleteCharAt(0);
                now = forward.get(0);
            }else {
                System.out.print(sb.substring(0,1)+" there is not in alphabet");
            }

        }
        return now.isFinal();
    }
}
