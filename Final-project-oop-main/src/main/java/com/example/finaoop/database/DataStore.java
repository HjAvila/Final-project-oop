package com.example.finaoop.database;

import com.example.finaoop.models.DisposalRequest;
import com.example.finaoop.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataStore {

    private static final String USERS_FILE = "users.dat";
    private static final String REQUESTS_FILE = "requests.dat";

    private static List<User> users = new ArrayList<>();
    private static List<DisposalRequest> requests = new ArrayList<>();

    private static User currentUser = null; // Track who is logged in

    // This block runs when the app starts, loading the databases
    static {
        loadData();
    }

    // --- User Methods ---

    public static void addUser(User user) {
        users.add(user);
        saveData(); // Save after adding
    }

    public static boolean emailExists(String email) {
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) return true;
        }
        return false;
    }

    /**
     * Finds a user by email and password.
     * @return The found User object, or null if login is invalid.
     */
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


    // --- Request Methods ---

    public static void addRequest(DisposalRequest request) {
        requests.add(0, request); // Add to the front of the list
        saveData();
    }

    /**
     * Gets all requests from all users.
     * @return ObservableList of all requests.
     */
    public static ObservableList<DisposalRequest> getAllRequests() {
        return FXCollections.observableArrayList(requests);
    }

    /**
     * Gets only the requests for the currently logged-in user.
     * @return ObservableList of requests filtered by the current user.
     */
    public static ObservableList<DisposalRequest> getRequestsForCurrentUser() {
        if (currentUser == null) {
            return FXCollections.observableArrayList(); // Return empty list if no one is logged in
        }
        List<DisposalRequest> userRequests = requests.stream()
                .filter(r -> r.getRequesterEmail().equalsIgnoreCase(currentUser.getEmail()))
                .collect(Collectors.toList());
        return FXCollections.observableArrayList(userRequests);
    }


    // --- Statistics Methods ---

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


    // --- Save/Load Data (Persistence) ---

    private static void saveData() {
        // Save users
        try (ObjectOutputStream oosUsers = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oosUsers.writeObject(new ArrayList<>(users)); // Save a copy
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save requests
        try (ObjectOutputStream oosRequests = new ObjectOutputStream(new FileOutputStream(REQUESTS_FILE))) {
            oosRequests.writeObject(new ArrayList<>(requests)); // Save a copy
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadData() {
        // Load users
        try (ObjectInputStream oisUsers = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            users = (List<User>) oisUsers.readObject();
        } catch (IOException | ClassNotFoundException e) {
            users = new ArrayList<>(); // File not found or empty, start new list
        }

        // Load requests
        try (ObjectInputStream oisRequests = new ObjectInputStream(new FileInputStream(REQUESTS_FILE))) {
            requests = (List<DisposalRequest>) oisRequests.readObject();
        } catch (IOException | ClassNotFoundException e) {
            requests = new ArrayList<>(); // File not found or empty, start new list
        }
    }
}