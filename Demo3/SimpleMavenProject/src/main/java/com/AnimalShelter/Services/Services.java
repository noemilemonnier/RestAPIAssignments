package com.AnimalShelter.Services;

public class Services {
    private static Services ourInstance = new Services();

    public static Services getInstance() {
        return ourInstance;
    }

    private Services() {
    }

    private CatService catService = new CatService();
    private DogService dogService = new DogService();
    private UserService userService = new UserService();

    public CatService getCatService() {
        return catService;
    }

    public DogService getDogService() {
        return dogService;
    }

    public Service getUserService() {
        return userService;
    }
}
