/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.business;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author user
 */
public class PageGroup implements Serializable {

    private Page page;
    private List<Page> pages;

    public PageGroup() {
    }

    public PageGroup(Page page, List<Page> pages) {
        this.page = page;
        this.pages = pages;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }
}
