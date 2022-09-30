import java.io.*;
import java.util.*;

public class CSV {
    public static void main(String[] args) throws IOException {
        //-------------------------- Variabili ------------------------
        Scanner im = new Scanner(System.in);
        String file;

        String riga;
        String[] elementi;

        int num;
        Map<String, Integer> classi = new HashMap<>();

        //-------------------------- Code ------------------------------
        System.out.print("inserire il nome del file: ");
        file = im.nextLine();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            riga = br.readLine();

            while((riga = br.readLine())!=null){
                elementi = riga.split(",");

                if(classi.containsKey(elementi[4])){
                    num = classi.get(elementi[4]).intValue()+1;
                    classi.put(elementi[4], num);
                }
                else{
                    classi.put(elementi[4], 1);
                }
            }

            System.out.println(classi);
        }
        catch (Exception e){
            System.out.println("file non valido");
        }
    }
}
