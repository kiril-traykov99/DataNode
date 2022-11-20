package com.nbu.datanode.data.datacontroller;

import java.util.HashMap;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nbu.datanode.data.datacontroller.rest.Results;
import com.nbu.datanode.data.datacontroller.rest.Results.Failure;
import com.nbu.datanode.data.datacontroller.rest.Results.Result;
import com.nbu.datanode.data.datacontroller.rest.Results.Success;
import com.nbu.datanode.data.datacontroller.rest.Results.SuccessWithPayload;
import com.nbu.datanode.data.datacontroller.service.DataEntry;
import com.nbu.datanode.data.datacontroller.service.DataService;

@RestController
    @RequestMapping("/v1/api")
    public class DataController {

    final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @RequestMapping(value = "/data/{key}", method = RequestMethod.GET)
        public ResponseEntity<DataEntry> requestData(@PathVariable String key) {
        Result result =  dataService.handleReadRequest(key);
        if (result instanceof Failure failure) {
            return handleFailure(failure);
        } else {
            SuccessWithPayload<DataEntry> success = (SuccessWithPayload<DataEntry>) result;
            DataEntry dataEntry = success.getPayload();
            return new ResponseEntity<>(dataEntry, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/data/{key}", method = RequestMethod.POST)
    public ResponseEntity<DataEntry> persistData(@PathVariable String key, @RequestBody HashMap<String, Object> data) {
        Result result = dataService.handleWriteRequest(key, data);
        if (result instanceof Success success) {
            if (Results.Updated.equals(success)) {
                return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.CREATED);
            }
        }
        return handleFailure((Failure) result);
    }

    @RequestMapping(value = "/data/{key}", method = RequestMethod.DELETE)
    public ResponseEntity<DataEntry> deleteData(@PathVariable String key) {
        Result result = dataService.handleDeleteRequest(key);
        if (result instanceof Failure failure) {
            return handleFailure(failure);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    private ResponseEntity<DataEntry> handleFailure(Failure failure) {
        if (Results.KeyAlreadyPresent.equals(failure)) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } else if (Results.KeyNotFound.equals(failure)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        else if (Results.InvalidKey.equals(failure)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
