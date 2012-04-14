/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rps.dataaccess.SchedulingInterviewDA;
import rps.entities.SchedulingInterview;
import rps.entities.SchedulingInterviewPK;

/**
 *
 * @author Bach Luong
 */
public class SchedulingInterviewService extends AbstractService {

    private SchedulingInterviewDA schedulingInterviewDA;

    public SchedulingInterviewService() {
        schedulingInterviewDA = new SchedulingInterviewDA(getEntityManager());
    }

    public SchedulingInterview addSchedulingInterview(SchedulingInterview entity) {
        SchedulingInterview addEntity = new SchedulingInterview();
        addEntity.setApplicant(entity.getApplicant());
        addEntity.setEmployee(entity.getEmployee());
        addEntity.setEndedTime(entity.getEndedTime());
        addEntity.setStartedTime(entity.getStartedTime());
        addEntity.setSchedulingInterviewPK(new SchedulingInterviewPK(entity.getEmployee().getEmployeeID(), entity.getApplicant().getApplicantID()));
        addEntity.setStatus(0);
        schedulingInterviewDA.create(addEntity);
        return addEntity;
    }
    public Boolean checkHour(Date startTime, Date endTime) {
        Boolean flag = true;
        List<SchedulingInterview> lstSchedulingInterview = new ArrayList<SchedulingInterview>();
        SchedulingInterview entity;
        lstSchedulingInterview = schedulingInterviewDA.findAll();
        Date timeNow = new Date();
        if (startTime.after(endTime)) {
            flag = false;
        }
        if(startTime.before(timeNow)||endTime.before(timeNow)){
             flag = false;
        }
        for (int count = 0; count < lstSchedulingInterview.size(); count++) {
            entity = lstSchedulingInterview.get(count);
            if (startTime.after(entity.getStartedTime()) && startTime.before(entity.getEndedTime())) {
                flag = false;
                break;
            }
            if(startTime.after(entity.getStartedTime())&&endTime.before(entity.getEndedTime())){
                flag = false;
                break;
            }
            if(startTime.before(entity.getStartedTime())&&endTime.before(entity.getEndedTime())){
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

    public List<SchedulingInterview> getSchedulingInterviews(Date date){
        //return schedulingInterviewDA.findRelatively("startedTime", date);
        return schedulingInterviewDA.searchSchedule(date);
    }

    public List<SchedulingInterview> getSchedulingInterviews(Date date, int status){
        return schedulingInterviewDA.findAbsolutely(new String[]{"startedTime, status"},
                new Object[]{date, status}, null, null, -1, -1);
    }
}
