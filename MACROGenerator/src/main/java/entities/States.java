package entities;

import java.util.ArrayList;
import java.util.Objects;

public class States {
    private ArrayList<String[]> statesStr;
    private ArrayList<Signal> signals;
    private int stateStringIndex;

    public States(ArrayList<String[]> statesStr, int stateStringIndex) {
        this.statesStr = statesStr;
        this.stateStringIndex = stateStringIndex;
        signals = new ArrayList<>();
    }

    public ArrayList<String[]> getStatesStr() {
        return statesStr;
    }

    public void setStatesStr(ArrayList<String[]> statesStr) {
        this.statesStr = statesStr;
    }

    public int getStateStringIndex() {
        return stateStringIndex;
    }

    public void setStateStringIndex(int stateStringIndex) {
        this.stateStringIndex = stateStringIndex;
    }

    public ArrayList<Signal> getSignals() {
        return signals;
    }

    public void setSignals(ArrayList<Signal> signals) {
        this.signals = signals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        States states1 = (States) o;
        if (this.statesStr.size() != states1.getStatesStr().size()) return false;
        for (int i = 0; i < this.statesStr.size(); i++) {
            for (int j = 0; j < this.statesStr.get(i).length; j++) {
                if (!(this.statesStr.get(i)[j].equals(states1.getStatesStr().get(i)[j]))) return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatesStr(), getStateStringIndex());
    }
}
