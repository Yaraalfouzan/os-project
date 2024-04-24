class ProcessControlBlock {
    private String processID;
    private int priority;
    private int arrivalTime;
    private int cpuBurst;
    private int startTime;
    private int terminationTime;
    private int turnaroundTime;
    private int waitingTime;
    private int responseTime;

    // Constructor
    public ProcessControlBlock(String processID, int priority, int arrivalTime, int cpuBurst) {
        this.processID = processID;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.cpuBurst = cpuBurst;
    }

    public int getPriority() {
        return priority;
    }

    public String getProcessID() {
        return processID;
    }

    public void setProcessID(String processID) {
        this.processID = processID;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getCpuBurst() {
        return cpuBurst;
    }

    public void setCpuBurst(int cpuBurst) {
        this.cpuBurst = cpuBurst;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getTerminationTime() {
        return terminationTime;
    }

    public void setTerminationTime(int terminationTime) {
        this.terminationTime = terminationTime;
    }

    public int getTurnaroundTime() {
        turnaroundTime=terminationTime-startTime;
        return turnaroundTime;
    }

    

    public int getWaitingTime() {
       
        waitingTime = Math.max(0, startTime - arrivalTime - cpuBurst);
        return waitingTime;
    }
    


    public int getResponseTime() {
      responseTime=startTime-arrivalTime;
        return responseTime;
    }

    

    

}
