import java.util.*;

public class State {

    private static int ID_GENATOR = 0;
    private String id;
    private Map<String, ArrayList<State>> transmission;
    private boolean isNFAState;
    private boolean isFinal;
    private ArrayList<String> alphabet;

    State(ArrayList<String> alphabet, boolean isFinal, boolean isNFAState) {
        this.alphabet = alphabet;
        transmission = new HashMap<>();
        for (String alpha : alphabet) {
            transmission.put(alpha, new ArrayList<>());
        }
        this.isNFAState = isNFAState;

        id = String.valueOf(ID_GENATOR);
        ID_GENATOR++;
        this.isFinal = isFinal;
        if (isNFAState)
            transmission.put("", new ArrayList<>());
    }

    State(ArrayList<String> alphabet, ArrayList<State> states) {
        this.alphabet = alphabet;
        this.isNFAState = false;
        transmission = new HashMap<>();
        for (String alpha : alphabet) {
            transmission.put(alpha, new ArrayList<>());
        }

        //generate id from states id
        ArrayList<String> list = new ArrayList<>();
        for (State s : states)
            list.add(s.id);
        Collections.sort(list);
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
            sb.append(" ");
        }
        this.id = sb.toString().trim();

        // add all transmission in states to new state
        for (String alpha : alphabet) {

            ArrayList<State> forwards = new ArrayList<>();

            for (State state : states) {
                forwards.addAll(state.forward(alpha));
            }

            //delete duplicate objects
            Set<State> mySet = new LinkedHashSet<>(forwards);

            transmission.get(alpha).addAll(mySet);
        }

        transmission.put("",new ArrayList<>());

        isFinal = false;
        for (State state : states)
            if (state.isFinal()) {
                isFinal = true;
                break;
            }
    }

    void addTransmission(String with, State to) {
        if (!isNFAState) {
            if (with.isEmpty()) {
                System.out.print("DFA not accept lambda transmission");
                return;
            } else {
                ArrayList<State> states = transmission.get(with);
                states.clear();
                states.add(to);
            }
        } else {
            ArrayList<State> states = transmission.get(with);
            states.add(to);
        }
    }

    void setTransmission(String with, State to) {
        transmission.get(with).clear();
        transmission.get(with).add(to);
    }

    ArrayList<State> forward(String with) {
        if (isNFAState) {
            ArrayList<State> forwards = new ArrayList<>();
            forwardWithLambda(forwards, this, with);
            Set<State> mySet = new LinkedHashSet<>(forwards);
            forwards.clear();
            forwards.addAll(mySet);
            return forwards;
        } else {
            return transmission.get(with);
        }
    }

    String getId() {
        return id;
    }

    boolean isFinal() {
        return isFinal;
    }

    private void forwardWithLambda(ArrayList<State> forwards, State now, String str) {

        if (str.isEmpty()) {

            forwards.add(now);
            for (State forward : now.transmission.get(""))
                forwardWithLambda(forwards, forward, "");

        } else {
            forwards.addAll(now.transmission.get(str));

            for (State forward : now.transmission.get(str))
                forwardWithLambda(forwards, forward, "");

            for (State forward : now.transmission.get(""))
                forwardWithLambda(forwards, forward, str);
        }
    }


    @Override
    public String toString() {
        return id;
    }

    void convertTo(boolean isNFAState) {

        if (this.isNFAState && !isNFAState) {
            transmission.remove("");
        } else if (!this.isNFAState && isNFAState) {
            transmission.put("", new ArrayList<>());
        }

        this.isNFAState = isNFAState;
    }

    State copy(){
        State state = new State(alphabet, isFinal, isNFAState);
        state.id = this.id;
        state.transmission = this.transmission;
        return state;
    }

    boolean isNFAState(){
        return isNFAState;
    }
}
