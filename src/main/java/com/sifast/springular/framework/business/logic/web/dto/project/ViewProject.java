package com.sifast.springular.framework.business.logic.web.dto.project;

public class ViewProject {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ViewProject{" +
                "id=" + id +
                '}';
    }
}
