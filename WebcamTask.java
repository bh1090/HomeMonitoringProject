package eecs1021;

// importing the required libraries
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

// link to the github files:
// https://github.com/sarxos/webcam-capture

// This class allows me to connect my webcam with my computer and take images.
// This class was modified by me and was created by sarxos (GitHub Link above).

public class WebcamTask extends TimerTask {

    public static void main(String[] args) throws IOException {

    }

    // The class below was modified by me in order to allow my webcam to take images and perform additional actions such as saving the captured image.
    @Override
    public void run() {

        Webcam webcam = Webcam.getDefault(); // creates a Webcam object.


        webcam.setViewSize(new Dimension(640, 480)); // creating a new webcam resolution of 640 by 480 pixels.
        webcam.setViewSize(WebcamResolution.VGA.getSize());  // setting the webcam resolution to 640x480 pixels.


        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss"); // setting up the format of the date.
        Date date = new Date(); // creating a new Date object.
        String imageName = String.valueOf(formatter.format(date)); // converting the data into a String format for the name.
        imageName += ".jpg"; // adds the file type to the string imageName.

        webcam.open(); // opening the webcam for use.
        try { // a try catch block in order to handle a possible exception.

            ImageIO.write(webcam.getImage(), "JPG", new File(imageName)); // saves the image as a JPG file format to the specified folder.
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        webcam.close(); // closing the webcam after use.

    }
}
