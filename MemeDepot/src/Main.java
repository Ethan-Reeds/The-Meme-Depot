
import java.io.File;
import org.eclipse.jetty.util.log.AbstractLogger;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.StdErrLog;

public class Main {
    public static void main(String[] args){
        org.eclipse.jetty.start.Main.main(args);
    }
    
     public static void startOrStopJetty(boolean startIt){
        String jettyHome="../jetty";
        if( !new File(jettyHome).exists()) {
            System.out.println("Cannot find Jetty!");
            return;
        }
        String[] args = new String[]{ 
            "jetty.home="+jettyHome,
            "STOP.PORT=2021", "STOP.KEY=AutomaticTofu",
            startIt ? "" : "--stop"
        };
        var LG = new StdErrLog();
        LG.setLevel(AbstractLogger.LEVEL_OFF);
        Log.setLog(LG);
        org.eclipse.jetty.start.Main.main(args);
    }
}