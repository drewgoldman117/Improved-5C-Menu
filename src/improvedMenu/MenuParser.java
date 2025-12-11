/**
 * @author Uras1717
 */

package improvedMenu;

import java.io.*;
import java.util.ArrayList;

/** 
 * The MenuParser class is to parse menu.csv and store food items from there in arraylists, provide methods to access them both for all items or just items for one specific meal period.
 */
public class MenuParser{ 
    //This array lists all food items from a given csv file
    private ArrayList<String>fooditems;

    //This one lists all menu rows as objects MenuRow
    private ArrayList<MenuRow>rowsstore;

    //MenuRow represents a single row of the csv file, with info day, meal number, and the food item
    private static class MenuRow{ 
        //the day
        int theday;
        //the number of meal
        int thenomeal;
        //the food item
        String theitem;

        /**
         * Here I am constructing the aforementioned MenuRow object. This represents a single row.
         * @param day the day of the week selected
         * @param mealno the meal number selected
         * @param item the food item selected
         */
        MenuRow(int day, int mealno, String item){
            theday=day;
            thenomeal=mealno;
            theitem=item;
        }
    }

    /**
     * Here I am constructing MenuParser, which loads the menu from the csv file
     * To find the csv file, searches through directory first, then data folder. We moved things around a couple times so I just kept this if we moved again.
     * Then read the file.
     * @param menuusamplefilename a sample file name for the menu csv -- for our purposes it is menu.csv, but can be changed if needed
     */
    public MenuParser(String menusamplefilename){
        //arraylists for food items and storage of rows
        fooditems=new ArrayList<>(); rowsstore=new ArrayList<>();

        //first search through directory
        File filethathasthedata=new File(menusamplefilename);

        //if not under data/
        if(!filethathasthedata.exists()){filethathasthedata=new File("data/"+menusamplefilename);}

        //Here reading the file
        try(BufferedReader br=new BufferedReader(new FileReader(filethathasthedata))){
            
            //Define line variable and if first line has been read or not to move  on forward
            String line; boolean firstlineread=true;

            //Reading the file line by line
            while((line=br.readLine())!=null){
                if(!line.trim().isEmpty()){
                    if(firstlineread){firstlineread=false; continue;}

                    //Here I parse by commas (,)
                    String[]columns=line.split(",",-1);

                    //Here I make sure if there are at least 3 columns. If there are not, we skip that line as we have incomplete information, We don't want the incomplete info to break the whole.
                    if(columns.length>=3){

                        //the day that is SELECTED by user
                        int whatdayitis=Integer.parseInt(columns[0].trim());

                        //the meal number that is SELECTED by user
                        int whatnomealitis=Integer.parseInt(columns[1].trim());

                        //current food item
                        String currentitem=columns[2].trim();

                        //Adding these to our row storage
                        rowsstore.add(new MenuRow(whatdayitis,whatnomealitis,currentitem));

                        //and to our food items list (the item)
                        fooditems.add(currentitem);
                    }
                }
            }
            // in case something goes wrong
        }catch(IOException expection){expection.printStackTrace();}
    }

    /**
     * Returns ALL the food items from a CSV
     *
     * @return list of all food items
     */
    public ArrayList<String>getAllFoodItems(){return fooditems;};

    /**
     * Returns food items for a SPECIFIC day and meal number in that day
     *
     * @param whatdayisit the day of the week SELECCTED
     * @param whatnomealisit the meal number SELECTED
     * @return list of food items for that SPECIFIC day and meal number
     */
    public ArrayList<String>getItemsForDay(int whatdayisit,int whatnomealisit){
        
        // Definiing arraylist which is our result for the selections
        ArrayList<String> result=new ArrayList<>();

        //Going through all rows stored
        for(MenuRow currentrowofmenu:rowsstore){ 

            //checking if both day and meal number equal to their respective selected values
            if(currentrowofmenu.theday==whatdayisit && currentrowofmenu.thenomeal==whatnomealisit){
                //if so adding to result
                result.add(currentrowofmenu.theitem);}
            } 
        return result;
    }

    /**
     * Method to test MenuParser
     * Loads "menu.csv", prints all food items, and then those for day 1 and meal number 1 (breakfast) for that day
     *
     * @param args any argument from the command line technically
     */
    public static void main(String[]args){

        //set for menu.csv
        MenuParser parser=new MenuParser("menu.csv"); //the file is t menu.csv

        //printing all food items
        System.out.println("ALL FOOD ITEMS:");
        for(String item:parser.getAllFoodItems()){System.out.println(item);}

        //printing specific day and meal items (day 1, meal 1)
        System.out.println("\nDAY 1 BREAKFAST:");
        for(String item:parser.getItemsForDay(SystemManager.MON,SystemManager.MEAL1)){System.out.println(item);}
    }
}
