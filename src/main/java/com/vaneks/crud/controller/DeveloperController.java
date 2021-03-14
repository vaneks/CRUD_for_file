package com.vaneks.crud.controller;

import com.vaneks.crud.model.Developer;
import com.vaneks.crud.model.Skill;
import com.vaneks.crud.repository.DeveloperRepository;
import com.vaneks.crud.repository.io.JavaIODeveloperRepositoryImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeveloperController {
    DeveloperRepository controllerDeveloper = new JavaIODeveloperRepositoryImpl();

    public List<Developer> ControllerAll() {
        return controllerDeveloper.getAll();
    }

    public Developer controllerId(String id) {
        return controllerDeveloper.getById(strToLong(id));
    }

    public Developer controllerUpdate(String id, String firstName, String lastName, String[] skills) {
        try {
            return controllerDeveloper.update(new Developer(strToLong(id), firstName, lastName, skillsCount(skills)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void controllerDeleteById(String id){
        try {
            controllerDeveloper.deleteById(strToLong(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Developer controllerSave(String firstName, String lastName, String[] skills){
        try {
            return controllerDeveloper.save(new Developer(null, firstName, lastName, skillsCount(skills)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Skill> skillsCount(String[] skills) {
        List<Skill> listSkills = new ArrayList<>();
        for(int i = 0; i < skills.length; i++) {
            listSkills.add(new Skill(null,skills[i]));
        }
        return listSkills;
    }
    private Long strToLong(String string) {
        return Long.parseLong(string);
    }
}
