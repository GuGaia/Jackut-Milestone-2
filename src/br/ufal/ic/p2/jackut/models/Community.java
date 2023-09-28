package br.ufal.ic.p2.jackut.models;

public class Community {

    private String name;
    private String description;
    private User manager;

    public Community(String name, String description, User manager){
        this.name = name;
        this.description = description;
        this.manager = manager;
    }
}
