package Model.event;

import java.awt.*;

public class EventRectangle extends Rectangle {

    private int eventRectDefaultX, eventRectDefaultY;
    private boolean eventDone = false;

    public int getEventRectDefaultX() {
        return eventRectDefaultX;
    }

    public void setEventRectDefaultX(int eventRectDefaultX) {
        this.eventRectDefaultX = eventRectDefaultX;
    }

    public int getEventRectDefaultY() {
        return eventRectDefaultY;
    }

    public void setEventRectDefaultY(int eventRectDefaultY) {
        this.eventRectDefaultY = eventRectDefaultY;
    }

    public boolean isEventDone() {
        return eventDone;
    }

    public EventRectangle setEventDone(boolean eventDone) {
        this.eventDone = eventDone;
        return this;
    }
}
