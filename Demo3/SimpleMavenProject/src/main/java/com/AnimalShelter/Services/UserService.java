package com.AnimalShelter.Services;

import com.AnimalShelter.Models.User;

public class UserService extends Service<User> {

    public UserService() {
        User user1 = new User(1, "admin", "admin@example.com", "admin");
        user1.getRole().add("user");
        user1.getRole().add("admin");
        list.add(user1);

        User user2 = new User(2, "user", "user@example.com", "user");
        user2.getRole().add("user");
        list.add(user2);
    }

    public boolean userCredentialExists(String username, String password) {
        User user = getByName(username);
        return user.checkPassword(password);
    }
}
