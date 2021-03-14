package com.vaneks.crud.repository.io;

import com.vaneks.crud.model.Developer;
import com.vaneks.crud.model.Team;
import com.vaneks.crud.repository.TeamRepository;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class JavaIOTeamRepositoryImpl implements TeamRepository {
    private final String file =  "teams.txt";

    public List<Team> getAll() {return getAllInternal();}

    public Team getById(Long id) {
        return getAllInternal().stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }

    public Team update(Team team)  {
        List<Team> allteams = getAllInternal().stream()
                .peek(s -> {
                    if (s.getId().equals(team.getId())) {
                        s.setDevelopers(team.getDevelopers());
                        s.setTeamStatus(Team.TeamStatus.UNDER_REVIEW);
                    }
                })
                .collect(Collectors.toList());
        writeSkillToFile(allteams);
        return team;
    }

    public void deleteById(Long id) {
        List<Team> allteams = getAllInternal().stream()
                .peek(s -> {
                    if (s.getId().equals(id)) {
                        s.setTeamStatus(Team.TeamStatus.DELETED);
                    }
                })
                .collect(Collectors.toList());
        writeSkillToFile(allteams);
    }
    public void deleteAll() {
        List<Team> allTeams = getAllInternal();
        allTeams.removeIf(s -> s.getTeamStatus().equals(Team.TeamStatus.DELETED));
        writeSkillToFile(allTeams);
    }

    public Team save(Team teamToSave){
        List<Team> allTeam = getAllInternal();
        Team addId = new Team(teamToSave.getTeamStatus(),generateId(), teamToSave.getName(), teamToSave.getDevelopers());
        allTeam.add(addId);
        writeSkillToFile(allTeam);
        return addId;
    }

    private String idTeam(Team team) {
        JavaIODeveloperRepositoryImpl developer = new JavaIODeveloperRepositoryImpl();
        Developer id;
        List<Developer> teamDevelopers = team.getDevelopers();
        String str = "";
        for(Developer teamDeveloper : teamDevelopers) {
            if(developer.getByName(teamDeveloper.getFirstName(),teamDeveloper.getLastName()) == null) {
                id = developer.save(teamDeveloper);
            } else {
                id =  developer.getAll().stream().filter(s -> s.getFirstName().equals(teamDeveloper.getFirstName())).filter(s -> s.getLastName().equals(teamDeveloper.getLastName())).findFirst().orElse(null);
            }
            str += id.getId() + " ";

        }
        return str;
    }

    private String teamToString(Team team) {
        return team.getTeamStatus() + " " + team.getId() + " " + team.getName() + " " + idTeam(team);
    }

    private void writeSkillToFile(List<Team> teams) {
        BufferedWriter writerFile = null;
        try {
            writerFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        for (Team team : teams) {
            writerFile.write(teamToString(team));
            writerFile.newLine();
        }
        writerFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Team stringToTeam(String string) {
        String[] subStr = string.split(" ");
        List<Developer> developers = new ArrayList<>();
        JavaIODeveloperRepositoryImpl javaIODeveloperRepositoryImpl = new JavaIODeveloperRepositoryImpl();
        for(int i = 3; i < subStr.length; i++){
            developers.add(javaIODeveloperRepositoryImpl.getById(Long.parseLong(subStr[i])));
        }
        return new Team(Team.TeamStatus.valueOf(subStr[0] ), Long.parseLong(subStr[1]), subStr[2], developers);
    }

    private Long generateId() {
        return getAllInternal().stream().map(Team::getId).max(Comparator.comparing(aLong -> aLong)).orElse(0L) + 1;
    }

    private List<Team> getAllInternal() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            List<Team> teamList = new ArrayList<>();
            String strLine;
            while ((strLine = reader.readLine()) != null) {
                if (strLine.isEmpty()) {
                    break;
                }
                teamList.add(stringToTeam(strLine));
            }
            return teamList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
