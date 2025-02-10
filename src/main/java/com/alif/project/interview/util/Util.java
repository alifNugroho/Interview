package com.alif.project.interview.util;

import com.alif.project.interview.exceptions.TaskException;

public class Util {

    public static String statusTask(Integer taskStatus) {
        String statusTask = Constant.NEW_TASK;
        if(taskStatus == 1) {
            statusTask = Constant.COMPLETED_TASK;
        }

        return statusTask;
    }

    public static String checkRequest(String input, String name) throws TaskException {
        String result = "";
        if(input == null) {
            throw new TaskException(name+" Cannot be Null");
        }
        else {
            result = input;
        }
        return result;
    }
}
