package improvedMenu;

import java.io.*;
import java.util.ArrayList;

//author @Uras1717

//the menuparser method gives one) a list of all food items and two) the list of food items for a given specific day

public class MenuParser{ 
    private ArrayList<String>fooditems; //creating an array list here which will store all foor items from the menbu
    private ArrayList<MenuRow>rowsstore; //an arraylist storing all menu rows
    private static class MenuRow{ 
        int theday; int thenomeal; String theitem; //defining items or whatever
        MenuRow(int day, int mealno, String item){theday=day; thenomeal=mealno; theitem=item;} //menurow here represents one rtow of the csv, day, no of meal, and the food item
    }
    public MenuParser(String menuusamplefilename){ //constructor that takes in the filename of the menu csv
        fooditems=new ArrayList<>(); rowsstore=new ArrayList<>(); //define arraylists
        File filethathasthedata=new File(menuusamplefilename); //finding file in directory
        if(!filethathasthedata.exists()){filethathasthedata=new File("data/"+menuusamplefilename);} //if not found i am assigning to look in data folder
        try(BufferedReader br=new BufferedReader(new FileReader(filethathasthedata))){ //this is the buffered reader to read the file
            String line; boolean firstlineread=true;
            while((line=br.readLine())!=null){ //read line by line
                if(!line.trim().isEmpty()){ //if the line isnt empty
                    if(firstlineread){firstlineread=false; continue;}

                    String[]columns=line.split(",",-1); //parsng splitting by ,
                    if(columns.length>=3){ //if length of columns is more than 3 i am doing trim here just in case
                        int whatdayitis=Integer.parseInt(columns[0].trim());
                        int whatnomealitis=Integer.parseInt(columns[1].trim());
                        String currentitem=columns[2].trim();
                        rowsstore.add(new MenuRow(whatdayitis,whatnomealitis,currentitem)); //adding new row to row storage
                        fooditems.add(currentitem); //adding current item to food items list
                    }
                }
            }
        }catch(IOException expection){expection.printStackTrace();} //just in case sometihng goes wrong
    }

    public ArrayList<String>getAllFoodItems(){return fooditems;} //method to return food items

    public ArrayList<String>getItemsForDay(int whatdayisit,int whatnomealisit){ // this is to retturn food items for that specific day
        ArrayList<String>result=new ArrayList<>(); //definitng arraylist
        for(MenuRow currentrowofmenu:rowsstore){  //match current row of the menu with row storage 
            if(currentrowofmenu.theday==whatdayisit && currentrowofmenu.thenomeal==whatnomealisit){ //if both equal to their respective alikes
                result.add(currentrowofmenu.theitem);}} //adding result to list and therefore
        return result; //printing
    }

    public static void main(String[]args){ //here is my test main
        MenuParser parser=new MenuParser("menu.csv"); //the file is t menu.csv
        System.out.println("ALL FOOD ITEMS:");
        for(String item:parser.getAllFoodItems()){System.out.println(item);} //all food items
        System.out.println("\nDAY 1 BREAKFAST:");
        for(String item:parser.getItemsForDay(SystemManager.MON,SystemManager.MEAL1)){System.out.println(item);} //specific items. i am searching for monday and meal1 (defined in systemmanager as breakfast)
    }
}
