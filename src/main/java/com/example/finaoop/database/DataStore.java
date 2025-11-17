package com.example.finaoop.database;

import com.example.finaoop.models.DisposalRequest;
import com.example.finaoop.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataStore {

    private static final String USERS_FILE = "users.dat";
    private static final String REQUESTS_FILE = "requests.dat";

    private static List<User> users = new ArrayList<>();
    private static List<DisposalRequest> requests = new ArrayList<>();
    private static final ObservableList<DisposalRequest> observableRequests = FXCollections.observableArrayList();

    private static User currentUser = null;

    static {
        loadData();
    }

    public static void addUser(User user) {
        users.add(user);
        saveData();
    }

    public static boolean emailExists(String email) {
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) return true;
        }
        return false;
    }

    public static User findUser(String email, String password) {
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void updateUserProfilePicture(String email, String imagePath) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                user.setProfilePicturePath(imagePath);
                saveData();
                return;
            }
        }
    }


    public static void addRequest(DisposalRequest request) {
        requests.add(0, request);
        observableRequests.add(0, request);
        saveData();
    }

    public static ObservableList<DisposalRequest> getAllRequests() {
        return observableRequests;
    }

    public static ObservableList<DisposalRequest> getRequestsForCurrentUser() {
        if (currentUser == null) {
            return FXCollections.observableArrayList();
        }
        List<DisposalRequest> userRequests = requests.stream()
                .filter(r -> r.getRequesterEmail().equalsIgnoreCase(currentUser.getEmail()))
                .collect(Collectors.toList());
        return FXCollections.observableArrayList(userRequests);
    }

    public static void updateRequestStatus(String requestID, String newStatus) {
        for (int i = 0; i < requests.size(); i++) {
            DisposalRequest req = requests.get(i);
            if (req.getId().equals(requestID)) {
                req.setStatus(newStatus);
                observableRequests.set(i, req);
                saveData();
                return;
            }
        }
    }


    public static int totalReports() {
        return requests.size();
    }

    public static long activeRequests() {
        return requests.stream()
                .filter(r -> "Pending".equals(r.getStatus()) || "In Progress".equals(r.getStatus()))
                .count();
    }

    public static long completedTasks() {
        return requests.stream()
                .filter(r -> "Completed".equals(r.getStatus()))
                .count();
    }


    private static void saveData() {
        try (ObjectOutputStream oosUsers = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oosUsers.writeObject(new ArrayList<>(users));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectOutputStream oosRequests = new ObjectOutputStream(new FileOutputStream(REQUESTS_FILE))) {
            oosRequests.writeObject(new ArrayList<>(requests));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadData() {
        try (ObjectInputStream oisUsers = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            users = (List<User>) oisUsers.readObject();
        } catch (IOException | ClassNotFoundException e) {
            users = new ArrayList<>();
        }

        try (ObjectInputStream oisRequests = new ObjectInputStream(new FileInputStream(REQUESTS_FILE))) {
            requests = (List<DisposalRequest>) oisRequests.readObject();
        } catch (IOException | ClassNotFoundException e) {
            requests = new ArrayList<>();
        }

        observableRequests.setAll(requests);
    }

    public static Image loadImage(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return null;
        }
        try {
            if (imagePath.startsWith("file:")) {
                return new Image(imagePath);
            } else {
                return new Image(new File(imagePath).toURI().toString());
            }
        } catch (Exception e) {
            System.err.println("Could not load image: " + imagePath);
            return null;
        }
    }
}