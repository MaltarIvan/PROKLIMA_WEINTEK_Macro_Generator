package entities;

import java.util.ArrayList;

public class Signal {
    private String signalName;
    private ArrayList<States> states;
    public int stateStringIndex;
    private String stringId;
    private int enabledReg;
    private int enabledAddr;
    private String enabledCondition;
    private int enabledTPConfigDW;
    private int bitPosition;
    private int signalReg;
    private int signalAddr;
    private boolean TPConfigDW;

    public Signal() {
    }

    public String getSignalName() {
        return signalName;
    }

    public void setSignalName(String signalName) {
        this.signalName = signalName;
    }

    public ArrayList<States> getStates() {
        return states;
    }

    public void setStates(ArrayList<States> states) {
        this.states = states;
    }

    public String getStringId() {
        return stringId;
    }

    public int getStateStringIndex() {
        return stateStringIndex;
    }

    public void setStateStringIndex(int stateStringIndex) {
        this.stateStringIndex = stateStringIndex;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }

    public int getEnabledReg() {
        return enabledReg;
    }

    public void setEnabledReg(int enabledReg) {
        this.enabledReg = enabledReg;
    }

    public int getEnabledAddr() {
        return enabledAddr;
    }

    public void setEnabledAddr(int enabledAddr) {
        this.enabledAddr = enabledAddr;
    }

    public String getEnabledCondition() {
        return enabledCondition;
    }

    public void setEnabledCondition(String enabledCondition) {
        this.enabledCondition = enabledCondition;
    }

    public int getEnabledTPConfigDW() {
        return enabledTPConfigDW;
    }

    public void setEnabledTPConfigDW(int enabledTPConfigDW) {
        this.enabledTPConfigDW = enabledTPConfigDW;
    }

    public int getBitPosition() {
        return bitPosition;
    }

    public void setBitPosition(int bitPosition) {
        this.bitPosition = bitPosition;
    }

    public int getSignalReg() {
        return signalReg;
    }

    public void setSignalReg(int signalReg) {
        this.signalReg = signalReg;
    }

    public int getSignalAddr() {
        return signalAddr;
    }

    public void setSignalAddr(int signalAddr) {
        this.signalAddr = signalAddr;
    }

    public boolean isTPConfigDW() {
        return TPConfigDW;
    }

    public void setTPConfigDW(boolean TPConfigDW) {
        this.TPConfigDW = TPConfigDW;
    }
}
