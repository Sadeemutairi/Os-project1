package p1;

public class MemoryBlock {
	
	int blockSize;
    int startAddress;
    int endAddress;
    boolean isAllocated; // status (allocated or free)
    String processID;
    int IF; // internal fragmentation size

    public MemoryBlock(int start, int size) {
    	
        this.startAddress = start;
        this.blockSize = size;
        this.endAddress = start + size - 1;
        this.isAllocated = false;
        this.processID = "Null"; // default
        this.IF = 0;
    }
    
    // allocate memory block to a process
    public void allocate(String processID, int processSize) {
    	
        this.isAllocated = true;
        this.processID = processID;
        this.IF = this.blockSize - processSize;
    }

    public void deallocate() {
    	
        this.isAllocated = false;
        this.processID = "Null";
        this.IF = 0; // reset fragmentation
    }

}
