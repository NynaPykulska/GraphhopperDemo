import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.PathWrapper;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.Instruction;
import com.graphhopper.util.InstructionList;

import java.util.Locale;

public class Main {

    public static void main(String[] args){

            //importOpenStreetMapdata
            GraphHopper hopper = new GraphHopper();

            //to sobie trzeba sciagnac stad: http://download.geofabrik.de/europe.html
            hopper.setOSMFile("./src/main/resources/poland-latest.osm.pbf");
            hopper.setGraphHopperLocation("./target/mapmatchingtest");
            hopper.setEncodingManager(new EncodingManager("foot"));

            //nowthiscantakeminutesifitimportsorafewsecondsforloading
            //ofcoursethisisdependentontheareayouimport
            hopper.importOrLoad();

            //simpleconfigurationoftherequestobject,seetheGraphHopperServletclasssformorepossibilities.
            GHRequest req =new GHRequest(50.072878,19.976596,50.066076,19.961488).
                    setWeighting("fastest").
                    setVehicle("foot").
                    setLocale(Locale.UK);
            GHResponse rsp = hopper.route(req);

            //firstcheckforerrors
            if(rsp.hasErrors()){
                System.out.println("error");
                System.out.println(rsp.getErrors());
                return;
            }

            //usethebestpath,seetheGHResponseclassformorepossibilities.
            PathWrapper path=rsp.getBest();

            InstructionList il=path.getInstructions();

            System.out.println("instructions");
            for(Instruction instruction:il){
                //Distanceinmeteruntilnonewinstruction
                System.out.println(instruction.getDistance());
                System.out.println(instruction.getPoints());
                System.out.println(instruction.getSign());
                System.out.println(instruction.getName());
                System.out.println("-------");

            }


    }
}
