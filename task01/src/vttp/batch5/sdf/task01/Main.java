package vttp.batch5.sdf.task01;

// Use this class as the entry point of your program
import java.io.*;
import java.nio.file.*;
import java.util.*;

import vttp.batch5.sdf.task01.models.BikeEntry;

public class Main {

    public static int totalCyclists(BikeEntry entry) {

        int totalCyclists = entry.getCasual() + entry.getRegistered();
        return totalCyclists;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

        System.out.printf("hello, world\n");

        
        // String csvFile = "day.csv"; 
        String templateFile = "template.txt"; 
        String outputFile = "output.txt";

        List<BikeEntry> BikeList = new ArrayList<>();
        // Read file
        FileReader fr = new FileReader("day.csv");
        BufferedReader br = new BufferedReader(fr);

        // read header and skip it and print it
        
        System.out.println(br.readLine());
        String line;
        while ((line = br.readLine()) != null) {
            String[] bikeData = line.split(",");
            BikeEntry entry = BikeEntry.toBikeEntry(bikeData);
            BikeList.add(entry);
        }
        br.close();

        System.out.println("before sort...");
        // for (int i = 0; i < BikeList.size(); i++) {
        //     System.out.println(BikeList.get(i).toString());
        // }
        // for (BikeEntry entry : BikeList) {
        //     System.out.println(entry);
        // }

        String[] positions = { "highest", "second highest",
                "third highest", "fourth highest", "fifth highest" };
        System.out.println("after sort....");
        BikeList.sort((a, b) -> Integer.compare(totalCyclists(b), totalCyclists(a)));
        
        // for (BikeEntry entry : BikeList) {
        //     System.out.println(entry);
        // }

        String template = new String(Files.readAllBytes(Paths.get(templateFile)));

        FileWriter fw = new FileWriter(outputFile);
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i = 0; i < 5; i++) {

            BikeEntry entry = BikeList.get(i);
            String position = positions[i];

            String output = template.replace("<position>", position).replace("<season>", Utilities.toSeason(entry.getSeason()))
                    .replace("<day>", Utilities.toWeekday(entry.getWeekday()))
                    .replace("<month>", Utilities.toMonth(entry.getMonth())).replace("<total>", String.valueOf(totalCyclists(entry)))
                    .replace("<weather>", weatherSit(entry.getWeather())).replace("<holiday>", entry.isHoliday() ? "is a holiday" : "is not a holiday");
                    bw.write(output + "\n\n\n");
                    System.out.println(Utilities.toWeekday(entry.getWeekday()));
        }

        System.out.println("Printing completed, please check out put file --> " + outputFile);

        bw.flush();
        bw.close();

    }

  

    private static String weatherSit(int weatherNum) {
        switch (weatherNum) {
            case 1:
                return "Clear, Few clouds, Partly cloudy, Partly cloudy";
            case 2:
                return "Mist + Cloudy, Mist + Broken clouds, Mist + Few clouds, Mist";
            case 3:
                return "Light Snow, Light Rain + Thunderstorm + Scattered clouds, Light Rain + Scattered clouds";
            case 4:
                return "Heavy Rain + Ice Pellets + Thunderstorm + Mist, Snow + Fog";
            default:
                return "Unknown weather";
        }
    }
    
   
}
