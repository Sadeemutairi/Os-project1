package p1;

import java.util.*;

public class OS2 {
	
	
	

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
	    // get memory info from user
		System.out.print("Enter the total number of blocks: ");
        int numBlocks = scan.nextInt();
        
        List<Integer> blockSizes = new ArrayList<>();
        
        System.out.println("Enter the size of each block in KB: ");
        
        for (int i = 0; i < numBlocks; i++) {
            blockSizes.add(scan.nextInt());
        }
        
        MemoryManager memoryManager = new MemoryManager(blockSizes);
        
        // allocation strategy
        System.out.print("Enter allocation strategy (1 for first-fit, 2 for best-fit, 3 for worst-fit): ");
        int s = scan.nextInt();
        
        System.out.println("\nMemory blocks are created…");
        System.out.println("Memory blocks:");
        memoryManager.printMemoryReport();
        
        // run simulation
        while (true) {
            System.out.println("\n============================================");
            System.out.println("1) Allocates memory blocks");
            System.out.println("2) De-allocates memory blocks");
            System.out.println("3) Print report about the current state of memory and internal Fragmentation");
            System.out.println("4) Exit");
            System.out.println("============================================");
            System.out.print("Enter your choice: ");
            
            int choice = scan.nextInt();
            scan.nextLine();  // scan newline

            if (choice == 1) {
                System.out.print("Enter the process ID and size of process: ");
                String processID = scan.next();
                int processSize = scan.nextInt();
                memoryManager.allocateMemory(processID, processSize, s);

            } else if (choice == 2) {
                System.out.print("Enter the process ID to deallocate: ");
                String processID = scan.next();
                memoryManager.deallocateMemory(processID);

            } else if (choice == 3) {
                memoryManager.printMemoryReport();

            } else if (choice == 4) {
                System.out.println("Exiting the simulation...");
                break;

            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
        scan.close();
    }

	

}
