package pt.iscte.poo.utils;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Comparator;

public class Score implements Comparator<Score>{
    private int scoreTime;
    private LocalDateTime date;

    public Score(int scoreTime, LocalDateTime date){
        this.scoreTime=scoreTime;
        this.date=date;
    }

    public int getScoreTime(){
        return scoreTime;
    }

    public LocalDateTime getDate(){
        return date;
    }

    public String getDateFormated(){

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return getDate().format(formato);

    }

    @Override
    public int compare(Score s1, Score s2) {
        return s1.getScoreTime()-s2.getScoreTime();
    }

    public static Score readScoreString(String s){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String[] scoreArray = s.trim().split("\\s+");
        int scoree = Integer.valueOf(scoreArray[0]);
        LocalDateTime dataHoraFormatada = LocalDateTime.parse(scoreArray[1]+" "+scoreArray[2], formato);
        
        return new Score(scoree, dataHoraFormatada);
    }

    @Override
    public String toString(){
        System.out.println(12- String.valueOf(getScoreTime()).length());
        return getScoreTime() + emptyString(12- String.valueOf(getScoreTime()).length()) + getDateFormated();
    }

    public String emptyString(int n){
        String s = "";
        for(int i = 0; i != n;i++){
            s+=" ";
        }
        return s;
    }


    
}
