package entities;

import java.util.ArrayList;

public class States {
    private ArrayList<String> states;

    public States(String[] statesStr) {
        for (String stateStr : statesStr) {
            states.add(stateStr);
        }
    }

    public ArrayList<String> getStates() {
        return states;
    }

    public void setStates(ArrayList<String> states) {
        this.states = states;
    }
}
