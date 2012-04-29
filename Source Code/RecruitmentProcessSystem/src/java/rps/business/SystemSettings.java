/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.business;

import java.util.ArrayList;
import java.util.List;
import rps.entities.Account;

/**
 *
 * @author user
 */
public class SystemSettings {

    public SystemSettings() {
    }
    
    private List<PageGroup> listPages;

    public List<PageGroup> getListPages() {
        if (listPages == null) {
            listPages = new ArrayList<PageGroup>();
        }
        return listPages;
    }

    public void InitSideBar(Account account) {
        listPages = null;
        List<Page> pages = new ArrayList<Page>();
        Page page = new Page("Dashboard", "#", false, "", "hr");
        PageGroup pageGroup = new PageGroup(page, pages);
        getListPages().add(pageGroup);

        pages = new ArrayList<Page>();
        page = new Page("Create an new applicant", "applicant.xhtml", false, "", "hr");
        pages.add(page);
        page = new Page("Attach vacancies", "attach.xhtml", false, "", "hr");
        pages.add(page);
        page = new Page("List of applicants", "applicants.xhtml", false, "", "all");
        pages.add(page);
        page = new Page("Manage Applicants", "#", false, "", "all");
        pageGroup = new PageGroup(page, pages);
        getListPages().add(pageGroup);

        pages = new ArrayList<Page>();
        page = new Page("Create a new interview", "interview.xhtml", false, "", "hr");
        pages.add(page);
        page = new Page("Schedule of interviews", "interviews.xhtml", false, "", "all");
        pages.add(page);
        page = new Page("List of interviews", "manage-interview.xhtml", false, "", "all");
        pages.add(page);
        page = new Page("Manage Interviews", "#", false, "", "all");
        pageGroup = new PageGroup(page, pages);
        getListPages().add(pageGroup);

        pages = new ArrayList<Page>();
        page = new Page("Create a new vacancy", "vacancy.xhtml", false, "", "hr");
        pages.add(page);
        page = new Page("List of vacancies", "vacancies.xhtml", false, "", "all");
        pages.add(page);
        page = new Page("Manage Vacancies", "#", false, "", "all");
        pageGroup = new PageGroup(page, pages);
        getListPages().add(pageGroup);

        pages = new ArrayList<Page>();
        page = new Page("Your Profile", "info.xhtml", false, "", "all");
        pages.add(page);
        page = new Page("Users and Permissions", "#", false, "", "all");
        pages.add(page);
        page = new Page("Settings", "#", false, "", "all");
        pageGroup = new PageGroup(page, pages);
        getListPages().add(pageGroup);

        boolean hr = false;
        boolean interviewer = false;
        AccountService accountService = new AccountService();
        interviewer = accountService.isInterviewer(account.getUserName());
        hr = accountService.isHRGroup(account.getUserName());
        String remove = "";
        if (hr && !interviewer) {
            remove = "interviewer";
        } else if (!hr && interviewer) {
            remove = "hr";
        }
        if (!remove.equals("")) {
            List<PageGroup> listPagesTemp = new ArrayList<PageGroup>();
            for (PageGroup group : getListPages()) {
                PageGroup groupTemp = new PageGroup();
                if (group.getPage().getRole().equalsIgnoreCase(remove)) {
                    continue;
                }
                groupTemp.setPage(group.getPage());
                groupTemp.setPages(new ArrayList<Page>());
                for (Page p : group.getPages()) {
                    if (!p.getRole().equalsIgnoreCase(remove)) {
                        groupTemp.getPages().add(p);
                    }
                }
                listPagesTemp.add(groupTemp);
            }
            listPages = listPagesTemp;
        }
    }
    private List<PageGroup> listMenu;

    public List<PageGroup> getListMenu() {
        if (listMenu == null) {
            listMenu = new ArrayList<PageGroup>();
        }
        return listMenu;
    }

    public void InitMenuBar(Account account) {
        listMenu = null;
        List<Page> pages = new ArrayList<Page>();
        Page page = new Page("Create Vacancy", "vacancy.xhtml", false, "resources/images/other/add_job_img.png", "hr");
        pages.add(page);
        page = new Page("Create Applicant", "applicant.xhtml", false, "resources/images/other/add_app_img.png", "hr");
        pages.add(page);
        page = new Page("Attach Vacancies", "attach.xhtml", false, "resources/images/other/attach_job_img.png", "hr");
        pages.add(page);
        page = new Page("Create Interview", "interview.xhtml", false, "resources/images/other/add_interview_img.png", "hr");
        pages.add(page);
        page = new Page("Schedule Interviews", "interviews.xhtml", false, "resources/images/other/interview_img.png", "all");
        pages.add(page);
        page = new Page("Review", "manage-interview.xhtml", false, "resources/images/other/review_img.png", "interviewer");
        pages.add(page);
        page = new Page("", "#", false, "", "all");
        PageGroup pageGroup = new PageGroup(page, pages);
        getListMenu().add(pageGroup);

        boolean hr = false;
        boolean interviewer = false;
        AccountService accountService = new AccountService();
        interviewer = accountService.isInterviewer(account.getUserName());
        hr = accountService.isHRGroup(account.getUserName());
        String remove = "";
        if (hr && !interviewer) {
            remove = "interviewer";
        } else if (!hr && interviewer) {
            remove = "hr";
        }
        if (!remove.equals("")) {
            List<PageGroup> listPagesTemp = new ArrayList<PageGroup>();
            for (PageGroup group : getListMenu()) {
                PageGroup groupTemp = new PageGroup();
                if (group.getPage().getRole().equalsIgnoreCase(remove)) {
                    break;
                }
                groupTemp.setPage(group.getPage());
                groupTemp.setPages(new ArrayList<Page>());
                for (Page p : group.getPages()) {
                    if (!p.getRole().equalsIgnoreCase(remove)) {
                        groupTemp.getPages().add(p);
                    }
                }
                listPagesTemp.add(groupTemp);
            }
            listMenu = listPagesTemp;
        }
    }
}
