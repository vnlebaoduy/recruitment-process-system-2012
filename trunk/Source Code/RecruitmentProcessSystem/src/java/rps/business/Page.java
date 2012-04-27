/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.business;

import java.io.Serializable;
import java.util.List;
import rps.entities.Role;

/**
 *
 * @author user
 */
public class Page implements Serializable {

    private String title;
    private String url;
    private boolean current;
    private String image;
    private String role;

    public Page(String title, String url, boolean current, String image, String role) {
        this.title = title;
        this.url = url;
        this.current = current;
        this.image = image;
        this.role = role;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
