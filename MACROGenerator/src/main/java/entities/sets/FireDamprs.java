package entities.sets;

import entities.States;

public class FireDamprs {
    private int numberEnReg;
    private int numberEnAddr;
    private int stringIdStart;
    private States states;
    private int addrStart;
    private int reg;
    private int dataType;
    private FireDamprsClosed fireDampersClosed;

    public FireDamprs() {}

    public FireDamprs(int numberEnReg, int numberEnAddr, int stringIdStart, States states, int addrStart, int reg, int dataType, int closedEnabledReg, int closedEnabledAddr, States closedStates) {
        this.numberEnReg = numberEnReg;
        this.numberEnAddr = numberEnAddr;
        this.stringIdStart = stringIdStart;
        this.states = states;
        this.addrStart = addrStart;
        this.reg = reg;
        this.dataType = dataType;
        this.fireDampersClosed = new FireDamprsClosed(closedEnabledReg, closedEnabledAddr, closedStates);
    }

    public int getNumberEnReg() {
        return numberEnReg;
    }

    public void setNumberEnReg(int numberEnReg) {
        this.numberEnReg = numberEnReg;
    }

    public int getNumberEnAddr() {
        return numberEnAddr;
    }

    public void setNumberEnAddr(int numberEnAddr) {
        this.numberEnAddr = numberEnAddr;
    }

    public int getStringIdStart() {
        return stringIdStart;
    }

    public void setStringIdStart(int stringIdStart) {
        this.stringIdStart = stringIdStart;
    }

    public States getStates() {
        return states;
    }

    public void setStates(States states) {
        this.states = states;
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

    public FireDamprsClosed getFireDampersClosed() {
        return fireDampersClosed;
    }

    public void setFireDampersClosed(int enabledReg, int enabledAddr, States states) {
        this.fireDampersClosed = new FireDamprsClosed(enabledReg, enabledAddr, states);
    }

    public class FireDamprsClosed {
        private int enabledReg;
        private int enabledAddr;
        private States states;

        public FireDamprsClosed() {
        }

        public FireDamprsClosed(int enabledReg, int enabledAddr, States states) {
            this.enabledReg = enabledReg;
            this.enabledAddr = enabledAddr;
            this.states = states;
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

        public States getStates() {
            return states;
        }

        public void setStates(States states) {
            this.states = states;
        }
    }
}
