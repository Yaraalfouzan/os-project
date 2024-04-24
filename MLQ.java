import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MLQ {
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
    }

    // Method to add a process to the appropriate queue
    public void addProcess(ProcessControlBlock process) {
        if (process.getPriority() == 1) {
            Q1[q1Size++] = process;
        } else {
            Q2[q2Size++] = process;
        }
    }
    public void scheduleQ1RoundRobin() {
        int timeQuantum = 3; // Time quantum for Round Robin
        int currentTime = 0; // Current time

        // Process each process in Q1
        for (int i = 0; i < q1Size; i++) {
            ProcessControlBlock process = Q1[i];
            process.setStartTime(currentTime);
            if (process.getCpuBurst() <= timeQuantum) {
                // Process completes within time quantum
                currentTime += process.getCpuBurst();
                process.setTerminationTime(currentTime);
            } else {
                // Process needs more time, add it back to Q1
                currentTime += timeQuantum;
                process.setCpuBurst(process.getCpuBurst() - timeQuantum);
                Q1[q1Size++] = process; // Add back to Q1
            }
        }
    }
    //  generate report
    public void generateReport() {
       
    try {
        FileWriter writer = new FileWriter("Report.txt");
        writer.write("Scheduling order of processes: ");
        
        // Display scheduling order
        for (int i = 0; i < q1Size; i++) {
            writer.write(Q1[i].getProcessID() + " | ");
        }
        for (int i = 0; i < q2Size; i++) {
            writer.write(Q2[i].getProcessID() + " | ");
        }
        writer.write("\n\n");

        // Detailed information about each process
        writer.write("Process ID | Priority | Arrival Time | CPU Burst | Start Time | Termination Time | Turnaround Time | Waiting Time | Response Time\n");
        for (int i = 0; i < q1Size; i++) {
            ProcessControlBlock process = Q1[i];
            writer.write(process.getProcessID() + " | " + process.getPriority() + " | " + process.getArrivalTime() + " | " + process.getCpuBurst() + " | " + process.getStartTime() + " | " + process.getTerminationTime() + " | " + process.getTurnaroundTime() + " | " + process.getWaitingTime() + " | " + process.getResponseTime() + "\n");
        }
        for (int i = 0; i < q2Size; i++) {
            ProcessControlBlock process = Q2[i];
            writer.write(process.getProcessID() + " | " + process.getPriority() + " | " + process.getArrivalTime() + " | " + process.getCpuBurst() + " | " + process.getStartTime() + " | " + process.getTerminationTime() + " | " + process.getTurnaroundTime() + " | " + process.getWaitingTime() + " | " + process.getResponseTime() + "\n");
        }

        // Calculate and display average turnaround time, waiting time, and response time
        double avgTurnaroundTime = 0;
        double avgWaitingTime = 0;
        double avgResponseTime = 0;
        for (int i = 0; i < q1Size; i++) {
            ProcessControlBlock process = Q1[i];
            avgTurnaroundTime += process.getTurnaroundTime();
            avgWaitingTime += process.getWaitingTime();
            avgResponseTime += process.getResponseTime();
        }
        for (int i = 0; i < q2Size; i++) {
            ProcessControlBlock process = Q2[i];
            avgTurnaroundTime += process.getTurnaroundTime();
            avgWaitingTime += process.getWaitingTime();
            avgResponseTime += process.getResponseTime();
        }
        avgTurnaroundTime /= (q1Size + q2Size);
        avgWaitingTime /= (q1Size + q2Size);
        avgResponseTime /= (q1Size + q2Size);
        writer.write("\nAverage Turnaround Time: " + avgTurnaroundTime + "\n");
        writer.write("Average Waiting Time: " + avgWaitingTime + "\n");
        writer.write("Average Response Time: " + avgResponseTime + "\n");

        writer.close();
        System.out.println("Report generated successfully.");
    } catch (IOException e) {
        System.out.println("An error occurred while generating the report: " + e.getMessage());
    }
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
                    System.out.print("Enter the number of processes: ");
                    int numProcesses = scanner.nextInt();
                    for (int i = 1; i <= numProcesses; i++) {
                        System.out.println("Enter details for process P" + i + ":");
                        System.out.print("Priority (1 or 2): ");
                        int priority = scanner.nextInt();
                        System.out.print("Arrival time: ");
                        int arrivalTime = scanner.nextInt();
                        System.out.print("CPU burst time: ");
                        int cpuBurst = scanner.nextInt();
                        ProcessControlBlock process = new ProcessControlBlock("P" + i, priority, arrivalTime, cpuBurst);
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


