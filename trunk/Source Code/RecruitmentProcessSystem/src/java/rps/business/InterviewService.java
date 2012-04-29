/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rps.dataaccess.FindResult;
import rps.dataaccess.InterviewDA;
import rps.entities.Applicant;
import rps.entities.Employee;
import rps.entities.Interview;
import rps.entities.Vacancy;

/**
 *
 * @author Bach Luong
 */
public class InterviewService extends AbstractService {

    private InterviewDA interviewDA;

    public InterviewService() {
        interviewDA = new InterviewDA(getEntityManager());
    }

    public Interview addInterview(Applicant applicant, Vacancy vacancy) {
        Interview interview = new Interview();
        interview.setInterviewID(generateID());
        interview.setApplicant(applicant);
        interview.setVacancy(vacancy);
        interview.setStatus(0);
        interview.setAVStatus(0);
        interviewDA.create(interview);
        return interview;
    }

    public Interview addInterview(Employee employee, Applicant applicant,
            Vacancy vacancy, Date startedTime, Date endedTime) {
        Interview interview = new Interview();
        interview.setInterviewID(generateID());
        interview.setApplicant(applicant);
        interview.setVacancy(vacancy);
        interview.setEmployee(employee);
        interview.setEndedTime(endedTime);
        interview.setStartedTime(startedTime);
        interviewDA.create(interview);
        return interview;
    }

    public List<Interview> getInterviews(Date date) {
        long interval = 24 * 1000 * 60 * 60; // Time 1 day - miliseconds
        Date endedDate = new Date(date.getTime() + interval);
        return interviewDA.searchInterview(null, date, endedDate, -1);
    }

    public List<Interview> getInterviews(Employee employee, Date date) {
        long interval = 24 * 1000 * 60 * 60; // Time 1 day - miliseconds
        Date endedDate = new Date(date.getTime() + interval);
        return interviewDA.searchInterview(employee, date, endedDate, -1);
    }

    public List<Interview> getInterviews(Date startedDate, Date endedDate) {
        return interviewDA.searchInterview(null, startedDate, endedDate, -1);
    }

    public List<Interview> getEventInterviews(Employee employee,
            Date startedDate, Date endedDate) {
        return interviewDA.searchInterview(employee, startedDate, endedDate, -1);
    }

    public List<Interview> getInterviews(Date startedDate, Date endedDate, int status) {
        return interviewDA.searchInterview(null, startedDate, endedDate, status);
    }

    public List<Interview> getInterviews(Employee employee,
            Date startedDate, Date endedDate, int status) {
        return interviewDA.searchInterview(employee, startedDate, endedDate, status);
    }

    public List<Interview> getInterviews(Employee employee,
            Date startedDate, Date endedDate) {
        return interviewDA.searchInterview(employee, null, startedDate, endedDate);
    }

    public List<Interview> getInterviews(Applicant applicant,
            Date startedDate, Date endedDate) {
        return interviewDA.searchInterview(null, applicant, startedDate, endedDate);
    }

    public List<Interview> getInterviews(Date date, int status) {
        long interval = 24 * 1000 * 60 * 60; // Time 1 day - miliseconds
        Date endedDate = new Date(date.getTime() + interval);
        return interviewDA.searchInterview(null, date, endedDate, status);
    }

    public List<Interview> getInterviews(Employee employee, Date date, int status) {
        long interval = 24 * 1000 * 60 * 60; // Time 1 day - miliseconds
        Date endedDate = new Date(date.getTime() + interval);
        return interviewDA.searchInterview(employee, date, endedDate, status);
    }

    public List<Interview> getInterviews(Employee employee, int status) {
        return interviewDA.findAbsolutely(new String[]{"employee", "status"},
                new Object[]{employee, status}, null, null, -1, -1);
    }

    public List<Interview> getInterviews(Vacancy vacancy) {
        return interviewDA.findAbsolutely("vacancy", vacancy);
    }

    public List<Interview> getInterviews(Applicant applicant) {
        return interviewDA.findAbsolutely("applicant", applicant);
    }

    public List<Interview> getInterviews(Vacancy vacancy, int status) {
        return interviewDA.findAbsolutely(new String[]{"vacancy", "aVStatus"},
                new Object[]{vacancy, status}, new String[]{"startedTime"},
                new String[]{"ASC"}, -1, -1);
    }

