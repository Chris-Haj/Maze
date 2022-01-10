import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Copy {

    public Copy(){

    }

    public static ArrayList<String> DrawMap(){
        ArrayList<String> Map = new ArrayList<>();
        File file =  new File("in.txt");
        Scanner readLine = null;
        try {
           readLine = new Scanner(file);
           while(readLine.hasNextLine()){
               Map.add(readLine.nextLine());
           }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            readLine.close();
        }
        return Map;
    }

    public static int FindStartPosition(String firstLine){
        return firstLine.indexOf('S');
    }

    public static void Initialize(){
        ArrayList<String> Map = DrawMap();
        int start = FindStartPosition(Map.get(0));
        int maxXpos=Map.get(0).length()-1;
        int maxYpos=Map.size()-1;
        boolean[][] visited=new boolean[maxXpos+1][maxYpos+1];
    }


    public static int FSP(ArrayList<String> Map,int x,int y,int FinishX,int FinishY,boolean[][] visited,int minTraveled,int distance,int maxXPos,int maxYPos){
      if(x==FinishX&&y==FinishY)
          return Math.min(minTraveled,distance);
      visited[x][y]=true;
      if (x>=0&&x<=maxXPos&&y>=0&&y<=maxYPos)

    }

    public static boolean ContinuePath(ArrayList<String> Map,boolean[][] visited, int x,int y,int maxX,int maxY, char Direction){
        return (x >= 0 && x <= maxX && y >= 0 && y <= maxY) && Map.get(y).charAt(x) !='0' && !visited[x][y];
    }
}
