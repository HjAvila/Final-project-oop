package com.example.finaoop.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RequestsStore {
    private static final List<Request> requests = Collections.synchronizedList(new ArrayList<>());

    public static void add(Request r) { requests.add(r); }
    public static List<Request> all() { return new ArrayList<>(requests); }
    public static int count() { return requests.size(); }

    public static class Request {
        private final String id, location, type, description, date, status;
        public Request(String id, String location, String type, String description, String date, String status) {
            this.id = id; this.location = location; this.type = type; this.description = description; this.date = date; this.status = status;
        }
        public String getId() { return id; }
        public String getLocation() { return location; }
        public String getType() { return type; }
        public String getDescription() { return description; }
        public String getDate() { return date; }
        public String getStatus() { return status; }
    }
}
