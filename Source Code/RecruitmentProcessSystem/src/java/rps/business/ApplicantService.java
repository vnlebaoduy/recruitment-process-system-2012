/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import rps.dataaccess.ApplicantDA;
import rps.dataaccess.FindResult;
import rps.entities.Applicant;
import rps.entities.Interview;
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

    public Applicant addApplicant(String firstName, String lastName, boolean gender, Date dob,
            String phone, String email, String address, Double salary, String language, int year,
            String degree, String skill, String award, List<Interview> interviews) {
        Applicant applicant = setAttributes(generateID(), firstName, lastName, gender,
                dob, phone, email, address, salary, language, year, degree,
                skill, award, interviews, 0, Calendar.getInstance().getTime());

        applicantDA.create(applicant);
        return applicant;
    }

    public Applicant updateApplicant(String id, String firstName, String lastName,
            boolean gender, Date dob, String phone, String email, String address,
            Double salary, String language, int year, String degree, String skill,
            String award, List<Interview> interviews, Date createdDate, int status) {

        Applicant applicant = setAttributes(id, firstName, lastName, gender,
                dob, phone, email, address, salary, language, year, degree,
                skill, award, interviews, status, createdDate);

        applicantDA.edit(applicant);
        return applicant;
    }

    private Applicant setAttributes(String id, String firstName, String lastName,
            boolean gender, Date dob, String phone, String email, String address,
            Double salary, String language, int year, String degree, String skill,
            String award, List<Interview> interviews, int status, Date createdDate) {
        Applicant applicant = new Applicant();
        applicant.setApplicantID(id);
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
        applicant.setInterviewList(interviews);
        applicant.setStatus(status);
        applicant.setCreatedDate(createdDate);
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

    public Applicant getApplicant(String id) {
        return applicantDA.find(id);
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

    public List<Applicant> getApplicantByStatus(int status) {
        return applicantDA.findAbsolutely("status", status);
    }

    public List<Applicant> getApplicantsToAttach() {
        return applicantDA.findRelatively(new String[]{"status", "status"},
                new Object[]{99, 0}, null, null, -1, -1);
    }

    public List<Interview> attachVacancies(Applicant applicant, List<Vacancy> list) {
        List<Interview> interviews = new ArrayList<Interview>();
        if (!list.isEmpty()) {
            InterviewService interviewService = new InterviewService();
            for (Vacancy vacancy : list) {
                interviewService.beginTransaction();
                Interview interview = interviewService.addInterview(applicant, vacancy);
                interviewService.commitTransaction();
                interviews.add(interview);
            }
        }
        return interviews;
    }

    public void attachVacancies(Applicant applicant,
            List<Vacancy> oldList, List<Vacancy> newList) {
        if (oldList == null || oldList.isEmpty()) {
            attachVacancies(applicant, newList);
        } else {
            if (!newList.isEmpty()) {
                InterviewService interviewService = new InterviewService();
                // Check new vacancy
                for (Vacancy vacancy : newList) {
                    if (!oldList.contains(vacancy)) {
                        interviewService.beginTransaction();
                        interviewService.addInterview(applicant, vacancy);
                        interviewService.commitTransaction();
                    }
                }
                // Check remove vacancy
                for (Vacancy vacancy : oldList) {
                    if (!newList.contains(vacancy)) {
                        Interview interview = interviewService.getInterviews(applicant, vacancy, 0);
                        interviewService.beginTransaction();
                        interviewService.removeInterview(interview);
                        interviewService.commitTransaction();
                    }
                }
            }
        }
    }
    public List<Applicant> searchApplicant(String keyword, int status) {
        List<Applicant> list = applicantDA.search(keyword, status);
        if (list == null) {
            list = new ArrayList<Applicant>();
        }
        return list;
    }
}
