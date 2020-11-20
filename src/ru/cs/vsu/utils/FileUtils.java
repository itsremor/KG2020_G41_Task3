package ru.cs.vsu.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUtils {
    public static ArrayList getFileData(String filename){
        ArrayList<Integer> data = new ArrayList<>();

        try {
            Scanner scn = new Scanner(new File(filename));
            scn.useDelimiter("[^0-9]+");

            while (scn.hasNext()) {
                data.add(scn.nextInt());
            }
        }
        catch (FileNotFoundException t){
            return null;
        }

        return data;
    }

    public static void loadStringToFile(String filename, ArrayList data){
        try(FileWriter writer = new FileWriter(filename, false))
        {
            for (int i = 0; i < data.size(); i++) {
                writer.write(data.get(i) + " ");
            }
            writer.flush();
        }
        catch(IOException ex){}
    }
}
