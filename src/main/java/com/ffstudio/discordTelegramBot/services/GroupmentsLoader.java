package com.ffstudio.discordTelegramBot.services;

import com.opencsv.CSVReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class GroupmentsLoader {

    public List<String> getGroupments() {
        List<String> names = null;
        try (CSVReader csvReader = new CSVReader(new FileReader(new ClassPathResource("groupments.csv").getFile()));) {
            names = Arrays.asList(csvReader.readNext());
        } catch (IOException e) {
            System.err.println(e);
        }
        return names;
    }

}
