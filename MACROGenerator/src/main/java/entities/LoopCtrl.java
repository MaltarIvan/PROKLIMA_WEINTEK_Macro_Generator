package entities;

/**
 * Created by Maltar on 9.10.2018..
 */
public class LoopCtrl {
    private String name;
    private int stringId;
    private int enabledReg;
    private int enableAddr;
    private int enabledTPConfigDW;
    private String enableCondition;
    private int enableBitPosition;
    private int addr;
    private boolean cascade;
    private boolean TPConfigDW;

    public LoopCtrl() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getEnableAddr() {
        return enableAddr;
    }

    public void setEnableAddr(int enableAddr) {
        this.enableAddr = enableAddr;
    }

    public int getEnabledTPConfigDW() {
        return enabledTPConfigDW;
    }

    public void setEnabledTPConfigDW(int enabledTPConfigDW) {
        this.enabledTPConfigDW = enabledTPConfigDW;
    }

    public String getEnableCondition() {
        return enableCondition;
    }

    public void setEnableCondition(String enableCondition) {
        this.enableCondition = enableCondition;
    }

    public int getEnableBitPosition() {
        return enableBitPosition;
    }

    public void setEnableBitPosition(int enableBitPosition) {
        this.enableBitPosition = enableBitPosition;
    }

    public int getAddr() {
        return addr;
    }

    public void setAddr(int addr) {
        this.addr = addr;
    }

    public boolean isCascade() {
        return cascade;
    }

    public void setCascade(boolean cascade) {
        this.cascade = cascade;
    }

    public boolean isTPConfigDW() {
        return TPConfigDW;
    }

    public void setTPConfigDW(boolean TPConfigDW) {
        this.TPConfigDW = TPConfigDW;
    }
}
