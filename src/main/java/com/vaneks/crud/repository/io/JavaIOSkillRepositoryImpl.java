package com.vaneks.crud.repository.io;

import com.vaneks.crud.model.Skill;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class JavaIOSkillRepositoryImpl implements com.vaneks.crud.repository.SkillRepository {

    private final String file = getClass().getClassLoader().getResource("/skills.txt").getFile();
    public List<Skill> getAll() {
        return getAllInternal();
    }

    public Skill getById(Long id) {
        return getAllInternal().stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }

    public Skill update(Skill skill) {
        List<Skill> allSkills = getAllInternal().stream()
                .peek(s -> {
                    if (s.getId().equals(skill.getId()))
                        s.setName(skill.getName());
                })
                .collect(Collectors.toList());
        writeSkillToFile(allSkills);
        return skill;
    }

    public void deleteById(Long id) {
        List<Skill> allSkills = getAllInternal();
        allSkills.removeIf(s -> s.getId().equals(id));
        writeSkillToFile(allSkills);
    }

    public Skill save(Skill skillToSave) {
        List<Skill> allSkills = getAllInternal();
        Skill addId = new Skill(generateId(), skillToSave.getName());
        allSkills.add(addId);
        writeSkillToFile(allSkills);
        return addId;
    }

    private void writeSkillToFile(List<Skill> skills) {
        BufferedWriter writerFile = null;
        try {
            writerFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

        for (Skill skill : skills) {
            writerFile.write(skillToString(skill));
            writerFile.newLine();
        }
        writerFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Skill stringToSkill(String string) {
        String[] subStr = string.split(" ");
        return new Skill(Long.parseLong(subStr[0]), subStr[1]);
    }

    private String skillToString(Skill skill) {
        return skill.getId() + " " + skill.getName();
    }

    private Long generateId() {
        return getAllInternal().stream().map(Skill::getId).max(Comparator.comparing(aLong -> aLong)).orElse(0L) + 1;
    }
    public Skill getByName(String name) {
        return getAllInternal().stream().filter(s -> s.getName().equals(name)).findFirst().orElse(null);
    }

    private List<Skill> getAllInternal() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            List<Skill> skillList = new ArrayList<>();
            String strLine;
            while ((strLine = reader.readLine()) != null) {
                if (strLine.isEmpty()) {
                    break;
                }
                skillList.add(stringToSkill(strLine));
            }
            return skillList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
