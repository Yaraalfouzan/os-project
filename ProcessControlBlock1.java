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
  
//actual class
    // Constructor
    public ProcessControlBlock(String processID, int priority, int arrivalTime, int cpuBurst) {
        this.processID = processID;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.cpuBurst = cpuBurst;
        this.startTime = 0;
        this.terminationTime = 0;
        this.turnaroundTime = 0;
        this.waitingTime = 0;
        this.responseTime = 0;
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
       turnaroundTime=terminationTime-arrivalTime;
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
    	this.turnaroundTime=turnaroundTime;
		
	}
    

    public int getWaitingTime() {
       
       waitingTime =  (terminationTime- arrivalTime) - cpuBurst;
        return waitingTime;
    }
    
    public void setWaitingTime(int waitingTime) {
    	this.waitingTime=waitingTime;
    }
    


    public int getResponseTime() {
     responseTime=startTime-arrivalTime;
        return responseTime;
    }

    public void setResponseTime(int ResponseTime) {
    	this.responseTime = ResponseTime;
    }
    

    

}