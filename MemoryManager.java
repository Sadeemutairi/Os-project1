package p1;

import java.util.*;


public class MemoryManager {

	List<MemoryBlock> memoryBlocks;

	public MemoryManager(List<Integer> blockSizes) {

		memoryBlocks = new ArrayList<>();
		int startAddress = 0; // starting address of first block

		for (int size : blockSizes) {
			memoryBlocks.add( new MemoryBlock(startAddress, size) ); // create memory block and add to list of blocks
			startAddress += size;  // start address for next block
		}
	}
	
    // allocate memory based on strategy: First-Fit, Best-Fit, Worst-Fit
	public boolean allocateMemory(String processID, int processSize, int strategy) {
		
		MemoryBlock chosenBlock = null; // to store selected block
        
		// First-Fit : find first suitable block  (faster but may lead to fragmentation)
		if (strategy == 1) { 
			
			for (MemoryBlock block : memoryBlocks) {
				if (!block.isAllocated && block.blockSize >= processSize) { //choose available block that is large enough
					chosenBlock = block;
					break;
				}
			}
		} 
		
		// Best-Fit : find smallest suitable block (minimizes fragmentation)
		else if (strategy == 2) { 
			int minSize = Integer.MAX_VALUE; //  largest possible value, any valid block will be smaller than this
			
			for (MemoryBlock block : memoryBlocks) {
				if (!block.isAllocated && block.blockSize >= processSize && block.blockSize < minSize) { // choose the smallest possible suitable block
					chosenBlock = block;
					minSize = block.blockSize;
				}
			}
		} 
		
		// Worst-Fit : find largest suitable block (potentially leaves larger gaps)
		else if (strategy == 3) { 
			int maxSize = 0; // any valid block found will be larger than this
			
			for (MemoryBlock block : memoryBlocks) {
				if (!block.isAllocated && block.blockSize >= processSize && block.blockSize > maxSize) { //choose the largest possible suitable block
					chosenBlock = block;
					maxSize = block.blockSize;
				}
			}
		}
		
        // if a suitable block is found
		if (chosenBlock != null) {
			
			chosenBlock.allocate(processID, processSize);
			System.out.println(processID + " Allocated at address " + chosenBlock.startAddress + ",and the internal fragmentation is " + chosenBlock.IF);
			return true;
			
		} else { // allocation failed
			System.out.println("Error: not enough memory for process " + processID);
			return false;
		}
	}

	public void deallocateMemory(String processID) {
		
		for (MemoryBlock block : memoryBlocks) {
			
			if ( block.isAllocated && block.processID.equals(processID) ) {
				
				block.deallocate(); // free memory block
				System.out.println("Process " + processID + " deallocated.");
				return;
			}
		}
		System.out.println("Error: Process " + processID + " not found.");
	}

	public void printMemoryReport() {
		
		System.out.println("==========================================================");
		System.out.printf("%-8s %-8s %-12s %-10s %-10s %-10s%n", 
                "Block#", "Size", "Start-End", "Status", "ProcessID", "InternalFrag");
		System.out.println("==========================================================");
		
		for (int i = 0; i < memoryBlocks.size(); i++) {
			
			MemoryBlock block = memoryBlocks.get(i);
			String processID = block.isAllocated ? block.processID : "Null"; // handle unallocated blocks
			int internalFrag = block.isAllocated ? block.IF : 0; 
			
			System.out.printf("Block%-3d %-6d %6d-%-6d %-13s %-10s %-10d%n",
					i, block.blockSize, block.startAddress, block.endAddress,
					(block.isAllocated ? "allocated" : "free"), processID , internalFrag );
		}
		System.out.println("==========================================================");
	}


}
