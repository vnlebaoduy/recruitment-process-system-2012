/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.DateSelectEvent;
import rps.business.InterviewService;
import rps.entities.Interview;

/**
 *
 * @author user
 */
@ManagedBean
@ViewScoped
public class CalendarMB implements Serializable {

    private Date date;
    private Date currentDate;
    private int month;
    private int year;
    private int day;

    /** Creates a new instance of CalendarMB */
    public CalendarMB() {
    }

    public Date getCurrentDate() {
        if (currentDate == null) {
            currentDate = new Date();
        }
        return currentDate;
    }

    public Date getDate() {
        if (date == null) {
            date = new Date();
        }
        return date;
    }

    public int getMonth() {
        if (month == 0) {
            month = this.getDate().getMonth();
        }
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        if (day == 0) {
            day = 1;
        }
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        if (year == 0) {
            year = this.getDate().getYear();
        }
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Date> getDates() {
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = Calendar.getInstance();
        Date d = new Date(this.getYear(), this.getMonth(), this.getDay());
        calendar.setTime(d);
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        Date startDate = new Date(this.getYear(), this.getMonth(), 1);
        Date endDate = new Date(this.getYear(), this.getMonth(), days);

        Date oldDate = startDate;
        Date newDate = endDate;
        long interval = 24 * 1000 * 60 * 60; // Time 1 day - miliseconds

        //Add sub-date in last month
        calendar.setTime(startDate);
        int dowCurrentFirst = calendar.get(Calendar.DAY_OF_WEEK);
        if (dowCurrentFirst != Calendar.MONDAY) {
            if (dowCurrentFirst == Calendar.SUNDAY) {
                dowCurrentFirst += 7;
            }
            int numLastMonthDays = dowCurrentFirst - Calendar.MONDAY;
            long time = startDate.getTime();
            oldDate = new Date(time - interval * numLastMonthDays);
        }

        //Add sub-date in next month
        calendar.setTime(endDate);
        int dowCurrentLast = calendar.get(Calendar.DAY_OF_WEEK);
        if (dowCurrentLast != Calendar.SUNDAY) {
            int numNextMonthDays = Calendar.SUNDAY + 7 - dowCurrentLast;
            long time = endDate.getTime();
            newDate = new Date(time + interval * numNextMonthDays);
        }

        //Add main-date in this month
        long curTime = oldDate.getTime();
        long endTime = newDate.getTime();
        while (curTime <= endTime) {
            dates.add(new Date(curTime));
            curTime += interval;
        }
        return dates;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String dayInWeekFormat(Object obj) {
        return format(obj, "E");
    }

    public String dayFormat(Object obj) {
        return format(obj, "d");
    }

    public String monthFormat(Object obj) {
        return format(obj, "MMM");
    }

    public String monthFullFormat(Object obj) {
        return format(obj, "MMMM");
    }

    public String monthYearFormat(Object obj) {
        return format(obj, "MMMM yyyy");
    }

    public String dateFormat(Object obj) {
        return format(obj, "MM/dd/yyyy");
    }

    public String titleFormat(Object obj) {
        return format(obj, "E, MMMM d yyyy");
    }

    public String timeFormat(Object obj) {
        return format(obj, "HH:mm");
    }

    public String dateTimeFormat(Object obj) {
        return format(obj, "MM/dd/yyyy HH:mm");
    }

    public String yearFormat(Object obj) {
        return format(obj, "yyyy");
    }

    private String format(Object obj, String pattern) {
        try {
            Date d = (Date) obj;
            Format formatter = new SimpleDateFormat(pattern);
            return formatter.format(d);
        } catch (Exception ex) {
            return "";
        }
    }

    public void nextMonth() {
        int next = this.getMonth() + 1;
        if (next > 12) {
            next = 1;
            this.setYear(year + 1);
        }
        this.setMonth(next);
        this.setDate(new Date(this.getYear(), this.getMonth(), this.getDay()));
    }

    public void backMonth() {
        int back = this.getMonth() - 1;
        if (back <= 0) {
            back = 12;
            this.setYear(year - 1);
        }
        this.setMonth(back);
        this.setDate(new Date(this.getYear(), this.getMonth(), this.getDay()));
    }

    public boolean today(Object obj) {
        Date d = (Date) obj;
        String dFormat = dateFormat(d);
        String currentFormat = dateFormat(this.getCurrentDate());
        if (dFormat.equals(currentFormat)) {
            return true;
        }
        return false;
    }

    public boolean lastMonth(Object obj) {
        Date d = (Date) obj;
        if (d.getYear() == this.getDate().getYear()) {
            if (d.getMonth() == this.getDate().getMonth()) {
                return false;
            }
        }
        return true;
    }
    // <editor-fold defaultstate="collapsed" desc="MANAGE INTERVIEW">
    private Date firstDate;
    private Date lastDate;

    public Date getFirstDate() {
        firstDate = new Date(this.getYear(), this.getMonth(), 1);
        return firstDate;
    }

    public Date getLastDate() {
        Calendar calendar = Calendar.getInstance();
        Date d = new Date(this.getYear(), this.getMonth(), this.getDay());
        calendar.setTime(d);
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        lastDate = new Date(this.getYear(), this.getMonth(), days);
        return lastDate;
    }

    public List<Date> getEventDates() {
        try {
            List<Date> dates = new ArrayList<Date>();
            List<Interview> list = new ArrayList<Interview>();
            InterviewService service = new InterviewService();

            AccountMB bean = (AccountMB) FacesContext.getCurrentInstance().
                    getExternalContext().getSessionMap().get("accountMB");
            if (bean != null) {
                if (bean.isInterviewer()) {
                    if (search) {
                        list = service.getInterviews(bean.getAccount().getEmployee(),
                                getStartTime(), getEndTime(), status);
                    } else {
                        list = service.getEventInterviews(bean.getAccount().getEmployee(),
                                firstDate, lastDate);
                    }
                } else if (bean.ishRGroup()) {
                    if (search) {
                        list = service.getInterviews(getStartTime(), getEndTime(), status);
                    } else {
                        list = service.getInterviews(firstDate, lastDate);
                    }
                }
            }
            for (Interview s : list) {
                Date current = new Date(Date.parse(dateFormat(s.getStartedTime())));
                if (!dates.contains(current)) {
                    dates.add(current);
                }
            }
            if (dates.size() > 0) {
                return dates;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    private String msgInterview;

    public String getMsgInterview() {
        int num = 0;
        String time = "";
        String type = "";
        InterviewService service = new InterviewService();
        AccountMB bean = (AccountMB) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("accountMB");
        if (bean != null) {
            if (bean.isInterviewer()) {

                if (search) {
                    num = service.getInterviews(bean.getAccount().getEmployee(),
                            getStartTime(), getEndTime(), status).size();
                    time = " from " + dateTimeFormat(getStartTime()) + " to "
                            + dateTimeFormat(getEndTime());
                } else {
                    num = service.getInterviews(bean.getAccount().getEmployee(),
                            getFirstDate(), getLastDate()).size();
                    time = " in " + monthFullFormat(getFirstDate());
                }
            } else if (bean.ishRGroup()) {
                if (search) {
                    num = service.getInterviews(getStartTime(), getEndTime(), status).size();
                    time = " from " + dateTimeFormat(getStartTime()) + " to "
                            + dateTimeFormat(getEndTime());
                } else {
                    num = service.getInterviews(getFirstDate(), getLastDate()).size();
                    time = " in " + monthFullFormat(getFirstDate());
                }
            }
        }
        switch (num) {
            case 0:
                msgInterview = "Have not any an " + statusValue() + " interview" + time;
                break;
            case 1:
                msgInterview = "There are 1 " + statusValue() + " interview" + time;
                break;
            default:
                msgInterview = "There are " + num + " " + statusValue() + " interviews" + time;
                break;
        }
        return msgInterview.replaceAll("\\b\\s{2,}\\b", " ");
    }
    // <editor-fold defaultstate="collapsed" desc="SEARCH ON INTERVIEW DATE">
    private boolean search;

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }
    Date startTime;

    public Date getStartTime() {
        if (startTime == null) {
            startTime = getFirstDate();
        }
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    Date endTime;

    public Date getEndTime() {
        if (endTime == null) {
            endTime = getLastDate();
        }
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void handleDateStart(DateSelectEvent event) {
        setStartTime(event.getDate());
    }

    public void handleDateEnd(DateSelectEvent event) {
        setEndTime(event.getDate());
    }
    private int status = -1;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String statusValue() {
        String value = "";
        switch (status) {
            case 0:
                value = "not in progress";
                break;
            case 100:
                value = "selected";
                break;
            case -100:
                value = "rejected";
                break;
            case 99:
                value = "postpone";
                break;
            case 1:
                value = "remove";
                break;
            default:
                break;
        }
        return value;
    }
    // </editor-fold>
    // </editor-fold>    
}
