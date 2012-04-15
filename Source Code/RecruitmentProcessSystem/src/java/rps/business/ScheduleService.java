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

    public Boolean checkHour(Date startTime, Date endTime) {
        Boolean flag = true;
        List<Schedule> lstSchedule = new ArrayList<Schedule>();
        Schedule entity;
        lstSchedule = scheduleDA.findAll();
        Date timeNow = new Date();
        if (startTime.after(endTime)) {
            flag = false;
        }
        if (startTime.before(timeNow) || endTime.before(timeNow)) {
            flag = false;
        }
        for (int count = 0; count < lstSchedule.size(); count++) {
            entity = lstSchedule.get(count);
            if (startTime.after(entity.getStartedTime()) && startTime.before(entity.getEndedTime())) {
                flag = false;
                break;
            }
            if (startTime.after(entity.getStartedTime()) && endTime.before(entity.getEndedTime())) {
                flag = false;
                break;
            }
            if (startTime.before(entity.getStartedTime()) && endTime.before(entity.getEndedTime())) {
                flag = false;
                break;
            }
        }
        if (startTime.after(endTime)) {
            flag = false;
        } else if (true) {
        }
        return flag;
    }

    public List<Schedule> getSchedules(Date date) {
        //return ScheduleDA.findRelatively("startedTime", date);
        return scheduleDA.searchSchedule(date);
    }

    public List<Schedule> getSchedules(Date date, int status) {
        return scheduleDA.findAbsolutely(new String[]{"startedTime, status"},
                new Object[]{date, status}, null, null, -1, -1);
    }

    private String generateID() {
        FindResult<Schedule> schedules = scheduleDA.findAbsolutely(null, null, new String[]{"scheduleID"}, new String[]{"DESC"}, 0, 1);
        String newID = "A";
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