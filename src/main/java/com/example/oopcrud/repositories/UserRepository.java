package com.example.oopcrud.repositories;

import com.example.oopcrud.models.User;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class UserRepository {
    private static final String FILE_PATH = "users.txt";
    private static final AtomicInteger idGenerator = new AtomicInteger(1);
    public UserRepository() {
        // Create the file if it doesn't exist
        File file = new File(FILE_PATH);
        try {
            if (!file.exists()) {
                file.createNewFile(); // Empty file for initial use
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = parseUser(line);
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public Optional<User> findById(int id) {
        return findAll().stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

    public void save(User user) {
        if (user.getId() == 0) {
            user.setId(idGenerator.getAndIncrement());
        }

        List<User> users = findAll();
        users.removeIf(u -> u.getId() == user.getId());
        users.add(user);
        writeAll(users);
    }

    public void deleteById(int id) {
        List<User> users = findAll();
        users.removeIf(u -> u.getId() == id);
        writeAll(users);
    }

    private void writeAll(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                writer.write(formatUser(user));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatUser(User user) {
        return user.getId() + "|" + user.getFullName() + "|" + user.getEmail() + "|" + user.getPassword() + "|" + user.getRole() + "|" + user.getCreatedAt();
    }


    private User parseUser(String line) {
        String[] parts = line.split("\\|");
        User user = new User();
        user.setId(Integer.parseInt(parts[0]));
        user.setFullName(parts[1]);
        user.setEmail(parts[2]);
        user.setPassword(parts[3]);
        user.setRole(parts[4]);
        // createdAt is set via BaseEntity, not manually updated
        return user;
    }
}
