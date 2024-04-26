import java.time.LocalDateTime;
import java.util.HashMap;

public class LecturaLog {
    public static final String FICHERO_LOG = "Linux_2k.log";
    public String regex;
    public HashMap<LocalDateTime, String> accesosSSH;

    // Constructor
    public LecturaLog() {
        this.regex = "\\b\\w{3}\\s\\d{1,2}\\s\\d{2}:\\d{2}:\\d{2}\\b.*sshd.*rhost=(\\S+)";
        this.accesosSSH = new HashMap<>();
    }

    public void leerFichero(){

    }



}
