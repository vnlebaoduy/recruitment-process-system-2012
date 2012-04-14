/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.business;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import rps.dataaccess.ApplicantDA;
import rps.dataaccess.FindResult;
import rps.entities.Applicant;
import rps.entities.Schedule;
import rps.entities.Vacancy;

/**
 *
 * @author user
 */
public class ApplicantService extends AbstractService {

    private ApplicantDA applicantDA;

    public ApplicantService() {
        applicantDA = new ApplicantDA(getEntityManager());
    }

    public Applicant addApplicant(String firstName, String lastName, String email) {
        Applicant applicant = new Applicant();
        applicant.setApplicantID(generateID());
        applicant.setFirstName(firstName);
        applicant.setLastName(lastName);
        applicant.setEmail(email);

        applicantDA.create(applicant);

        return applicant;
    }

    public Applicant addApplicant(String firstName, String lastName, boolean gender, Date dob,
            String phone, String email, String address, Double salary, String language, int year,
            String degree, String skill, String award, List<Vacancy> vacancy, List<Schedule> schedule) {

        Applicant applicant = new Applicant();
        applicant.setApplicantID(generateID());
        applicant.setFirstName(firstName);
        applicant.setLastName(lastName);
        applicant.setGender(gender);
        applicant.setDob(dob);
        applicant.setPhoneNumber(phone);
        applicant.setEmail(email);
        applicant.setAddress(address);
        applicant.setSalaryRequirement(salary);
        applicant.setLanguage(language);
        applicant.setYearOfExperience(year);
        applicant.setDegree(degree);
        applicant.setSkill(skill);
        applicant.setAward(award);
        applicant.setVacancyList(vacancy);
        applicant.setScheduleList(schedule);
        applicant.setStatus(0);
        applicant.setCreatedDate(Calendar.getInstance().getTime());

        applicantDA.create(applicant);

        return applicant;
    }

    private String generateID() {
        FindResult<Applicant> applicants = applicantDA.findAbsolutely(null, null, new String[]{"applicantID"}, new String[]{"DESC"}, 0, 1);
        String newID = "A";
        if (applicants.size() > 0) {
            Applicant applicant = applicants.get(0);
            String aId = applicant.getApplicantID();
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

    public void attachVacancy(String id, List<Vacancy> vacancies) {
        Applicant applicant = applicantDA.find(id);
        applicant.setVacancyList(vacancies);
        applicantDA.edit(applicant);
    }

    public boolean isEmailExisted(String email) {
        FindResult result = applicantDA.findAbsolutely("email", email);
        if (result.size() > 0) {
            return true;
        }
        return false;
    }

    public List<Applicant> getListApplicant() {
        return applicantDA.findAll();
    }

    public List<Applicant> getListApplicantByID(String idAppl) {
        if (idAppl.equals("")) {
            return applicantDA.findAll();
        }
        return applicantDA.findAbsolutely("applicantID", idAppl);
    }
}
