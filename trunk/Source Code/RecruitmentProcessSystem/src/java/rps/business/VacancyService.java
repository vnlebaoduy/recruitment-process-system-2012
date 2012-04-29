/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import rps.dataaccess.FindResult;
import rps.dataaccess.VacancyDA;
import rps.entities.Applicant;
import rps.entities.Department;
import rps.entities.Interview;
import rps.entities.Vacancy;

/**
 *
 * @author user
 */
public class VacancyService extends AbstractService {

    private VacancyDA vacancyDA;

    public VacancyService() {
        vacancyDA = new VacancyDA(getEntityManager());
    }

    public List<Vacancy> searchVacancyByTitle(String title) {
        return vacancyDA.findAbsolutely("title", title);
    }

    public Vacancy insertVacancy(String title, Department deparment, int numberRequirement,
            String position, String workingPlace, String workType, double minimumSalary, double maximumSalary, String description, String skillRequirement,
            String entitlement, int minimumAge, int maximumAge, boolean gender, String degree, int yearOfExperience, String probationaryPeriod,
            Date deadline) {
        Vacancy objEntity = new Vacancy();
        objEntity.setVacancyID(generateID());
        objEntity.setTitle(title);
        objEntity.setDepartment(deparment);
        objEntity.setStatus(0);
        objEntity.setNumberRequirement(numberRequirement);
        objEntity.setPosition(position);
        objEntity.setWorkingPlace(workingPlace);
        objEntity.setWorkType(workType);
        objEntity.setMinimumSalary(minimumSalary);
        objEntity.setMaximumSalary(maximumSalary);
        objEntity.setDescription(description);
        objEntity.setSkillRequirement(skillRequirement);
        objEntity.setEntitlement(entitlement);
        objEntity.setMinimumAge(minimumAge);
        objEntity.setMaximumAge(maximumAge);
        objEntity.setGender(gender);
        objEntity.setDegree(degree);
        objEntity.setYearOfExperience(yearOfExperience);
        objEntity.setProbationaryPeriod(probationaryPeriod);
        objEntity.setDeadline(deadline);
        objEntity.setCreatedDate(Calendar.getInstance().getTime());
        vacancyDA.create(objEntity);
        return objEntity;
    }

    private String generateID() {
        FindResult<Vacancy> vacancies = vacancyDA.findAbsolutely(null, null, new String[]{"vacancyID"}, new String[]{"DESC"}, 0, 1);
        String newID = "V";
        if (vacancies.size() > 0) {
            Vacancy vacancy = vacancies.get(0);
            String aId = vacancy.getVacancyID();
            String number = aId.substring(1, aId.length());
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

    public List<Vacancy> getAvailableVacancies() {
        return vacancyDA.findAbsolutely("status", 0);
    }

    public List<Vacancy> getAvailableVacancies(Applicant applicant) {
        List<Vacancy> listAttached = getAttachedVacancies(applicant);
        List<Vacancy> listTotal = vacancyDA.findAbsolutely("status", 0);
        List<Vacancy> listAvailable = new ArrayList<Vacancy>();
        if (listAttached == null || listAttached.isEmpty()) {
            return listTotal;
        }
        for (Vacancy vacancy : listTotal) {
            if (!listAttached.contains(vacancy)) {
                listAvailable.add(vacancy);
            }
        }
        return listAvailable;
    }

    public List<Vacancy> getAttachedVacancies(Applicant applicant) {
        InterviewService service = new InterviewService();
        List<Vacancy> list = new ArrayList<Vacancy>();
        list = service.getVacancies(applicant, 99);
        List<Vacancy> listAttached = service.getVacancies(applicant, 0);
        for (Vacancy vacancy : listAttached) {
            if (!listAttached.isEmpty()) {
                list.add(vacancy);
            }
        }
        return list;
    }

    public List<Applicant> getApplicantHired(String vacancyID) {
        List<Applicant> list = new ArrayList<Applicant>();
        Vacancy vacancy = vacancyDA.find(vacancyID);
        if (vacancy != null && !vacancy.getInterviewList().isEmpty()) {
            for (Interview interview : vacancy.getInterviewList()) {
                if (interview.getAVStatus() == 100
                        && interview.getApplicant().getStatus() == 1) {//Applicant hired - status = 1
                    list.add(interview.getApplicant());
                }
            }
        }
        return list;
    }

    public Vacancy getDetailVacancy(String id) {
        return vacancyDA.find(id);
    }

    public List<Vacancy> getListVacancyByID(String idVacancy) {
        if (idVacancy.equals("")) {
            return vacancyDA.findAll();
        }
        return vacancyDA.findAbsolutely("vacancyID", idVacancy);
    }

    public List<Vacancy> getAll() {
        return vacancyDA.findAll();
    }

    public Vacancy editVacancy(String id, String title, Department deparment, int numberRequirement, int status,
            String position, String workingPlace, String workType, double minimumSalary, double maximumSalary,
            String description, String skillRequirement, String entitlement,
            int minimumAge, int maximumAge, boolean gender, String degree, int yearOfExperience, String probationaryPeriod,
            Date deadline, Date createDate) {
        Vacancy objEntity = getDetailVacancy(id);
        objEntity.setTitle(title);
        objEntity.setDepartment(deparment);
        objEntity.setStatus(status);
        objEntity.setNumberRequirement(numberRequirement);
        objEntity.setPosition(position);
        objEntity.setWorkingPlace(workingPlace);
        objEntity.setWorkType(workType);
        objEntity.setMinimumSalary(minimumSalary);
        objEntity.setMaximumSalary(maximumSalary);
        objEntity.setDescription(description);
        objEntity.setSkillRequirement(skillRequirement);
        objEntity.setEntitlement(entitlement);
        objEntity.setMinimumAge(minimumAge);
        objEntity.setMaximumAge(maximumAge);
        objEntity.setGender(gender);
        objEntity.setDegree(degree);
        objEntity.setYearOfExperience(yearOfExperience);
        objEntity.setProbationaryPeriod(probationaryPeriod);
        objEntity.setDeadline(deadline);
        objEntity.setCreatedDate(createDate);
        vacancyDA.edit(objEntity);
        return objEntity;
    }

    public List<Vacancy> searchVacancy(String keyword, int status) {
        List<Vacancy> list = vacancyDA.search(keyword, status);
        if (list == null) {
            list = new ArrayList<Vacancy>();
        }
        return list;
    }

    public boolean isClosedVacancy(Interview interview) throws Exception {
        List<Applicant> list = getApplicantHired(interview.getVacancy().getVacancyID());
        if (list.size() == interview.getVacancy().getNumberRequirement()) {
            beginTransaction();
            interview.getVacancy().setStatus(1);
            vacancyDA.edit(interview.getVacancy());
            commitTransaction();

            InterviewService interviewService = new InterviewService();
            ApplicantService applicantService = new ApplicantService();
            List<Interview> removeList = interviewService.getInterviews(
                    interview.getVacancy(), 99);
            for (Interview i : removeList) {
                i.setStatus(1);
                i.setAVStatus(-100);
                interviewService.beginTransaction();
                interviewService.updateInterview(i.getInterviewID(),
                        i.getEmployee(), i.getVacancy(), i.getApplicant(),
                        i.getStartedTime(), i.getEndedTime(), i.getStatus(), i.getAVStatus());
                interviewService.commitTransaction();
                applicantService.checkAvailabeInterviews(i.getApplicant());
            }
            return true;
        }
        return false;
    }
}
