package DB;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author josue
 */
public class Reminder{

    public Reminder(Timestamp StartTIME, Timestamp EndTIME, String message, String reminderName, String ReminderID) {
        this.StartTIME = StartTIME;
        this.EndTIME = EndTIME;
        this.message = message;
        this.reminderName = reminderName;
        this.ReminderID = ReminderID;
    }
    
    private Timestamp StartTIME;
    private Timestamp EndTIME;
    private String message;
    private String reminderName;
    private String ReminderID;

    Reminder() {
    }

    public Timestamp getStartTIME() {
        return StartTIME;
    }

    public void setStartTIME(Timestamp StartTIME) {
        this.StartTIME = StartTIME;
    }

    public Timestamp getEndTIME() {
        return EndTIME;
    }

    public void setEndTIME(Timestamp EndTIME) {
        this.EndTIME = EndTIME;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReminderName() {
        return reminderName;
    }

    public void setReminderName(String reminderName) {
        this.reminderName = reminderName;
    }

    public String getReminderID() {
        return ReminderID;
    }

    public void setReminderID(String ReminderID) {
        this.ReminderID = ReminderID;
    }


}

