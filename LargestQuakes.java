import java.util.*;
/**
 * Write a description of LargestQuakes here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LargestQuakes {
    public void findLargestQuakes(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        /*
        for(QuakeEntry qe:list){
            System.out.println(qe.toString());
        }*/
        //System.out.println("read data for "+list.size()+" quakes");
        int index = indexOfLargest(list);
        //System.out.println("index of largest quake is "+ index + " with "+ list.get(index).getMagnitude() +" magnitude.");
        ArrayList<QuakeEntry> res = getLargest(list,5);
        for (int i=0; i < 5; i++){
            System.out.println(res.get(i));
        }
        
        
    }
    public int indexOfLargest(ArrayList<QuakeEntry> data){
        int imax=0;
        for(int k=0;k<data.size();k++){
            if (data.get(imax).getMagnitude() < data.get(k).getMagnitude()){
                imax = k;
            }
        }
        return imax;
    } 
    
    public ArrayList<QuakeEntry> getLargest(ArrayList<QuakeEntry> quakeData, int howMany){
        ArrayList<QuakeEntry> copy = new ArrayList<QuakeEntry>(quakeData);
        ArrayList<QuakeEntry> ret  = new ArrayList<QuakeEntry>();
        for(int j=0; j < howMany; j++) {
            int maxIndex = indexOfLargest(copy);          
            ret.add(copy.get(maxIndex));
            copy.remove(maxIndex);
        }
        return ret;  
    }
    
}
