/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;
import rps.business.DepartmentService;
import rps.business.VacancyService;
import rps.entities.Department;
import rps.entities.Vacancy;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class VacancyMB {

    private Vacancy vacancy;
    private String vacancyID;
    private Department department;
    private List<Vacancy> lstVacancy;

    public String search() {
        vacancyService = new VacancyService();
        lstVacancy = vacancyService.getListVacancyByID(vacancyID);
        return null;
    }

    public List<Vacancy> getLstVacancy() {
        return lstVacancy;
    }

    public void setLstVacancy(List<Vacancy> lstVacancy) {
        this.lstVacancy = lstVacancy;
    }

    public VacancyService getVacancyService() {
        return vacancyService;
    }

    public void setVacancyService(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    public Department getDepartment() {
        if (department == null) {
            department = new Department();
        }
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Vacancy getVacancy() {
        if (vacancy == null) {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String paramID = request.getParameter("id");
            if (paramID == null || paramID.equals("")) {
                vacancy = new Vacancy();

            } else {
                try {
                    vacancy = vacancyService.getDetailVacancy(paramID);
                } catch (Exception ex) {
                    FacesMessage message = new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Vacancy not found!",
                            "Vacancy not found!");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    vacancy = new Vacancy();
                }
            }
        }
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }
    private VacancyService vacancyService;
    private DepartmentService departmentService;

    /** Creates a new instance of VacancyMB */
    public VacancyMB() {
        vacancyService = new VacancyService();
        departmentService = new DepartmentService();
    }

    public String searchVacancy() {
        list = vacancyService.searchVacancyByTitle(vacancy.getTitle());
        return "search.xhtml";
    }
    private List<Vacancy> list;

    public List<Vacancy> getList() {
        return list;
    }

    public void setList(List<Vacancy> list) {
        this.list = list;
    }

    public String insertVacancy() {
        if (!validateInput()) {
            return null;
        }
        vacancyService = new VacancyService();
        vacancyService.beginTransaction();
        if (department == null) {
            department = new Department();
        }

        department = departmentService.getDeparmentById(id);
        vacancyService.insertVacancy(vacancy.getTitle(), department, vacancy.getNumberRequirement(),
                vacancy.getPosition(), vacancy.getWorkingPlace(), vacancy.getWorkType(),
                vacancy.getMinimumSalary(), vacancy.getMaximumSalary(), vacancy.getDescription(),
                vacancy.getSkillRequirement(), vacancy.getEntitlement(), vacancy.getMinimumAge(), vacancy.getMaximumAge(),
                vacancy.getGender(), vacancy.getDegree(), vacancy.getYearOfExperience(), vacancy.getProbationaryPeriod(), vacancy.getDeadline());
        vacancyService.commitTransaction();
        return null;
    }

    public List<Department> getDepartments() {
        return departmentService.getDepartments();
    }
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void validateNumberRequirement(FacesContext context, UIComponent component, Object obj) throws ValidatorException {
        UIInput uIInput = (UIInput) component;
        if (uIInput.isRequired() && uIInput.isValid()) {
            int numberValue = (Integer) obj;
            if (numberValue <= 0) {
                FacesMessage message = new FacesMessage(
                        FacesMessage.SEVERITY_WARN,
                        "Number Requirement is invalid",
                        "Number Requirement is invalid");
                throw new ValidatorException(message);
            }
        }
    }

    public void validateYearOfExperience(FacesContext context, UIComponent component, Object obj) throws ValidatorException {
        UIInput uIInput = (UIInput) component;
        if (uIInput.isValid()) {
            int number = (Integer) obj;
            if (number < 0) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Number Requirement is validate", "Number Requirement must a integer number");
                throw new ValidatorException(message);
            }
        }
    }

    public void validateMinSalary(FacesContext context, UIComponent component, Object obj) throws ValidatorException {
        UIInput iInput = (UIInput) component;
        if (iInput.isValid()) {
            Double number = (Double) obj;
            if (number < 0) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Number is invalid", "Number is invalid");
                throw new ValidatorException(message);
            }
        }
    }

    public void validateMaxSalary(FacesContext context, UIComponent component, Object obj) throws ValidatorException {
        UIInput iInput = (UIInput) component;
        if (iInput.isValid()) {
            Double number = (Double) obj;
            if (number < 0) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Number is invalid", "Number is invalid");
                throw new ValidatorException(message);
            }
        }
    }

    private boolean validateInput() {
        boolean result = true;
        FacesContext context = FacesContext.getCurrentInstance();
        if (vacancy.getMaximumSalary() < vacancy.getMinimumSalary()) {
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_WARN,
                    "Number is greater than minimum salary",
                    "Number is greater than minimum salary");
            context.addMessage("msgMaximumSalary", message);
            result = false;
        }
        return result;
    }

    public void validateMinAge(FacesContext context, UIComponent component, Object object) throws ValidatorException {
        UIInput uIInput = (UIInput) component;
        if (uIInput.isValid()) {
            int number = (Integer) object;
            if (number < 0) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Number is invalid", "Number is invalid");
                throw new ValidatorException(message);
            }
        }
    }

    public void validateMaxAge(FacesContext context, UIComponent component, Object object) throws ValidatorException {
        UIInput iInput = (UIInput) component;
        if (iInput.isValid()) {
            int number = (Integer) object;
            if (number < 0) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Number is invalid", "Number is invalid");
                throw new ValidatorException(message);
            }
        }
    }

    public String editRedirect() {
        return "vacancy.xhtml?faces-redirect=true&id=" + this.getVacancy().getVacancyID();
    }

    public String editVacancy() {
        if (!validateInput()) {
            return null;
        }
        vacancyService = new VacancyService();
        vacancyService.beginTransaction();
        department = departmentService.getDeparmentById(id);
        vacancyService.editVacancy(vacancy.getVacancyID(), vacancy.getTitle(), department,
                vacancy.getNumberRequirement(),vacancy.getStatus(),vacancy.getPosition(),
                vacancy.getWorkingPlace(),vacancy.getWorkType(),vacancy.getMinimumSalary(),
                vacancy.getMaximumSalary(),vacancy.getDescription(),vacancy.getSkillRequirement(),
                vacancy.getEntitlement(),vacancy.getMinimumAge(),vacancy.getMaximumAge(),
                vacancy.getGender(),vacancy.getDegree(),vacancy.getYearOfExperience(),
                vacancy.getProbationaryPeriod(),vacancy.getDeadline(),vacancy.getCreatedDate());
        vacancyService.commitTransaction();
        return null;
    }
}
