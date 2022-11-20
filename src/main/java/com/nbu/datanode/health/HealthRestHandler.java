package com.nbu.datanode.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class HealthRestHandler {

    final DataNodeHealthService dataNodeHealthService;

    public HealthRestHandler(DataNodeHealthService dataNodeHealthService) {
        this.dataNodeHealthService = dataNodeHealthService;
    }

    @RequestMapping("/health")
    public HealthStatus getDataNodeHealth() {
        System.out.println("health rquest");
        return dataNodeHealthService.handleHealthRequest();
    }

}
