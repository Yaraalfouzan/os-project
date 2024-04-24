import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MLQ {
    private ProcessControlBlock[] executedProcesses; // Array to store executed processes
    private int executedCount; // Counter to keep track of executed processes

    private ProcessControlBlock[] Q1;
    private ProcessControlBlock[] Q2;
    private int q1Size;
    private int q2Size;

    // Constructor
    public MLQ(int maxSize) {
        Q1 = new ProcessControlBlock[maxSize];
        Q2 = new ProcessControlBlock[maxSize];
        q1Size = 0;
        q2Size = 0;
        executedProcesses = new ProcessControlBlock[Q1.length + Q2.length]; // Maximum possible size
        executedCount = 0;
    }

    // Method to add a process to the appropriate queue
    public void addProcess(ProcessControlBlock process) {
        if (process.getPriority() == 1) {
            Q1[q1Size] = process;
            q1Size++;
        } else {
            Q2[q2Size++] = process;
        }
    }
    
    
        public void scheduleQ1RoundRobin() {
            int timeQuantum = 3; // Time quantum for Round Robin
    
            // Process each process in Q1
            while (q1Size > 0) {
                // Find the process with the earliest arrival time
                int earliestIndex = findProcessWithEarliestArrivalTime();
    
                // Set the current time to the arrival time of the earliest process
                int currentTime = Q1[earliestIndex].getArrivalTime();
    
                // Execute processes until no processes are left in Q1 or until the current time is
                // greater than the arrival time of the earliest process
                while (q1Size > 0 && currentTime >= Q1[earliestIndex].getArrivalTime()) {
                    ProcessControlBlock process = Q1[earliestIndex];
                    if (process.getStartTime() == -1) {
                        // Set the start time only if the process hasn't started yet
                        process.setStartTime(currentTime);
                    }
                    
                    if (process.getNewCpuBurst() <= timeQuantum) {
                        // Process completes within time quantum
                        currentTime += process.getNewCpuBurst();
                        process.setTerminationTime(currentTime);
                        executedProcesses[executedCount++] = process;
                        removeProcessFromQ1(earliestIndex);
                    } else {
                        // Process needs more time, execute for time quantum and move it to the end of Q1
                        currentTime += timeQuantum;
                        process.setNewCpuBurst(process.getCpuBurst() - timeQuantum);
                        moveToEndOfQ1(process);
                    }
    
                    // Find the process with the earliest arrival time for the next iteration
                    earliestIndex = findProcessWithEarliestArrivalTime();
                }
            }
        }
    
        // Utility method to find the index of the process with the earliest arrival time in Q1
        private int findProcessWithEarliestArrivalTime() {
            int earliestIndex = -1;
            int earliestArrivalTime = Integer.MAX_VALUE;
            for (int i = 0; i < q1Size; i++) {
                if (Q1[i].getArrivalTime() < earliestArrivalTime) {
                    earliestArrivalTime = Q1[i].getArrivalTime();
                    earliestIndex = i;
                }
            }
            return earliestIndex;
        }
    
        // Utility method to remove process at index i from Q1
        private void removeProcessFromQ1(int index) {
            for (int j = index; j < q1Size - 1; j++) {
                Q1[j] = Q1[j + 1];
            }
            q1Size--;
        }
    
        // Utility method to move process to the end of Q1
        private void moveToEndOfQ1(ProcessControlBlock process) {
            for (int i = 0; i < q1Size; i++) {
                if (Q1[i] == process) {
                    removeProcessFromQ1(i);
                    break;
                }
            }
            Q1[q1Size++] = process;
        }
    
        // Generate report
        public void generateReport() {
            try {
                FileWriter writer = new FileWriter("Report.txt");
                writer.write("Scheduling order of processes: ");
    
                // Display scheduling order
                for (int i = 0; i < executedCount; i++) {
                    writer.write(executedProcesses[i].getProcessID() + " | ");
                }
                writer.write("\n\n");
    
                // Detailed information about each process
                writer.write("Process ID | Priority | Arrival Time | CPU Burst | Start Time | Termination Time | Turnaround Time | Waiting Time | Response Time\n");
                for (int i = 0; i < executedCount; i++) {
                    ProcessControlBlock process = executedProcesses[i];
                    writer.write(process.getProcessID() + " | " + process.getPriority() + " | " + process.getArrivalTime() + " | " + process.getCpuBurst() + " | " + process.getStartTime() + " | " + process.getTerminationTime() + " | " + process.getTurnaroundTime() + " | " + process.getWaitingTime() + " | " + process.getResponseTime() + "\n");
                }
    
                // Calculate and display average turnaround time, waiting time, and response time
                double avgTurnaroundTime = 0;
                double avgWaitingTime = 0;
                double avgResponseTime = 0;
                for (int i = 0; i < executedCount; i++) {
                    ProcessControlBlock process = executedProcesses[i];
                    avgTurnaroundTime += process.getTurnaroundTime();
                    avgWaitingTime += process.getWaitingTime();
                    avgResponseTime += process.getResponseTime();
                }
                avgTurnaroundTime /= executedCount;
                avgWaitingTime /= executedCount;
                avgResponseTime /= executedCount;
                writer.write("\nAverage Turnaround Time: " + avgTurnaroundTime + "\n");
                writer.write("Average Waiting Time: " + avgWaitingTime + "\n");
                writer.write("Average Response Time: " + avgResponseTime + "\n");
    
                writer.close();
                System.out.println("Report generated successfully.");
            } catch (IOException e) {
                System.out.println("An error occurred while generating the report: " + e.getMessage());
            }
        }
    
    


// Main class
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the maximum number of processes: ");
        int maxSize = scanner.nextInt();
        MLQ scheduler = new MLQ(maxSize);

        int choice;
        do {
            System.out.println("Menu:");
            System.out.println("1. Enter process’ information.");
            System.out.println("2. Report detailed information about each process and different scheduling criteria.");
            System.out.println("3. Exit the program.");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                  
                    for (int i = 1; i <= maxSize; i++) {
                        System.out.println("Enter details for process P" + i + ":");
                        System.out.print("Priority (1 or 2): ");
                        int priority = scanner.nextInt();
                        System.out.print("Arrival time: ");
                        int arrivalTime = scanner.nextInt();
                        System.out.print("CPU burst time: ");
                        int cpuBurst = scanner.nextInt();
                        ProcessControlBlock process = new ProcessControlBlock("P" + i, priority, arrivalTime, cpuBurst,cpuBurst);
                        scheduler.addProcess(process);
                    }
                    break;
                case 2:
                scheduler.scheduleQ1RoundRobin();
                //add rr scheduller 
                    scheduler.generateReport();//just generate report
                    break;
                case 3:
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter again.");
            }
        } while (choice != 3);

        scanner.close();
    }
}


