package com.nbu.datanode.data.datacontroller.rest;

public final class Results {

    public sealed interface Result permits Success, Failure {
        boolean isSuccessful();
    }

    public sealed interface Success extends Result permits Added, Updated, Deleted, SuccessWithPayload {
        @Override
        default boolean isSuccessful() {
            return true;
        }
    }

    public sealed interface Failure extends Result permits KeyNotFound, KeyAlreadyPresent, ServiceError, InvalidKey {
        @Override
        default boolean isSuccessful() {
            return false;
        }

        String getMessage();
    }

    public static final Failure KeyNotFound = new KeyNotFound();
    public static final Failure KeyAlreadyPresent = new KeyAlreadyPresent();
    public static final Failure InvalidKey = new InvalidKey();
    public static final Failure SERVICE_ERROR = new ServiceError();

    public static final Success Deleted = new Deleted();
    public static final Success Added = new Added();
    public static final Success Updated = new Updated();

    public static final class KeyNotFound implements Failure {
        @Override
        public String getMessage() {
            return "Key not found";
        }
    }

    public static final class KeyAlreadyPresent implements Failure {
        @Override
        public String getMessage() {
            return "Key already present";
        }
    }


    public static final class InvalidKey implements Failure {
        @Override
        public String getMessage() {
            return "Key already present";
        }
    }

    private static final class ServiceError implements Failure {
        @Override
        public String getMessage() {
            return "Upstream error";
        }
    }

    private static final class Deleted implements Success {}

    private static final class Added implements Success {}

    private static final class Updated implements Success {}

    public static final class SuccessWithPayload <T> implements Success {
        private final T payload;

        public SuccessWithPayload(T payload) {
            this.payload = payload;
        }

        public T getPayload() {
            return payload;
        }
    }
}