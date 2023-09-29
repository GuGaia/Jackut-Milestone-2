package br.ufal.ic.p2.jackut.models;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Community {

    private String name;
    private String description;
    private String manager;
    private ArrayList<String> members;

    @JsonCreator
    public Community(@JsonProperty("name") String name, @JsonProperty("description")String description, @JsonProperty("manager")String manager){
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.members = new ArrayList<>();
    }
    public String getDescription() {
        return description;
    }
    public String getManager() {
        return manager;
    }
    public ArrayList<String> getMembers() {
        return members;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setManager(String manager) {
        this.manager = manager;
    }
    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }
    public void addMember(User user){
        members.add(user.getLogin());
    }
}
