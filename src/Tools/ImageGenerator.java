package Tools;

import GUI.Renderer;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Locale;

public class ImageGenerator {

    public static void main(String[] args) throws IOException {

        File dir = new File("src/assets/textures/block");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                System.out.println(child.getName());
                String path = child.getPath();
                StringBuilder varName = new StringBuilder();
                String[] wordlist = child.getName().replace(".png", "").split("_");

                for(String word : wordlist) {
                    word = word.replace("_", "");

                    StringBuilder finalWord = new StringBuilder("");

                    char[] charArray = word.toCharArray();

                    int index = 0;
                    for(char c : charArray) {
                        String Char = String.valueOf(c);

                        if(index == 0) {
                            Char = Char.toUpperCase();
                        }

                        finalWord.append(Char);

                        index ++;
                    }

                    if(finalWord != null) {
                        varName.append(finalWord);
                    }

                }

                //path = '"' + path + '"';

                Path filePath = Path.of("demo.txt");
                String content = Files.readString(filePath);
                //String value =  "public static Image " + varName + " = Renderer.CreateImage(" + "\"" + path.replace("\\", "\\\\") + "\"" + ");" + "\n";
                String value =  "public static Image " + varName + ";" + "\n";
                //String value =  varName + " = Renderer.CreateImage(" + "\"" + path.replace("\\", "\\\\") + "\"" + ");" + "\n";
                //String value = varName + " = Renderer.ScaleImage(Renderer.CreateImage(\"" + path.replace("\\", "\\\\") + "\"), new Dimension(1, 1));" + "\n";
                if(value.contains("mcmeta")) value = "";
                content  = content + value;

                Files.writeString(filePath, content);
            }
        }
    }
}
