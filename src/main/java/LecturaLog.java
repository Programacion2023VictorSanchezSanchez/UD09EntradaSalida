import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LecturaLog {
    private static final String FICHERO_LOG = "D:\\Users\\vagos\\UD09EntradaSalida\\src\\main\\java\\Linux_2k.log";
    private static final String REGEX = "\\b\\w{3}\\s+\\d{1,2}\\s+\\d{2}:\\d{2}:\\d{2}\\b.*sshd.*rhost=([^\\s]+)";
    private HashMap<LocalDateTime, String> accesosSSH;

    // Constructor
    public LecturaLog() {
        this.accesosSSH = new HashMap<>();
    }

    public void leerFichero() {
        try (BufferedReader br = new BufferedReader(new FileReader(FICHERO_LOG))) {
            String line;
            Pattern pattern = Pattern.compile(REGEX);
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .appendPattern("MMM d HH:mm:ss")
                    .toFormatter(Locale.ENGLISH);

            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String dateString = matcher.group(0).substring(0, 15); // Extraer la parte de la fecha
                    String ip = matcher.group(1);

                    // Parsear la fecha
                    LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
                    accesosSSH.put(dateTime, ip);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void accesosDesdeHasta(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        String nombreArchivo = "accesos_" + fechaInicio.format(DateTimeFormatter.ofPattern("yyyy_MM_dd")) +
                "_" + fechaFin.format(DateTimeFormatter.ofPattern("yyyy_MM_dd")) + ".txt";

        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            for (LocalDateTime fecha : accesosSSH.keySet()) {
                if (fecha.isAfter(fechaInicio) && fecha.isBefore(fechaFin)) {
                    String linea = "Fecha: " + fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                            ", IP: " + accesosSSH.get(fecha) + "\n";
                    writer.write(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LecturaLog lector = new LecturaLog();
        lector.leerFichero();

        LocalDateTime fechaInicio = LocalDateTime.of(2023, 6, 15, 0, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2023, 6, 16, 0, 0);
        lector.accesosDesdeHasta(fechaInicio, fechaFin);
    }



}
