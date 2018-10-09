package entities.sets;

/**
 * Created by Maltar on 9.10.2018..
 */
public class RoomCtrls {
    private int enableReg;
    private int enableAddr;
    private int stringIdStart;
    private int addrStart;

    public RoomCtrls() {
    }

    public RoomCtrls(int enableReg, int enableAddr, int stringIdStart, int addrStart) {
        this.enableReg = enableReg;
        this.enableAddr = enableAddr;
        this.stringIdStart = stringIdStart;
        this.addrStart = addrStart;
    }

    public int getEnableReg() {
        return enableReg;
    }

    public void setEnableReg(int enableReg) {
        this.enableReg = enableReg;
    }

    public int getEnableAddr() {
        return enableAddr;
    }

    public void setEnableAddr(int enableAddr) {
        this.enableAddr = enableAddr;
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
}
