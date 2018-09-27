package utilities.groups;

public class RoomPressures {
    private int enabledReg;
    private int enabledAddr;
    private int stringIdStart;
    private int addrStart;
    private int reg;
    private int dataType;
    private double scale;

    public RoomPressures() {
    }

    public RoomPressures(int enabledReg, int enabledAddr, int stringIdStart, int addrStart, int reg, int dataType, double scale) {
        this.enabledReg = enabledReg;
        this.enabledAddr = enabledAddr;
        this.stringIdStart = stringIdStart;
        this.addrStart = addrStart;
        this.reg = reg;
        this.dataType = dataType;
        this.scale = scale;
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

    public int getStringIdStart() {
        return stringIdStart;
    }

    public void setStringIdStart(int stringIdStart) {
        this.stringIdStart = stringIdStart;
    }

    public int getAddrStart() {
        return addrStart;
    }

    public void setAddrStart(int addrStart) {
        this.addrStart = addrStart;
    }

    public int getReg() {
        return reg;
    }

    public void setReg(int reg) {
        this.reg = reg;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }
}
