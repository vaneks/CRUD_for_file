package com.vaneks.crud.controller;

import com.vaneks.crud.model.Skill;
import com.vaneks.crud.repository.SkillRepository;
import com.vaneks.crud.repository.io.JavaIOSkillRepositoryImpl;

import java.io.IOException;
import java.util.List;

public class SkillController {
    SkillRepository controllerSkill = new JavaIOSkillRepositoryImpl();

    public List <Skill> ControllerAll() {
        return controllerSkill.getAll();
    }

    public Skill controllerId(String id) {
        return controllerSkill.getById(strToLong(id));
    }

    public Skill controllerUpdate(String id, String name){
        try {
            return controllerSkill.update(new Skill(strToLong(id), name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void controllerDeleteById(String name) {
        try {
            controllerSkill.deleteById(strToLong(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Skill controllerSave(String name){
        try {
            return controllerSkill.save(new Skill(null, name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private Long strToLong(String string) {
        return Long.parseLong(string);
    }
}
