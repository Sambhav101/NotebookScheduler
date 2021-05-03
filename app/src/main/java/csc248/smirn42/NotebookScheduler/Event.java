package csc248.smirn42.NotebookScheduler;

public class Event {
    private String eventName;
    private int EventColor;
    private boolean checked ;
    public Event(String eventName, int eventColor , boolean checked) {
        this.eventName = eventName;
        this.EventColor = eventColor;
        this.checked = checked;
    }


    public boolean isChecked() {
        return checked;
    }

    public String getEventName() {
        return eventName;
    }


    public int getEventColor() {
        return EventColor;
    }

}
