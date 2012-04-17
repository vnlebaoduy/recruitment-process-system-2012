/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rps.dataaccess.FindResult;
import rps.dataaccess.ScheduleDA;
import rps.entities.Applicant;
import rps.entities.Employee;
import rps.entities.Schedule;

/**
 *
 * @author Bach Luong
 */
public class ScheduleService extends AbstractService {

    private ScheduleDA scheduleDA;

    public ScheduleService() {
        scheduleDA = new ScheduleDA(getEntityManager());
    }

    public Schedule addSchedule(Schedule entity) {
        Schedule addEntity = new Schedule();
        addEntity.setScheduleID(generateID());
        addEntity.setApplicant(entity.getApplicant());
        addEntity.setEmployee(entity.getEmployee());
        addEntity.setEndedTime(entity.getEndedTime());
        addEntity.setStartedTime(entity.getStartedTime());
        addEntity.setStatus(0);
        scheduleDA.create(addEntity);
        return addEntity;
    }

    public String checkHour(Date startTime, Date endTime, String emplID, String applID) {
        String flag = "";
        List<Schedule> lstSchedule = new ArrayList<Schedule>();
        Schedule entity;
        lstSchedule = scheduleDA.findAll();
        Date timeNow = new Date();
        if (startTime.after(endTime)) {
            flag = "Thời gian bắt đầu phải trước thời gian kết thúc phỏng vấn";
        }
        if (startTime.before(timeNow) || endTime.before(timeNow)) {
            flag = "Thời gian phỏng vấn không thể trước hoặc bằng với thời gian hiện tại";
        }
        if(!(startTime.getDay() == endTime.getDay())){
        flag = "Thời gian phỏng vấn phải trong cùng 1 ngày";
        }
        for (int count = 0; count < lstSchedule.size(); count++) {
            entity = lstSchedule.get(count);
            if ((emplID.equals(entity.getEmployee().getEmployeeID()) && applID.equals(entity.getApplicant().getApplicantID()))||(emplID.equals(entity.getEmployee().getEmployeeID()))||(applID.equals(entity.getApplicant().getApplicantID()))) {
                if (startTime.after(entity.getStartedTime()) && startTime.before(entity.getEndedTime())) {
                    flag = "Employee hoặc Vacancy đã được lên lịch trùng với thời gian bắt đầu này";
                    break;
                }
                if (startTime.after(entity.getStartedTime()) && endTime.before(entity.getEndedTime())) {
                    flag = "Employee hoặc Vacancy đã được lên lịch trong khoảng thời gian bắt đầu và kết thúc này";
                    break;
                }
                if (startTime.before(entity.getStartedTime()) && endTime.before(entity.getEndedTime())) {
                    flag = "Employee hoặc Vacancy đã được lên lịch trong khoảng thời gian bắt đầu và kết thúc này";
                    break;
                }
            } 
        }

        return flag;
    }

    public List<Schedule> getSchedules(Date date) {
        //return ScheduleDA.findRelatively("startedTime", date);
        return scheduleDA.searchSchedule(date);
    }

    public List<Schedule> getSchedules(Date date, int status) {
        return scheduleDA.findAbsolutely(new String[]{"startedTime", "status"},
                new Object[]{date, status}, null, null, -1, -1);
    }

    public List<Schedule> getSchedules(Employee employee, int status) {
        return scheduleDA.findAbsolutely(new String[]{"employee", "status"},
                new Object[]{employee, status}, null, null, -1, -1);
    }

    public List<Schedule> getCurrentSchedules(Employee employee) {
        return scheduleDA.getNotRemoveSchedule(employee, 1);
    }

    public Schedule getSchedule(String id) {
        try {
            return scheduleDA.find(id);
        } catch (Exception ex) {
            return null;
        }
    }

    public Schedule updateSchedule(String id, Employee employee,
            Applicant applicant, Date startedTime, Date endedTime, int status)
            throws Exception {
        Schedule schedule = getSchedule(id);
        if (schedule == null) {
            throw new Exception("Schedule not found.");
        }
        schedule.setEmployee(employee);
        schedule.setApplicant(applicant);
        schedule.setStartedTime(startedTime);
        schedule.setEndedTime(endedTime);
        schedule.setStatus(status);
        scheduleDA.edit(schedule);
        return schedule;
    }

    private String generateID() {
        FindResult<Schedule> schedules = scheduleDA.findAbsolutely(null, null, new String[]{"scheduleID"}, new String[]{"DESC"}, 0, 1);
        String newID = "S";
        if (schedules.size() > 0) {
            Schedule schedule = schedules.get(0);
            String sId = schedule.getScheduleID();
            String number = sId.substring(1, sId.length());
            int id = Integer.parseInt(number) + 1;
            if (id / 1000 > 0) {
                newID += String.valueOf(id);
            } else if (id / 100 > 0) {
                newID += "0" + String.valueOf(id);
            } else if (id / 10 > 0) {
                newID += "00" + String.valueOf(id);
            } else {
                newID += "000" + String.valueOf(id);
            }
        }
        return newID;
    }
}
