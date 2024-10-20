package tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Config extends Properties{
    public Config()
    {
        try{
            load(new FileInputStream("C:\\Users\\user\\IdeaProjects\\nsu\\game\\Game\\src\\tools\\config.properties"));
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
