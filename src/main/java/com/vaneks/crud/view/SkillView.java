package com.vaneks.crud.view;

import com.vaneks.crud.controller.SkillController;
import com.vaneks.crud.model.Skill;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class SkillView {
    SkillController skillController = new SkillController();
    private Scanner scanner = new Scanner(System.in);
    private List<Skill> skillList;
    private Skill skill;

    public void view() {
        System.out.println("Выберите действие, и нажмите Enter: \n" +
                "1 - Вывести все Skills на экран \n" +
                "2 - Найти Skill по его ID \n" +
                "3 - Обновить данные Skill по его ID \n" +
                "4 - Удалить Skill по его ID \n" +
                "5 - Добавить новый Skill \n");
        String select = scanner.nextLine();
        switch (select) {
            case "1":
                getAll();
                break;
            case "2":
                getById();
                break;
            case "3":
                update();
                break;
            case "4":
                delete();
                break;
            case "5":
                save();
                break;
        }
    }

    private void getAll() {
        skillList = skillController.ControllerAll();
        for (Skill skill : skillList) System.out.println(skill.toString());
    }

    private void getById() {
        System.out.println("Введите Skill's ID");
        String id = scanner.nextLine();
        skill = skillController.controllerId(id);
        System.out.println(skill.toString());
    }

    private void update() {
        System.out.println("Введите ID");
        String id = scanner.nextLine();
        System.out.println("Введите Название");
        String name = scanner.nextLine();
        skill = skillController.controllerUpdate(id, name);
        System.out.println(skill.toString());
    }

    private void delete() {
        System.out.println("Введите Skill's ID");
        String id = scanner.nextLine();
        skillController.controllerDeleteById(id);
        System.out.println("Skill успешно удален");
    }

    private void save() {
        System.out.println("Введите Название Skill");
        String name = scanner.nextLine();
        skill = skillController.controllerSave(name);
        System.out.println(skill.toString());
    }
}
