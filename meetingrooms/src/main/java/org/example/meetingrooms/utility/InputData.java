package org.example.meetingrooms.utility;

import java.util.function.Supplier;

public class InputData {

    public <T> T getDataFromUser(String message, Supplier<T> supplier) {
        System.out.println(message);
        return supplier.get();
    }
}
