package ru.cs.vsu;

import ru.cs.vsu.models.data.DataUtils;
import ru.cs.vsu.models.data.Torch;
import ru.cs.vsu.models.data.TorchUpgraded;
import ru.cs.vsu.utils.FileUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ConsoleTest {
    public static void main(String[] args) {
        ArrayList<Integer> arrayList = FileUtils.getFileDataByString("C:\\Users\\akamo\\IdeaProjects\\LEXUS\\KG2020_G41_Task3\\src\\ru\\cs\\vsu\\data\\dayData.txt");
        TorchUpgraded.getTorchesByData(arrayList, 7);
    }
}
