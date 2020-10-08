package ua.edu.sumdu.j2se.tenytskyi.tasks;

public class Task {
    private String title;
    private int time;
    private int start;
    private int end;
    private int interval;
    private boolean active;
    public Task(String title, int time)
    {
        if(time > 0) {
            this.title = title;
            this.time = time;
            this.interval = 0;
            this.active = false;
        }
    }

    public Task(String title, int start, int end, int interval)
    {
        if(start > 0 && end > 0 && interval > 0) {
            this.title = title;
            this.start = start;
            this.end = end;
            this.interval = interval;
            this.active = false;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTime() {
        if(this.interval > 0) {
            return start;
        }
        else {
            return time;
        }
    }

    public void setTime(int time) {
        if(time >= 0) {
            this.time = time;
            interval = 0;
        }
    }

    public int getStartTime() {
        if(interval > 0) {
            return start;
        }
        else {
            return time;
        }
    }

    public int getEndTime() {
        if(interval > 0) {
            return end;
        }
        else {
            return time;
        }
    }

    public int getRepeatInterval() {
        return interval;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isRepeated() {
        if(this.interval > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public void setTime(int start, int end, int interval)
    {
        if(start > 0 && end > 0 && interval > 0) {
            this.start = start;
            this.end = end;
            this.interval = interval;
        }
    }

    public int nextTimeAfter(int current)
    {
        if(current >= 0 && this.active == true) {
            if (interval > 0) {
                if(current < this.start)
                {
                    return this.start;
                }
                int prevTime = this.start;
                for (int i = this.start; i < this.end; i += interval) {
                    if (current < i && current >= prevTime) {
                        return i;
                    }
                    prevTime = i;
                }
                return -1;
            } else {
                if(current < this.time)
                {
                    return this.time;
                }
                else
                {
                    return -1;
                }
            }
        }
        else {
            return -1;
        }
    }
}
