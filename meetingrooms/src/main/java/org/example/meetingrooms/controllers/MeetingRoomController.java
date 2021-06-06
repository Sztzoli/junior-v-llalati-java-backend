package org.example.meetingrooms.controllers;

import org.example.meetingrooms.domain.MeetingRoom;
import org.example.meetingrooms.repositories.inmemory.InMemoryMeetingRoomRepository;
import org.example.meetingrooms.services.MeetingRoomService;
import org.example.meetingrooms.services.MeetingRoomServiceImpl;
import org.example.meetingrooms.utility.InputData;

import java.util.Scanner;

public class MeetingRoomController {

    private final MeetingRoomService meetingRoomService = new MeetingRoomServiceImpl(new InMemoryMeetingRoomRepository());

    private final Scanner scanner = new Scanner(System.in);
    private final InputData inputData = new InputData();

    public static final String MENU = """ 
            0. Tárgyaló rögzítése
            1. Tárgyalók névsorrendben
            2. Tárgyalók név alapján visszafele sorrendben
            3. Minden második tárgyaló
            4. Területek
            5. Keresés pontos név alapján
            6. Keresés névtöredék alapján
            7. Keresés terület alapján""";


    public static void main(String[] args) {
        MeetingRoomController controller = new MeetingRoomController();
        controller.initData();
        controller.start();


    }

    public void initData() {
        MeetingRoom meetingRoom1 = MeetingRoom.builder().name("AAA").length(1).width(1).build();
        MeetingRoom meetingRoom2 = MeetingRoom.builder().name("ÉÉÉ").length(2).width(2).build();
        MeetingRoom meetingRoom3 = MeetingRoom.builder().name("ZZZ").length(3).width(3).build();
        meetingRoomService.save(meetingRoom1);
        meetingRoomService.save(meetingRoom2);
        meetingRoomService.save(meetingRoom3);
    }

    public void start() {
        System.out.println(MENU);
        int menuPoint = inputData.getDataFromUser("adj meg a menü pontot"
                , () -> Integer.parseInt(scanner.nextLine()));
        switch (menuPoint) {
            case 0:
                saveMeetingRoom();
                break;
            case 1:
                System.out.println(meetingRoomService.findAllSortedByName());
                start();
                break;
            case 2:
                System.out.println(meetingRoomService.findAllSortedByNameReverse());
                start();
                break;
            case 3:
                System.out.println(meetingRoomService.findEverySecondSortedByName());
                start();
                break;
            case 4:
                System.out.println(meetingRoomService.findAllSortedByArea());
                start();
                break;
            case 5:
                searchByName();
                break;
            case 6:
                searchByNamePrefix();
                break;
            case 7:
                searchByArea();
                break;
            default:
                break;
        }

    }

    private void searchByArea() {
        int area = inputData.getDataFromUser("adj meg a területet"
                , () -> Integer.parseInt(scanner.nextLine()));
        System.out.println(meetingRoomService.findBiggerAreaThen(area));
        start();
    }

    private void searchByNamePrefix() {
        String name = inputData.getDataFromUser("Tárgyaló neve", () -> scanner.nextLine());
        System.out.println(meetingRoomService.findByNamePrefix(name));
        start();
    }

    private void searchByName() {
        String name = inputData.getDataFromUser("Tárgyaló neve", () -> scanner.nextLine());
        System.out.println(meetingRoomService.findByName(name));
        start();
    }

    private void saveMeetingRoom() {
        String name = inputData.getDataFromUser("Tárgyaló neve", () -> scanner.nextLine());
        int width = inputData.getDataFromUser("Tárgyaló szélessége", () -> Integer.parseInt(scanner.nextLine()));
        int length = inputData.getDataFromUser("Tárgyaló hosszúsága", () -> Integer.parseInt(scanner.nextLine()));
        MeetingRoom meetingRoom = MeetingRoom.builder().name(name).width(width).length(length).build();
        meetingRoomService.save(meetingRoom);
        start();
    }


}
