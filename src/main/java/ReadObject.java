package readObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadObject {
	Properties p = new Properties();
	public Properties getProperties() throws IOException{
		InputStream stream = new FileInputStream(new File(System.getProperty("user.dir")+"\\src\\main\\java\\properties\\object.properties"));
        //load all objects
        p.load(stream);
         return p;
	}
}