    public Interview getInterviews(Applicant applicant, Vacancy vacancy,
            int status, int aVStatus) {
        List<Interview> list = interviewDA.findAbsolutely(
                new String[]{"applicant", "vacancy", "status", "aVStatus"},
                new Object[]{applicant, vacancy, status, aVStatus},
                null, null, -1, -1);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public Interview getInterviews(Applicant applicant, Vacancy vacancy,
            int status) {
        List<Interview> list = interviewDA.findAbsolutely(
                new String[]{"applicant", "vacancy", "status"},
                new Object[]{applicant, vacancy, status},
                null, null, -1, -1);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public Interview getInterviews(Applicant applicant, Vacancy vacancy) {
        List<Interview> list = interviewDA.findAbsolutely(
                new String[]{"applicant", "vacancy"},
                new Object[]{applicant, vacancy},
                null, null, -1, -1);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public List<Interview> getInterviewsLate(Applicant applicant, Date endTime) {
        return interviewDA.searchInterview(applicant, endTime);
    }

    public List<Interview> getCurrentInterviews(Employee employee) {
        return interviewDA.getNotRemoveInterview(employee, 1);
    }

    public List<Interview> getRejectedInterviews() {
        return interviewDA.searchInterview(null, null, null, -100);
    }

    public Interview getInterview(String id) {
        try {
            return interviewDA.find(id);
        } catch (Exception ex) {
            return null;
        }
    }

    public Interview getInterviewNotRemove(Applicant applicant, Vacancy vacancy,
            int aVStatus) {
        List<Interview> list = interviewDA.searchInterviewNotRemove(vacancy, applicant, aVStatus);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public List<Applicant> getApplicantsByAVStatus(int avStatus) {
        List<Interview> interviews = getInterviewsByAVStatus(avStatus);
        List<Applicant> applicants = new ArrayList<Applicant>();
        for (Interview interview : interviews) {
            if (!applicants.contains(interview.getApplicant())) {
                applicants.add(interview.getApplicant());
            }
        }
        return applicants;
    }

    private List<Interview> getInterviewsByAVStatus(int avStatus) {
        return interviewDA.findAbsolutely("aVStatus", avStatus);
    }

    public List<Vacancy> getVacancies(Applicant applicant) {
        List<Vacancy> vacancies = new ArrayList<Vacancy>();
        List<Interview> interviews = interviewDA.findAbsolutely(
                new String[]{"applicant", "aVStatus"}, new Object[]{applicant, 0},
                null, null, -1, -1);
        if (!interviews.isEmpty()) {
            for (Interview interview : interviews) {
                vacancies.add(interview.getVacancy());
            }
        }
        return vacancies;
    }

    public List<Vacancy> getVacancies(Applicant applicant, int aVStatus) {
        List<Vacancy> vacancies = new ArrayList<Vacancy>();
        List<Interview> interviews = interviewDA.findAbsolutely(
                new String[]{"applicant", "aVStatus"}, new Object[]{applicant, aVStatus},
                null, null, -1, -1);
        if (!interviews.isEmpty()) {
            for (Interview interview : interviews) {
                vacancies.add(interview.getVacancy());
            }
        }
        return vacancies;
    }

    public List<Interview> getInterviews(Applicant applicant, int status) {
        return interviewDA.findAbsolutely(
                new String[]{"applicant", "status"},
                new Object[]{applicant, status},
                null, null, -1, -1);
    }

    public List<Interview> getAvailableInterviews(Applicant applicant, int aVStatus) {
        return interviewDA.findAbsolutely(
                new String[]{"applicant", "aVStatus"},
                new Object[]{applicant, aVStatus},
                null, null, -1, -1);
    }

    public Interview updateInterview(String id, Employee employee, Vacancy vacancy,
            Applicant applicant, Date startedTime, Date endedTime, int status,
            int avStatus)
            throws Exception {
        Interview interview = getInterview(id);
        if (interview == null) {
            throw new Exception("Interview not found.");
        }
        interview.setEmployee(employee);
        interview.setApplicant(applicant);
        interview.setVacancy(vacancy);
        interview.setStartedTime(startedTime);
        interview.setEndedTime(endedTime);
        interview.setStatus(status);
        interview.setAVStatus(avStatus);
        interviewDA.edit(interview);
        return interview;
    }

    public void removeInterview(Interview interview) {
        interviewDA.remove(interview);
    }

    public void reviewInterview(Interview interview) throws Exception {
        List<Interview> list = getAvailableInterviews(interview.getApplicant(), 99);
        if (list != null && !list.isEmpty()) {
            if (interview.getAVStatus() == 100) {
                for (Interview i : list) {
                    beginTransaction();
                    updateInterview(i.getInterviewID(),
                            i.getEmployee(), i.getVacancy(), i.getApplicant(),
                            i.getStartedTime(), i.getEndedTime(), 1, 1);
                    commitTransaction();
                }
            }
        }
    }

    private String generateID() {
        FindResult<Interview> interviews = interviewDA.findAbsolutely(null, null, new String[]{"interviewID"}, new String[]{"DESC"}, 0, 1);
        String newID = "I";
        if (interviews.size() > 0) {
            Interview interview = interviews.get(0);
            String sId = interview.getInterviewID();
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
