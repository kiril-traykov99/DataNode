package com.nbu.datanode.health;

import org.springframework.stereotype.Service;

import com.nbu.datanode.data.datacontroller.service.DataService;

@Service
public class DataNodeHealthService {

    final
    DataService dataService;

    public DataNodeHealthService(DataService dataService) {
        this.dataService = dataService;
    }

    public HealthStatus handleHealthRequest() {
        return new HealthStatus(getMemoryUsage(), dataService.getTotalNumberOfKeys());
    }

    private int getMemoryUsage() {
        try {
            long memoryInBytes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            return (int) (memoryInBytes / (1024 * 1024));
        } catch (Exception e) {
            return 0;
        }
    }
}
