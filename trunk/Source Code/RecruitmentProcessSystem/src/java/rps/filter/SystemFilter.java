/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.filter;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import rps.business.AccountService;
import rps.entities.Account;

/**
 *
 * @author user
 */
@WebFilter(filterName = "SystemFilter", urlPatterns = {"*.xhtml"})
public class SystemFilter implements Filter {

    private FilterConfig filterConfig;
    private String requestURL;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public SystemFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        try {
            this.request = (HttpServletRequest) request;
            this.response = (HttpServletResponse) response;
            this.requestURL = getRequest().getRequestURL().toString();
            HttpSession session = getRequest().getSession();
            if (!getRequestURL().contains("login.xhtml") && !getRequestURL().contains("error.xhtml")) {
                if (session.getAttribute("account") == null) {
                    getResponse().sendRedirect("login.xhtml");
                    return;
                } else {
                    boolean access = accessPage(session);
                    if (!access) {
                        getResponse().sendRedirect("error.xhtml");
                        //getResponse().sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        return;
                    }
                }
            } else if (getRequestURL().contains("login.xhtml")) {
                if (session.getAttribute("account") != null) {
                    getResponse().sendRedirect("info.xhtml");
                    return;
                }
            }
            chain.doFilter(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void destroy() {
    }

    private boolean accessPage(HttpSession session) {
        String[] hrPages = {"applicant-info.xhtml", "applicant.xhtml",
            "applicants.xhtml", "attach.xhtml",
            "error.xhtml", "info.xhtml", "interview.xhtml", "interviews.xhtml", "login.xhtml",
            "manage-interview.xhtml", "processing-interview.xhtml", "vacancy.xhtml",
            "vacancies.xhtml", "vacancy-info.xhtml"};
        String[] interviewerPages = {"applicant-info.xhtml", "applicants.xhtml",
            "error.xhtml", "info.xhtml",
            "interviews.xhtml", "login.xhtml", "processing-interview.xhtml",
            "vacancies.xhtml", "vacancy-info.xhtml"};
        Account account = (Account) session.getAttribute("account");
        AccountService accountService = new AccountService();
        String page = getRequestURL().substring(getRequestURL().lastIndexOf("/") + 1);
        if (!Arrays.asList(hrPages).contains(page) && !Arrays.asList(interviewerPages).contains(page)) {
            return true;
        }
        boolean access = false;
        if (accountService.isHRGroup(account.getUserName())) {
            for (String p : hrPages) {
                if (getRequestURL().contains(page)) {
                    access = true;
                    break;
                }
            }
        } else if (accountService.isInterviewer(account.getUserName())) {
            for (String p : interviewerPages) {
                if (getRequestURL().contains(page)) {
                    access = true;
                    break;
                }
            }
        }
        return access;
    }
}
