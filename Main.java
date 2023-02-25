import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime; 

public class Main {

    // Constants for output
    public static double AIR_K = 0.4;
    public static double NUM_SHOTS = 40;
    public static double START_ANGLE = 10;
    public static double END_ANGLE = 80;    
    public static double LAUNCH_SPEED = 8.2;

    public static double X_STEP = 1;
    public static double X_MAX = 20;

    // File output
    public static FileWriter f;

    public static void main(String[] args) throws IOException {

        // Create file
        f = createFile();

        // Create header line
        f.write("Angle,Range\n");

        // Loop for every angle
        double delta = (END_ANGLE-START_ANGLE) / (NUM_SHOTS-1);
        for (int i = 0; i < NUM_SHOTS; i++) {
            double angle = START_ANGLE + delta*i;

            // Calculate the range
            double range = calcRangeFromAngle(angle);

            // Add to csv
            f.write("" + angle + "," + range + "\n");

        }

        // Close stream
        f.close();
    }

    // Calculate the range based on angle
    public static double calcRangeFromAngle(double a) {
        double rads = Math.toRadians(a);

        double temp1 = Math.pow(LAUNCH_SPEED, 2) * Math.sin(2 * rads);
        temp1 /= 9.81 + AIR_K * LAUNCH_SPEED * Math.sin(rads);

        double temp2 = -0.5 * AIR_K * LAUNCH_SPEED * Math.cos(rads);

        double temp3 = 4 * Math.pow(LAUNCH_SPEED * Math.sin(rads), 2);
        temp3 /= Math.pow(9.81 + AIR_K * LAUNCH_SPEED * Math.sin(rads), 2);

        double out = temp1 + (temp2*temp3);

        return out;

    }

    // Create file to write to
    public static FileWriter createFile() throws IOException {

        FileWriter f = new FileWriter(getFileNameString() + ".csv");
        return f;

    }

    // Get file name based on time
    public static String getFileNameString() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH,mm,ss");
        LocalDateTime now = LocalDateTime.now();
        return "out/" + dtf.format(now);

    }

}