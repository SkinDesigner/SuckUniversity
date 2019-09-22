import java.util.*;
public class HiddenMarkov {
    
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        String[][] clue_array = new String[7][2];
        for(int line_counter=0;line_counter<7;line_counter++){
            String clue = keyboard.nextLine();
            String[] array =clue.split("\\|");
            clue_array[line_counter] = array;
        }
        String[] newArrayA = new String[6];
        for(int i = 0; i<6;i++){
            newArrayA[i] = clue_array[i][1];
        }
        /////////////////////////////////////////////////////////////
        String last_place = clue_array[5][1];
        List<String> possible_next_place = new ArrayList<>();
        for(int i = 0; i<5;i++){
            if(newArrayA[i].equals(last_place)){
                possible_next_place.add(newArrayA[i+1]);
            }
        }
        HashMap<String,Double> hash = new HashMap<String,Double>();
        for(String place : possible_next_place){
            if(!hash.containsKey(place)){
                Double counter = 0.0;
                Double size = Double.valueOf(possible_next_place.size());
                for(String s : possible_next_place){
                    if(s == place){
                        counter ++;
                    }
                }
                hash.put(place,counter/size);
            }
        }
        for(String place : newArrayA){
            if(!possible_next_place.contains(place)){
                if(!hash.containsKey(place)){
                    hash.put(place,0.0);
                }
            }
        }
        /////////////////////////////////////////////////////////////////////
        String[] newArrayB = new String[6];
        for(int i = 0; i<6;i++){
            newArrayB[i] = clue_array[i][0];
        }
        String last_clue = clue_array[6][0];
        List<String> possible_next = new ArrayList<>();
        for(int i = 0; i<6;i++){
            if (newArrayB[i].equals(last_clue)){
                possible_next.add(newArrayA[i]);
            }
        }
        
        HashMap<String,Double> map = new HashMap<String,Double>();
        for(String place : possible_next){
            if(!map.containsKey(place)){
                Double counter = 0.0;
                Double size = Double.valueOf(possible_next.size());
                for(String s : possible_next){
                    if(s .equals(place)){
                        counter ++;
                    }
                }
                map.put(place,counter/size);
            }
        }
        for(String place : newArrayA){
            if(!possible_next.contains(place)){
                if(!map.containsKey(place)){
                    map.put(place,0.0);
                }
            }
        }
        Map<String,Double>placeA  = new TreeMap<String,Double>(hash);
        Map<String,Double>placeB = new TreeMap<String,Double>(map);
        // System.out.println(placeA.toString());
        // System.out.println(placeB.toString());
        HashMap<String,Double> result = new HashMap<String,Double>();
        for(String key : placeA.keySet()){
            result.put(key,placeA.get(key)*placeB.get(key));
        }
        
        Map<String,Double> fina = new TreeMap<String,Double>();
        for(String key: result.keySet()){
            Double p = result.get(key);
            Double total = 0.0;
            for(Double t : result.values()){
                total+=t;
            }
            Double percent = p/total;
            fina.put(key,percent);
        }
        for(String key : fina.keySet()){
            if(Double.isNaN(fina.get(key))){
                System.out.printf("%s: undefined",key);
                System.out.println();
            }
            else{
            System.out.printf("%s: %.2f ",key,fina.get(key)*100);
            System.out.println("%");}
        }
    }
}
