import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Driver {
	
	public String[] schedularOrder; 

    private ProcessControlBlock[] Q1;
    private ProcessControlBlock[] Q2;
    private int q1Size;
    private int q2Size;
    
    public Driver(int maxSize) {
    	
        Q1 = new ProcessControlBlock[maxSize];
        Q2 = new ProcessControlBlock[maxSize];
        q1Size = 0;
        q2Size = 0;

        schedularOrder= new String[maxSize*3];
    }
    
    // Method to add a process to the appropriate queue
    public void addProcess(ProcessControlBlock process) {
        if (process.getPriority() == 1) {
            Q1[q1Size] = process;
            q1Size++;
        } else {
            Q2[q2Size] = process;
            q2Size++;
        }
    }

    void sortQ2() {
    	if(q2Size!=0&& q2Size!=1){
        for (int i = 0; i < q2Size - 1; i++) {
            
            for (int j = 0; j < q2Size - i - 1; j++) {
                if (Q2[j].getCpuBurst() > Q2[j+1].getCpuBurst()) {
                    ProcessControlBlock temp = Q2[j];
                    Q2[j]=Q2[j + 1];
                    Q2[j + 1]=temp;   
            }
        }
    }}
            }// end of sort
    
    private void removeProcessFromQ1(int index) {
        for (int j = index; j < q1Size - 1; j++) {
            Q1[j] = Q1[j + 1];
        }
        q1Size--;
    }
    
    private void removeProcessFromQ2(int index) {
        for (int j = index; j < q2Size - 1; j++) {
            Q2[j] = Q2[j + 1];
        }
        q2Size--;
    }
    
    public int search(ProcessControlBlock process, ProcessControlBlock[] array) {
    	int j=0;
    	for (j = 0; j < array.length; j++){
            if(process.getProcessID().equalsIgnoreCase(array[j].getProcessID()))
            	break;
    	}
    	return j;
    }
    
    void execute(ProcessControlBlock[] processes) { //parameter is the processes that the user will enter and it is ordered by its arrival time "scheduler"

    	
    	int counter=0;
        int currentTime = 0;
        int i=0; 
        int tempBurst=0;
  

        
        do {
        	
        	while(q1Size==0 && q2Size==0) {
        if(counter<processes.length) {// so when we at the last process there won't be any left to add
        	if(currentTime>=processes[counter].getArrivalTime()) {
        			addProcess(processes[counter]);
        			counter++;
        	}}
        if(q1Size==0 && q2Size==0)
        currentTime++;
        	}
        			while(q1Size>0) { //round robin
        				
        				tempBurst =Q1[0].getCpuBurst(); 
        				int index=search(Q1[0], processes);
        				processes[index].setStartTime(currentTime);
        				processes[index].setResponseTime(processes[index].getStartTime()-processes[index].getArrivalTime());
        				
        				for(int j=0;j<3;j++) {
        					
        					if(tempBurst!=0)
            					currentTime++;
        					
        					tempBurst=tempBurst-1;
        					
        					if(i!=0) {
        						if(!(schedularOrder[i-1].equalsIgnoreCase(Q1[0].getProcessID())))
        							schedularOrder[i]=Q1[0].getProcessID();
        						}
        					else {
        						schedularOrder[i]=Q1[0].getProcessID();
        					}
        					
        					 if(counter<processes.length) {
        					if(currentTime>=processes[counter].getArrivalTime()) {
        						addProcess(processes[counter]);
        						counter++;}	
        					}  
        				}// end for 
        				
        				if(tempBurst>0) { // add it to Q2
        					Q2[q2Size++] =new ProcessControlBlock(Q1[0].getProcessID(), Q1[0].getPriority(), Q1[0].getArrivalTime(), tempBurst) ;
        				}
        				
        				else if(tempBurst<=0) {
        					processes[index].setTerminationTime(currentTime);
        					processes[index].setTurnaroundTime(processes[index].getTerminationTime()-processes[index].getArrivalTime());
        					processes[index].setWaitingTime(processes[index].getTurnaroundTime()-processes[index].getCpuBurst());

                        }
        				
        				removeProcessFromQ1(0);
        				i++;
        			}// end while RR
        			
        			while(q2Size>0 && q1Size==0) { //SJF
        				
        				while(counter<processes.length&&currentTime>=processes[counter].getArrivalTime()) {
        					for(int l=0;l<processes.length-counter;l++) {
        				if(counter<processes.length) {
       					 if(currentTime>=processes[counter+l].getArrivalTime()) { 
       						 addProcess(processes[counter]);
        						counter++;}
       					 }}}
        				
        				sortQ2();
        				int index= search(Q2[0], processes);
        				
        				if(processes[index].getPriority()==2) {
        				processes[index].setStartTime(currentTime);
        				processes[index].setResponseTime(processes[index].getStartTime()-processes[index].getArrivalTime());
        				}
        				
        				 for(int j=0;j<Q2[0].getCpuBurst();j++)   {
        					 currentTime++;
        					 if(counter<processes.length) {
        					 if(currentTime>=processes[counter].getArrivalTime()) { 
        						 addProcess(processes[counter]);
         						counter++;}
        					 }
        				 }// end for
        				 processes[index].setTerminationTime(currentTime);
        				 processes[index].setTurnaroundTime(processes[index].getTerminationTime()-processes[index].getArrivalTime());
        				 processes[index].setWaitingTime(processes[index].getTurnaroundTime()- processes[index].getCpuBurst());

        				 if(i!=0) {
     						if(!(schedularOrder[i-1].equalsIgnoreCase(Q2[0].getProcessID())))
     							schedularOrder[i]=Q2[0].getProcessID();
     						}
     					else {
     						schedularOrder[i]=Q2[0].getProcessID();
     					}
        				 i++;
        
        				 removeProcessFromQ2(0);
        				 
                        }
        			// end while SJF
        			
        		
        	
        }while(q1Size!=0||q2Size!=0||counter<processes.length) ; 
        
        
    }// end execute 
     public void generateReport(ProcessControlBlock[] schedular) {
            try {
                FileWriter writer = new FileWriter("Report.txt");
                writer.write("Scheduling order of processes: ");
    
                // Display scheduling order
                for (int i = 0; i < schedularOrder.length; i++) {
                	if(schedularOrder[i]==null)
                		break;
                    writer.write(schedularOrder[i] + " | ");
                }
                writer.write("\n\n");
    
                // Detailed information about each process
                writer.write("Process ID | Priority | Arrival Time | CPU Burst | Start Time | Termination Time | Turnaround Time | Waiting Time | Response Time\n");
                for (int i = 0; i < schedular.length; i++) {
                    ProcessControlBlock process = schedular[i];
                    writer.write(process.getProcessID() + " | " + process.getPriority() + " | " + process.getArrivalTime() + " | " + process.getCpuBurst() + " | " + process.getStartTime() + " | " + process.getTerminationTime() + " | " + process.getTurnaroundTime() + " | " + process.getWaitingTime() + " | " + process.getResponseTime() + "\n");
                }
    
                // Calculate and display average turnaround time, waiting time, and response time
                double avgTurnaroundTime = 0;
                double avgWaitingTime = 0;
                double avgResponseTime = 0;
                for (int i = 0; i < schedular.length; i++) {
                    ProcessControlBlock process = schedular[i];
                    avgTurnaroundTime += process.getTurnaroundTime();
                    avgWaitingTime += process.getWaitingTime();
                    avgResponseTime += process.getResponseTime();
                }
                avgTurnaroundTime /= schedular.length;
                avgWaitingTime /= schedular.length;
                avgResponseTime /= schedular.length;
                writer.write("\nAverage Turnaround Time: " + avgTurnaroundTime + "\n");
                writer.write("Average Waiting Time: " + avgWaitingTime + "\n");
                writer.write("Average Response Time: " + avgResponseTime + "\n");
    
                writer.close();
                System.out.println("Report generated successfully.\n");
            } catch (IOException e) {
                System.out.println("An error occurred while generating the report: " + e.getMessage());
            }
        }
    
    

    
 // Main class
 public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter the maximum number of processes: ");
    int maxSize = scanner.nextInt();

    ProcessControlBlock[] scheduler = new ProcessControlBlock[maxSize];
    Driver p = new Driver(maxSize);

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
                for (int i = 0; i < maxSize; i++) { // Start from 0
                    System.out.println("Enter details for process P" + (i + 1) + ":");
                    int priority;
                    while (true) { // to make sure that the user only enters 1 or 2
                        System.out.print("Priority (1 or 2): ");
                        priority = scanner.nextInt();
                        if (priority == 1 || priority == 2)
                            break;
                        else {
                            System.out.println("Only choose between 1 or 2");
                        }
                    }
                    System.out.print("Arrival time: ");
                    int arrivalTime = scanner.nextInt();
                    System.out.print("CPU burst time: ");
                    int cpuBurst = scanner.nextInt();
                    ProcessControlBlock process = new ProcessControlBlock("P" + (i + 1), priority, arrivalTime, cpuBurst);
                    scheduler[i] = process;
                }

                // Sort scheduler based on arrival time
                for (int j = 0; j < scheduler.length; j++) {
                    for (int k = 0; k < scheduler.length - j - 1; k++) { // Use k instead of j
                        if (scheduler[k].getArrivalTime() > scheduler[k + 1].getArrivalTime()) {
                            ProcessControlBlock temp = scheduler[k];
                            scheduler[k] = scheduler[k + 1];
                            scheduler[k + 1] = temp;
                        }
                    }
                }
                break;

            case 2:
            	if(scheduler[0]==null) {
            		System.out.println("no processes entered yet");
            		break;
            	}
                p.execute(scheduler); // call method execute
               p.generateReport(scheduler);//just generate report
               
               System.out.println("Scheduling order of processes:\n ");
               for (int i = 0; i < p.schedularOrder.length; i++) {
               	if(p.schedularOrder[i]==null)
               		break;
                   System.out.print(p.schedularOrder[i] + " | ");
               }
               System.out.println("\n");
               System.out.println("final results:");
               System.out.println("\npID | P | A | B | S | Te | Tur | W | R\n");
               for (int i = 0; i < scheduler.length; i++) {
                   ProcessControlBlock process = scheduler[i];
                   System.out.println(process.getProcessID() + " | " + process.getPriority() + " | " + process.getArrivalTime() + " | " + process.getCpuBurst() + " | " + process.getStartTime() + " | " + process.getTerminationTime() + " | " + process.getTurnaroundTime() + " | " + process.getWaitingTime() + " | " + process.getResponseTime() + "\n");
               }
               double avgTurnaroundTime = 0;
               double avgWaitingTime = 0;
               double avgResponseTime = 0;
               for (int i = 0; i < scheduler.length; i++) {
                   ProcessControlBlock process = scheduler[i];
                   avgTurnaroundTime += process.getTurnaroundTime();
                   avgWaitingTime += process.getWaitingTime();
                   avgResponseTime += process.getResponseTime();
               }
               avgTurnaroundTime /= scheduler.length;
               avgWaitingTime /= scheduler.length;
               avgResponseTime /= scheduler.length;
               System.out.println("\nAverage Turnaround Time: " + avgTurnaroundTime + "\n");
               System.out.println("Average Waiting Time: " + avgWaitingTime + "\n");
               System.out.println("Average Response Time: " + avgResponseTime + "\n");
   
               
                break;
               

            case 3:
                System.out.println("Exiting the program.");
                break;
            default:
                System.out.println("Invalid choice. Please enter again.");
        }
    } while (choice != 3);

    scanner.close();
}}