import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

class NFA {

    private ArrayList<String> alphabet;
    private ArrayList<State> states;
    private State start;

    NFA(@NotNull ArrayList<String> alphabet) {
        this.alphabet = alphabet;
        states = new ArrayList<>();
    }

    void addState(State state) {
        if (states.size() == 0) {
            start = state;
        }
        states.add(state);
    }

    void addStates(ArrayList<State> states) {
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

        return validExpression(start, str);
    }

    private Boolean validExpression(State now, String str) {

        if (str.isEmpty()) {
            if (now.isFinal())
                return true;
            else {
                for (State forward : now.forward(""))
                    if (forward.isFinal())
                        return true;
                return false;
            }
        } else {

            for (State forward : now.forward(str.substring(0, 1))) {
                StringBuilder sb = new StringBuilder(str);
                if (validExpression(forward, sb.deleteCharAt(0).toString()))
                    return true;
            }
            return false;
        }

    }

    DFA NFA2DFA() {
        DFA dfa = new DFA(alphabet);

        ArrayList<State> dfaStates = new ArrayList<>();

        ArrayList<State> first = start.forward("");

        if (containState(states, new State(alphabet, first)) != null)
            dfaStates.add(containState(states, new State(alphabet, first)));
        else
            dfaStates.add(new State(alphabet, first));

        for (int i = 0; i < dfaStates.size(); i++) {

            State now = dfaStates.get(i);

            for (String alpha : alphabet) {

                if (containState(dfaStates, new State(alphabet, now.forward(alpha))) != null) {

                    now.setTransmission(alpha, containState(dfaStates, new State(alphabet, now.forward(alpha))));

                } else if (containState(states, new State(alphabet, now.forward(alpha))) != null) {

                    State state = containState(states, new State(alphabet, now.forward(alpha))).copy();
                    dfaStates.add(state);
                    now.setTransmission(alpha, state);

                } else {
                    if (now.forward(alpha).size() > 0) {

                        State state = new State(alphabet, now.forward(alpha));

                        dfaStates.add(state);
                        now.setTransmission(alpha, state);
                    }

                }
            }
        }
        for (int i = 0; i < dfaStates.size(); i++) {
            if (dfaStates.get(i).isNFAState())
                dfaStates.get(i).convertTo(false);
        }
        dfa.addStates(dfaStates);
        return dfa;
    }

    private State containState(ArrayList<State> dfaStates, State state) {
        for (int i = 0; i < dfaStates.size(); i++) {
            if (dfaStates.get(i).getId().equals(state.getId()))
                return dfaStates.get(i);
        }

        return null;
    }


}
