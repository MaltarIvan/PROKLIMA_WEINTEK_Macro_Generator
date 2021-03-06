package entities;

public class Signal {
    private String signalName;
    private States states;
    private int stringId;
    private int enabledReg;
    private int enabledAddr;
    private String enabledCondition;
    private int enabledTPConfigDW;
    private int bitPosition;
    private int signalReg;
    private int signalAddr;
    private boolean TPConfigDW;
    private boolean disabled;
    private double scale;
    private int dataType;
    private double lowLimit;
    private double highLimit;

    public Signal() {
    }

    public String getSignalName() {
        return signalName;
    }

    public void setSignalName(String signalName) {
        this.signalName = signalName;
    }

    public States getStates() {
        return states;
    }

    public void setStates(States states) {
        this.states = states;
    }

    public int getStringId() {
        return stringId;
    }

    public void setStringId(int stringId) {
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

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public double getLowLimit() {
        return lowLimit;
    }

    public void setLowLimit(double lowLimit) {
        this.lowLimit = lowLimit;
    }

    public double getHighLimit() {
        return highLimit;
    }

    public void setHighLimit(double highLimit) {
        this.highLimit = highLimit;
    }

    @Override
    public String toString() {
        return "Signal{" +
                "signalName='" + signalName + '\'' +
                ", states=" + states +
                ", stringId=" + stringId +
                ", enabledReg=" + enabledReg +
                ", enabledAddr=" + enabledAddr +
                ", enabledCondition='" + enabledCondition + '\'' +
                ", enabledTPConfigDW=" + enabledTPConfigDW +
                ", bitPosition=" + bitPosition +
                ", signalReg=" + signalReg +
                ", signalAddr=" + signalAddr +
                ", TPConfigDW=" + TPConfigDW +
                ", disabled=" + disabled +
                ", scale=" + scale +
                ", dataType=" + dataType +
                '}';
    }
}
