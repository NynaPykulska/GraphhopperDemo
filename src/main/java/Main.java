import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.PathWrapper;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.storage.GraphHopperStorage;
import com.graphhopper.storage.index.LocationIndexTree;
import com.graphhopper.util.*;

import java.util.Locale;

public class Main {

    public static void main(String[] args){

        //import OpenStreetMap data
        GraphHopper hopper = new GraphHopper();

        //to sobie trzeba sciagnac stad: http://download.geofabrik.de/europe.html
        hopper.setOSMFile("./src/main/resources/malopolskie-latest.osm.pbf");
        hopper.setGraphHopperLocation("./target/mapmatchingtest");
        hopper.setEncodingManager(new EncodingManager("foot"));

        //now this can take minutes if it imports or a few seconds for loading
        //of course this is dependent on the area you import
        hopper.importOrLoad();

        //simple configuration of the request object, see the GraphHopperServlet class for more possibilities.
        GHRequest req = new GHRequest(50.072878,19.976596,50.066076,19.961488).
                setWeighting("fastest").
                setVehicle("foot").
                setLocale(Locale.UK);

        GHResponse rsp = hopper.route(req);


        GraphHopperStorage storage =  hopper.getGraphHopperStorage();
        System.out.println("\tStorage");
        System.out.println(storage.toString());
        System.out.println("\tDetail storage");
        System.out.println(storage.toDetailsString());
        System.out.println("");

        LocationIndexTree locationIndexTree = new LocationIndexTree(storage, storage.getDirectory());



        //first check for errors
        if(rsp.hasErrors()){
            System.out.println("error");
            System.out.println(rsp.getErrors());
            return;
        }

        //use the best path, see the GHResponse class for more possibilities.
        PathWrapper path=rsp.getBest();

        InstructionList il=path.getInstructions();

        System.out.println("instructions");
        for(Instruction instruction:il){
            //Distance in meter until no new instruction
            System.out.println(instruction.getDistance());
            System.out.println(instruction.getPoints());
            System.out.println(instruction.getSign());
            System.out.println(instruction.getName());
            System.out.println("-------");

        }
    }
}
