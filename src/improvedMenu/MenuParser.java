package improvedMenu;
import java.io.*;
import java.util.ArrayList;

//author @Uras1717

/**TODO: 
 * break up the menu in to
    allfood items  (AL)
    and then be able to return all the items on a particular day by reading the file and putting 
    those necessary items into an AL which is returned.
 */

//TODO: need to look at menu.csv NOT frary_menu_extended
public class MenuParser{
    private ArrayList<String> fooditems;
    public MenuParser(String samplefilename){
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
                    if (columns.length>=3){
                        fooditems.add(columns[2].trim());
                    }
                }
            }
        } catch (IOException expection){expection.printStackTrace();}
    }

    public ArrayList<String> getItems(){return fooditems;}

    public static void main(String[] args){
        MenuParser parser = new MenuParser("frary_menu_extended.csv");
        System.out.println("Food items in the file:");
        for (String item:parser.getItems()){
            System.out.println(item);
        }
    }
}
