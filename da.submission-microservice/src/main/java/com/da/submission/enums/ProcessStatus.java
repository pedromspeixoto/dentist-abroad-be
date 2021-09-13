package com.da.submission.enums;

public enum ProcessStatus {

PENDING_ASSIGNMENT, UNDER_VALIDATION, VALIDATED, PENDING_FRENCH_APPROVAL, PROCESS_APPROVED, READY_TO_WORK, WORKING;

    public String getProcessStatus() {
        return name();
    }

}