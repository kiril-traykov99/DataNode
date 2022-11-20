package com.nbu.datanode.health;

import java.util.Objects;

public class HealthStatus {

    private final int memoryUsage;
    private final int numberOfKeys;

    public HealthStatus(int memoryUsage, int numberOfKeys) {
        this.memoryUsage = memoryUsage;
        this.numberOfKeys = numberOfKeys;
    }

    public int getMemoryUsage() {
        return memoryUsage;
    }

    public int getNumberOfKeys() {
        return numberOfKeys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HealthStatus that = (HealthStatus) o;
        return memoryUsage == that.memoryUsage && numberOfKeys == that.numberOfKeys;
    }

    @Override
    public int hashCode() {
        return Objects.hash(memoryUsage, numberOfKeys);
    }

    @Override
    public String toString() {
        return com.google.common.base.MoreObjects.toStringHelper(this)
                .add("memoryUsage", memoryUsage)
                .add("numberOfKeys", numberOfKeys)
                .toString();
    }
}
