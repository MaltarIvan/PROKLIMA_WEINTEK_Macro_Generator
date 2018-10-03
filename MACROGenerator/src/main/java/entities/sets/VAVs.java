package entities.sets;

/**
 * Created by Maltar on 3.10.2018..
 */
public class VAVs {
    private int numberReg;
    private int numberAddr;
    private int stringIdStart;
    private int reg;
    private int addrStart;
    private int dataType;
    private double scale;
    private int splyEnReg;
    private int splyEnAddr;

    public VAVs() {
    }

    public VAVs(int numberReg, int numberAddr, int stringIdStart, int reg, int addrStart, int dataType, double scale, int splyEnReg, int splyEnAddr) {
        this.numberReg = numberReg;
        this.numberAddr = numberAddr;
        this.stringIdStart = stringIdStart;
        this.reg = reg;
        this.addrStart = addrStart;
        this.dataType = dataType;
        this.scale = scale;
        this.splyEnReg = splyEnReg;
        this.splyEnAddr = splyEnAddr;
    }

    public int getNumberReg() {
        return numberReg;
    }

    public void setNumberReg(int numberReg) {
        this.numberReg = numberReg;
    }

    public int getNumberAddr() {
        return numberAddr;
    }

    public void setNumberAddr(int numberAddr) {
        this.numberAddr = numberAddr;
    }

    public int getStringIdStart() {
        return stringIdStart;
    }

    public void setStringIdStart(int stringIdStart) {
        this.stringIdStart = stringIdStart;
    }

    public int getReg() {
        return reg;
    }

    public void setReg(int reg) {
        this.reg = reg;
    }

    public int getAddrStart() {
        return addrStart;
    }

    public void setAddrStart(int addrStart) {
        this.addrStart = addrStart;
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

    public int getSplyEnReg() {
        return splyEnReg;
    }

    public void setSplyEnReg(int splyEnReg) {
        this.splyEnReg = splyEnReg;
    }

    public int getSplyEnAddr() {
        return splyEnAddr;
    }

    public void setSplyEnAddr(int splyEnAddr) {
        this.splyEnAddr = splyEnAddr;
    }
}
