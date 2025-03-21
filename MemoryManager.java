package p1;

import java.util.*;

public class MemoryManager {

    List<MemoryBlock> memoryBlocks;

    public MemoryManager(List<Integer> blockSizes) {
        memoryBlocks = new ArrayList<>();
        int startAddress = 0; // Starting address of the first block

        for (int size : blockSizes) {
            memoryBlocks.add(new MemoryBlock(startAddress, size)); // Create memory block and add to the list
            startAddress += size; // Update start address for the next block
        }
    }

    
    public boolean allocateMemory(String processID, int processSize, int strategy) {
        MemoryBlock chosenBlock = null; // Store selected block
        
        if (strategy == 1) { 
            for (MemoryBlock block : memoryBlocks) {
                if (!block.isAllocated && block.blockSize >= processSize) {
                    chosenBlock = block;
                    break;
                }
            }
        } else if (strategy == 2) { 
            int minSize = Integer.MAX_VALUE;
            for (MemoryBlock block : memoryBlocks) {
                if (!block.isAllocated && block.blockSize >= processSize && block.blockSize < minSize) {
                    chosenBlock = block;
                    minSize = block.blockSize;
                }
            }
        } else if (strategy == 3) { 
            int maxSize = 0;
            for (MemoryBlock block : memoryBlocks) {
                if (!block.isAllocated && block.blockSize >= processSize && block.blockSize > maxSize) {
                    chosenBlock = block;
                    maxSize = block.blockSize;
                }
            }
        }
        
        // If a suitable block is found, allocate it
        if (chosenBlock != null) {
            chosenBlock.allocate(processID, processSize);
            System.out.println(processID + " allocated at address " + chosenBlock.startAddress 
                    + ", and the internal fregmentation is " + chosenBlock.IF);
            return true;
        } else {
            System.out.println("Error: Not enough memory for process " + processID);
            return false;
        }
    }

    public void deallocateMemory(String processID) {
        for (MemoryBlock block : memoryBlocks) {
            if (block.isAllocated && block.processID.equals(processID)) {
                block.deallocate(); // Free memory block
                System.out.println("Process " + processID + " deallocated.");
                return;
            }
        }
        System.out.println("Error: Process " + processID + " not found.");
    }

    public void printMemoryReport() {
        boolean hasAllocatedBlock = false;

        // Check if any block is allocated
        for (MemoryBlock block : memoryBlocks) {
            if (block.isAllocated) {
                hasAllocatedBlock = true;
                break;
            }
        }
		

        if (hasAllocatedBlock) {
            System.out.println("=====================================================================================");
            System.out.printf("%-10s %-10s %-15s %-12s %-15s %-22s%n",
                    "Block#", "Size", "Start-End", "Status", "ProcessID", "InternalFregmentation");
            System.out.println("=====================================================================================");
        } else {
            System.out.println("===================================================");
            System.out.printf("%-10s %-10s %-15s %-12s%n",
                    "Block#", "Size", "Start-End", "Status");
            System.out.println("===================================================");
        }

       // Print memory blocks
	   for (int i = 0; i < memoryBlocks.size(); i++) {
		MemoryBlock block = memoryBlocks.get(i);

		if (hasAllocatedBlock) {
			System.out.printf("%-10s %-10d %-15s %-12s %-15s %-22d%n",
					"Block" + i, block.blockSize,
					block.startAddress + "-" + block.endAddress,
					(block.isAllocated ? "allocated" : "free"),
					(block.isAllocated ? block.processID : "Null"),
					(block.isAllocated ? block.IF : 0));
		} else {
			System.out.printf("%-10s %-10d %-15s %-12s%n",
					"Block" + i, block.blockSize,
					block.startAddress + "-" + block.endAddress,
					(block.isAllocated ? "allocated" : "free"));
		}
	}

	if (hasAllocatedBlock) {
		System.out.println("=====================================================================================");
	} else {
		System.out.println("===================================================");
	}
}
}