import java.util.*;
import edu.duke.*;

public class EarthQuakeClient
{

    public EarthQuakeClient() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData,
    double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for (QuakeEntry qe:quakeData){
            if (qe.getMagnitude() > magMin){
                answer.add(qe);
            }
        }

        return answer;
    }

    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData,
    double distMax,
    Location from) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
         for (QuakeEntry qe : quakeData) {
            if (qe.getLocation().distanceTo(from) < distMax) {
                answer.add(qe);
            }
        }
        return answer;
    }

    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }

    }

    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        ArrayList<QuakeEntry> newList = filterByMagnitude(list,5.0);
        System.out.println("read data for "+ list.size() +" quakes");
        for (QuakeEntry qe : newList) {
           System.out.println(qe); 
        }
        System.out.println("Found "+ newList.size() +" quakes that match that criteria"); 
    }

    public void closeToMe(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");

        // This location is Durham, NC
        //Location city = new Location(35.988, -78.907);

        // This location is Bridgeport, CA
         Location city =  new Location(38.17, -118.82);
        ArrayList<QuakeEntry> close = filterByDistanceFrom(list, 1000*1000, city);
        for (int k=0; k< close.size(); k++) {
            QuakeEntry entry = close.get(k);
            System.out.println(entry);
        }
        System.out.println("Found " + close.size() + " quakes that match that criteria");
    }
    
    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData,
    double minDepth, double maxDepth) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for (QuakeEntry qe : quakeData) {
            if (minDepth < qe.getDepth() && qe.getDepth() < maxDepth) {
                answer.add(qe);
            }
        }
        return answer;
    }
    
    public void quakesOfDepth(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "data/nov20quakedatasmall.atom";
        String source="data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        //ArrayList<QuakeEntry> quakeDepth = filterByDepth(list, -10000.0, -5000.0);
        ArrayList<QuakeEntry> quakeDepth = filterByDepth(list, -10000.0, -8000.0);
        for (int k=0; k<quakeDepth.size(); k++) {
            QuakeEntry entry = quakeDepth.get(k);
            System.out.println(entry);
        }
        System.out.println("Found " + quakeDepth.size() + " quakes that match that criteria");
    
    }
    
    public ArrayList<QuakeEntry> filterByPhrase(ArrayList<QuakeEntry> quakeData, String where, String phrase){
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for (QuakeEntry qe:quakeData){
            String quakeTitle = qe.getInfo();
            if (where == "start" && quakeTitle.startsWith(phrase)){
                answer.add(qe);
            } else if (where == "end" && quakeTitle.endsWith(phrase)){
                answer.add(qe);
            }else if (where == "any" && quakeTitle.contains(phrase)) {
                answer.add(qe);
            } 
        }
        return answer;
    }
    
    public void quakesByPhrase(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        String where = "any";String phrase = "Creek";
        //String where = "any";String phrase = "Can";
        //String where = "start";String phrase = "Explosion";
        ArrayList<QuakeEntry> quakePhrase = filterByPhrase(list, where, phrase);
        
        
        for (int k=0; k<quakePhrase.size(); k++) {
            QuakeEntry entry = quakePhrase.get(k);
            System.out.println(entry);
        }
        System.out.println("Found " + quakePhrase.size() + " quakes that match " + phrase +" at " + where);
    
    
    }
    
    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: "+list.size());
    }
}
