package ru.cs.vsu;

import ru.cs.vsu.models.data.Torch;
import ru.cs.vsu.utils.FileUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ConsoleText {
    public static void main(String[] args) {
        /*
        String filename = "C:\\Users\\akamo\\IdeaProjects\\LEXUS\\KG2020_G41_Task3\\src\\ru\\cs\\vsu\\data\\weekData.txt";

        ArrayList<Integer> io = FileUtils.getFileData(filename);
        Random rnd = new Random();
        int curr = 100;
        try(FileWriter writer = new FileWriter(filename, false))
        {
            for (int i = 0; i < 604800; i++) {
                curr = rnd.nextInt(curr + (curr/2 + 1)) + 100;
                writer.write(curr + " ");
            }

            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
*/

        //ArrayList<Integer> list = FileUtils.getFileData("C:\\Users\\akamo\\IdeaProjects\\LEXUS\\KG2020_G41_Task3\\src\\ru\\cs\\vsu\\data\\dayData.txt");

        //ArrayList<Torch> torches = Torch.getTorchesByData(list, 17);

        //System.out.println(torches.size());
    }
}
