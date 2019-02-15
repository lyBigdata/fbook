package com.ouer.fbook.db.bean;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

public class BaseDbBean implements Serializable {

    @DatabaseField(useGetSet = true, generatedId = true, columnName = "id")
    Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
