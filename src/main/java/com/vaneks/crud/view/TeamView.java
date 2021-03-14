package com.vaneks.crud.view;

import com.vaneks.crud.controller.TeamController;
import com.vaneks.crud.model.Developer;
import com.vaneks.crud.model.Team;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class TeamView {
    TeamController teamController = new TeamController();
    private Scanner scanner = new Scanner(System.in);
    private List<Team> teamList;
    private Team team;

    public void view() {
        System.out.println("Выберите действие, и нажмите Enter: \n" +
                "1 - Вывести все команды на экран \n" +
                "2 - Найти команду по его ID \n" +
                "3 - Обновить данные команды по ее ID \n" +
                "4 - Пометить команду как 'DELETED' по его ID \n" +
                "5 - Удалить все команды со статусом'DELETED' по его ID \n" +
                "6 - Добавить новую команду \n");
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
                deleteAll();
                break;
            case "6":
                save();
                break;
        }
    }

    private void getAll() {
        teamList = teamController.ControllerAll();
        for (Team team: teamList) System.out.println(team.toString());
    }

    private void getById() {
        System.out.println("Введите Team's ID");
        String id = scanner.nextLine();
        team = teamController.controllerId(id);
        System.out.println(team.toString());
    }

    private void update() {
        System.out.println("Введите Team's ID");
        String id = scanner.nextLine();
        System.out.println("Введите Developer's ID через 'Пробел");
        String developers = scanner.nextLine();
        String[] idDevelopers = developers.split(" ");
        team = teamController.controllerUpdate(id, idDevelopers);
        System.out.println(team.toString());
    }

    private void delete() {
        System.out.println("Введите Team's ID");
        String id = scanner.nextLine();
        teamController.controllerDeleteById(id);
        System.out.println("Установлен статус 'DELETED'");
    }
    private void deleteAll() {
        teamController.controllerDeleteAll();
        System.out.println("Teams со статусом 'DELETED' успешно удалены");
    }
    private void save() {
        System.out.println("Введите Имя Команды");
        String name = scanner.nextLine();
        System.out.println("Введите Developers's ID через 'Пробел");
        String developer = scanner.nextLine();
        String[] idDevelopers = developer.split(" ");
        team = teamController.controllerSave( name, idDevelopers);
        System.out.println(team.toString());
    }
}
