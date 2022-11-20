package com.nbu.datanode.data.datacontroller.service;

import java.util.HashMap;
import java.util.Objects;

import com.google.common.base.MoreObjects;

public class DataEntry {

    private HashMap<String, Object> jsonData;

    public DataEntry(HashMap<String, Object> jsonData) {
        this.jsonData = jsonData;
    }

    public HashMap<String, Object> getJsonData() {
        return jsonData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataEntry dataEntry = (DataEntry) o;
        return Objects.equals(jsonData, dataEntry.jsonData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jsonData);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("jsonData", jsonData)
                .toString();
    }
}
