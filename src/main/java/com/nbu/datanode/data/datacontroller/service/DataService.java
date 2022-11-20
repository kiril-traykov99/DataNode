package com.nbu.datanode.data.datacontroller.service;

import java.util.HashMap;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.nbu.datanode.data.datacontroller.rest.Results;
import com.nbu.datanode.data.datacontroller.rest.Results.Result;
import com.nbu.datanode.data.datacontroller.rest.Results.SuccessWithPayload;

@Service
public class DataService {

    HashMap<String, DataEntry> localDataSet = new HashMap<>();

    public DataService() {}

    public Result handleReadRequest(String key) {
        if (!validateKey(key)) {
            return Results.InvalidKey;
        }
        if (localDataSet.containsKey(key)) {
            return new SuccessWithPayload<>(localDataSet.get(key));
        }
        return Results.KeyNotFound;
    }

    public Result handleWriteRequest(String key, HashMap<String, Object> data) {
        if (!validateKey(key)) {
            return Results.InvalidKey;
        }
        DataEntry dataEntry = new DataEntry(data);
        if (localDataSet.containsKey(key)) {
            localDataSet.put(key, dataEntry);
            return Results.Updated;
        }
        localDataSet.put(key, dataEntry);
        return Results.Added;
    }

    private boolean validateKey(String key) {
        return key != null && !key.equals("");
    }

    public Result handleDeleteRequest(String key) {
        if (!validateKey(key)) {
            return Results.InvalidKey;
        }
        if (localDataSet.containsKey(key)) {
            localDataSet.remove(key);
            return Results.Deleted;
        }
        return Results.KeyNotFound;
    }

    public int getTotalNumberOfKeys() {
        return localDataSet.size();
    }
}
