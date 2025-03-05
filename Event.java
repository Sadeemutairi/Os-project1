class Event implements Comparable<Event> {
    int Time;
    String Type;
    Process process;

    public Event(int Time, String Type, Process process) {
        this.Time = Time;
        this.Type = Type;
        this.process = process;
    }

    public int compareTo(Event other) {
        return this.Time - other.Time;
    }
}
