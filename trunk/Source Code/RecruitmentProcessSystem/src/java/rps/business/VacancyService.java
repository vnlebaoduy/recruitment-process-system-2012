/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.business;

import java.util.Date;
import java.util.List;
import rps.dataaccess.FindResult;
import rps.dataaccess.VacancyDA;
import rps.entities.Applicant;
import rps.entities.Department;
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

    public Vacancy insertVacancy(String title, Department deparment, Date createdDate, int status, int numberRequirement,
            String position, String workingPlace, String workType, double minimumSalary, double maximumSalary, String description, String skillRequirement,
            String entitlement, int minimumAge, int maximumAge, boolean gender, String degree, int yearOfExperience, String probationaryPeriod,
            Date deadline, List<Applicant> applicantList) {
        Vacancy objEntity = new Vacancy();
        objEntity.setVacancyID(generateID());
        objEntity.setTitle(title);
        objEntity.setDepartment(deparment);
        objEntity.setCreatedDate(createdDate);
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
        objEntity.setApplicantList(applicantList);
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

    public Vacancy getVacancyByStatus(){
        int status =1;
        Vacancy objVacancy = new Vacancy();
        List<Vacancy> list = vacancyDA.findAll();
        status = objVacancy.getStatus();
        if(status!=1){
             return (Vacancy) list;
        }
        return null;
    }
}
