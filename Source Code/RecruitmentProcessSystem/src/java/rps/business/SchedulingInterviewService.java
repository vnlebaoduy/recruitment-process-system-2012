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
        for (int count = 0; count < lstSchedulingInterview.size(); count++) {
            entity = lstSchedulingInterview.get(count);
            if (entity.getStartedTime().equals(startTime)) {
                flag = false;
            }
            if (entity.getEndedTime().after(startTime)) {
                flag = false;
            }
        }
        if (startTime.before(endTime)) {
            flag = false;
        } else if (true) {
        }
        return flag;
    }
}
