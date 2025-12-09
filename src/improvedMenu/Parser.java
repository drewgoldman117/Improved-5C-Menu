package improvedMenu;
import java.io.*;
import java.util.ArrayList;

//author @Uras1717

public class Parser{
    private ArrayList<String> fooditems;
    public Parser(String samplefilename){
        fooditems=new ArrayList<>();
        File thedatafile = new File(samplefilename);
        if (!thedatafile.exists()){thedatafile = new File("data/"+samplefilename);}
        try (BufferedReader bufferedreader1 = new BufferedReader(new FileReader(thedatafile))){
            String ithline;
            boolean firstline=true;

            while ((ithline = bufferedreader1.readLine()) != null){
                if (!ithline.trim().isEmpty()){
                    if (firstline){firstline=false;
                        continue;}
                    String[] columns=ithline.split(",",-1);
                    if (columns.length>=3) {
                        fooditems.add(columns[2].trim());
                    }
                }
            }
        } catch (IOException expection){expection.printStackTrace();}
    }

    public ArrayList<String> getItems(){return fooditems;}

    public static void main(String[] args){
        Parser parser = new Parser("frary_menu_extended.csv");
        System.out.println("Food items in the file:");
        for (String item:parser.getItems()){
            System.out.println(item);
        }
    }
}
