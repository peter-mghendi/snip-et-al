package com.boophq.snipetal;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "snips")
public class Snip {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String subject;

    private String content;

    private int priority;

    public Snip(String subject, String content, int priority) {
        this.subject = subject;
        this.content = content;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public int getPriority() {
        return priority;
    }
}
