package com.vaneks.crud.view;

import com.vaneks.crud.controller.DeveloperController;
import com.vaneks.crud.model.Developer;
import com.vaneks.crud.model.Skill;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class DeveloperView {
    DeveloperController developerController = new DeveloperController();
    private Scanner scanner = new Scanner(System.in);
    private List<Developer> developerList;
    private Developer developer;

    public void view() {
        System.out.println("Выберите действие, и нажмите Enter: \n" +
                "1 - Вывести всех разработчиков на экран \n" +
                "2 - Найти разработчика по его ID \n" +
                "3 - Обновить данные разработчика по его ID \n" +
                "4 - Удалить разработчика по его ID \n" +
                "5 - Добавить нового разработчика \n");
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
        developerList = developerController.ControllerAll();
        for (Developer developer: developerList) System.out.println(developer.toString());
    }

    private void getById() {
        System.out.println("Введите Developer's ID");
        String id = scanner.nextLine();
        developer = developerController.controllerId(id);
        System.out.println(developer.toString());
    }

    private void update() {
        System.out.println("Введите Developer's ID");
        String id = scanner.nextLine();
        System.out.println("Введите FirstName");
        String firstname = scanner.nextLine();
        System.out.println("Введите LastName");
        String lastname = scanner.nextLine();
        System.out.println("Введите Skill's Name через 'Пробел");
        String skills = scanner.nextLine();
        String[] idSkills = skills.split(" ");
        developer = developerController.controllerUpdate(id, firstname, lastname, idSkills);
        System.out.println(developer.toString());
    }

    private void delete() {
        System.out.println("Введите Developer's ID");
        String id = scanner.nextLine();
        developerController.controllerDeleteById(id);
        System.out.println("Developer успешно удален");
    }

    private void save() {
        System.out.println("Введите FirstName");
        String firstname = scanner.nextLine();
        System.out.println("Введите LastName");
        String lastname = scanner.nextLine();
        System.out.println("Введите Skill's Name через 'Пробел'");
        String skills = scanner.nextLine();
        String[] idSkills = skills.split(" ");
        developer = developerController.controllerSave( firstname, lastname, idSkills);
        System.out.println(developer.toString());
    }
}
