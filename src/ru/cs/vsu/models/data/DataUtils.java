package ru.cs.vsu.models.data;

public class DataUtils {
    public static int getTime(Period period){
        switch(period){
            case MINUTE:
                return 60;
            case HOUR:
                return 3600;
            case DAY:
                return 86400;
            case WEEK:
                return 604800;
            case MONTH:
                return 2592000;
        }
        return -1;
    }
}
