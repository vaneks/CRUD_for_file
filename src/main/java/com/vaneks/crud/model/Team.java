package com.vaneks.crud.model;

import java.util.List;


public class Team {
    private Long id;
    private String name;
    private List<Developer> developers;

    public enum TeamStatus {
        ACTIVE, UNDER_REVIEW, DELETED
    }
    private TeamStatus teamStatus;

    public  Team(){};

    public Team(TeamStatus teamStatus, Long id, String name, List<Developer> developers) {
        this.id = id;
        this.name = name;
        this.developers = developers;
        this.teamStatus = teamStatus;
    }
    public TeamStatus getTeamStatus() {
        return teamStatus;
    }

    public void setTeamStatus(TeamStatus teamStatus) {
        this.teamStatus = teamStatus;
    }
    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return teamStatus + " " + id + " " + name + " " + developers;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }
}
