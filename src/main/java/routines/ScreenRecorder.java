package routines;

import org.monte.media.Format;
import org.monte.media.FormatKeys.*;
import org.monte.media.Registry;
import org.monte.media.math.Rational;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class ScreenRecorder extends org.monte.screenrecorder.ScreenRecorder {

    private final String name;

    public ScreenRecorder(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat,
                          Format screenFormat, Format mouseFormat, Format audioFormat, File movieFolder,
                          String name) throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        this.name = name;
    }

    @Override
    protected  File createMovieFile(Format fileFormat) throws IOException {
        if (!movieFolder.exists()){
            movieFolder.mkdir();
        }else if(!movieFolder.isDirectory()){
            throw  new IOException("." + movieFolder + "is not a directory");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        return new File(movieFolder,name + "-" + dateFormat.format(new Date()) + "." + Registry.getInstance().getExtension(fileFormat));
    }

    public static ScreenRecorder screenRecorder;

    public static void stopScreenRecording() throws IOException{
        screenRecorder.stop();
    }

    public static void startScreenRecording() throws IOException, AWTException {
        File file = new File("./Video/");
        Dimension screenZise = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenZise.width;
        int height = screenZise.height;

        Rectangle captureSize = new Rectangle(0,0,width,height);

        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

        screenRecorder = new ScreenRecorder(gc, captureSize,
                new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
                        Rational.valueOf(15), QualityKey,1.0f,KeyFrameIntervalKey,15*60),
                new  Format( MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey,
                Rational.valueOf(30)), null,file,"ScreenRecorded"); //Cambiar name por test deseado

        screenRecorder.start();
    }
}

