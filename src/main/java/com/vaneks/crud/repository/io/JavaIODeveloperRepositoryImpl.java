package com.vaneks.crud.repository.io;

import com.vaneks.crud.model.Developer;
import com.vaneks.crud.model.Skill;
import com.vaneks.crud.repository.DeveloperRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class JavaIODeveloperRepositoryImpl implements DeveloperRepository {
    private final String file = "developers.txt";
    public List<Developer> getAll() {return getAllInternal();}

    public Developer getById(Long id) {
        return getAllInternal().stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }

    public Developer update(Developer developer) {
        List<Developer> allDevelopers = getAllInternal().stream()
                .peek(s -> {
                    if (s.getId().equals(developer.getId())) {
                        s.setFirstName(developer.getFirstName());
                        s.setLastName(developer.getLastName());
                        s.setSkills(developer.getSkills());
                    }
                 })
                .collect(Collectors.toList());
        writeSkillToFile(allDevelopers);
        return developer;
    }

    public void deleteById(Long id) {
        List<Developer> allDevelopers = getAllInternal();
        allDevelopers.removeIf(s -> s.getId().equals(id));
        writeSkillToFile(allDevelopers);
    }

    public Developer save(Developer developerToSave) {

        List<Developer> allDevelopers = getAllInternal();
        Developer addId = new Developer(generateId(), developerToSave.getFirstName(), developerToSave.getLastName(),
                developerToSave.getSkills());
        allDevelopers.add(addId);
        writeSkillToFile(allDevelopers);
        return addId;
    }
    private String idDeveloper(Developer developer) {
        JavaIOSkillRepositoryImpl skill = new JavaIOSkillRepositoryImpl();
        Skill id;
        List<Skill> developerSkills = developer.getSkills();
        String str = "";
        for (Skill developerSkill : developerSkills) {
            if (skill.getByName(developerSkill.getName()) == null) {
                id = skill.save(developerSkill);
            } else {
                id = skill.getAll().stream().filter(s -> s.getName().equals(developerSkill.getName())).findFirst().orElse(null);
            }
            str += id.getId() + " ";

        }
        return str;
    }

    private String developerToString(Developer developer) {
        return developer.getId() + " " + developer.getFirstName() + " " + developer.getLastName() + " " + idDeveloper(developer);
    }

    private void writeSkillToFile(List<Developer> developers)  {
        BufferedWriter writerFile = null;
        try {
            writerFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (Developer developer : developers) {
            if (writerFile != null) {
                try {
                    writerFile.write(developerToString(developer));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                writerFile.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                writerFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private Developer stringToDeveloper(String string) {
        String[] subStr = string.split(" ");
        List<Skill> devskills = new ArrayList<>();
        JavaIOSkillRepositoryImpl javaIOSkillRepositoryImpl = new JavaIOSkillRepositoryImpl();
        for(int i = 3; i < subStr.length; i++){
            devskills.add(javaIOSkillRepositoryImpl.getById(Long.parseLong(subStr[i])));
        }
        return new Developer(Long.parseLong(subStr[0]), subStr[1],subStr[2], devskills);
    }

    private Long generateId() {
        return getAllInternal().stream().map(Developer::getId).max(Comparator.comparing(aLong -> aLong)).orElse(0L) + 1;
    }
    public Developer getByName(String firstName, String lastName) {
        return getAllInternal().stream().filter(s -> s.getFirstName().equals(firstName)).filter(s -> s.getLastName().equals(lastName)).findFirst().orElse(null);
    }
    private List<Developer> getAllInternal() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            List<Developer> developerList = new ArrayList<>();
            String strLine;
            while ((strLine = reader.readLine()) != null) {
                if (strLine.isEmpty()) {
                    break;
                }
                developerList.add(stringToDeveloper(strLine));
             }
            return developerList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
