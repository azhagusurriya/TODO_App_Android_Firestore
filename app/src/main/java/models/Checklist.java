package models;

import java.util.Calendar;
import java.util.Date;

public class Checklist {
    String id = "";
    String title;
    Boolean completion;
    Date dateCreated;

    public Checklist(String title){
        this.title = title;
        this.completion = false;
        this.dateCreated = Calendar.getInstance().getTime();
    }

    public Checklist(String title, Boolean completion, Date dateCreated) {
        this.title = title;
        this.completion = completion;
        this.dateCreated = dateCreated;
    }

    public Checklist(){
        this.title = "";
        this.completion = false;
        this.dateCreated = Calendar.getInstance().getTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompletion() {
        return completion;
    }

    public void setCompletion(Boolean completion) {
        this.completion = completion;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "Checklist{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", completion=" + completion +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
