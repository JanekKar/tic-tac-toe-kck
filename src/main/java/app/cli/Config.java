package app.cli;

import app.cli.colors.ColorSchema;
import app.cli.colors.DefaultColors;
import app.cli.colors.MonokaiColors;
import app.cli.colors.OceanColors;
import app.cli.controls.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Config {

    private static Config instance;

    public static ColorSchema colorSchema = DefaultColors.getInstance();
    public static Controls controls = DefaultControls.getInstance();

    private Config() {
        try {
            File config = new File(".ttt_config.cfg");
            String tempColors = null, tempControls = null;
            if (!config.createNewFile()){
                Scanner scanner = new Scanner(config);
                if (scanner.hasNext())
                    tempColors = scanner.nextLine();
                if (scanner.hasNext())
                    tempControls = scanner.nextLine();
                scanner.close();
            }

            if(tempColors != null){
                switch (tempColors.toLowerCase()){
                    case "monokai":
                        colorSchema = MonokaiColors.getInstance();
                        break;
                    case "ocean":
                        colorSchema = OceanColors.getInstance();
                        break;
                    default:
                        colorSchema = DefaultColors.getInstance();
                }
            }


            if (tempControls != null){
                switch (tempControls.toLowerCase()){
                    case "wasd":
                        controls = WASDControls.getInstance();
                        break;
                    case "vim":
                        controls = VimControls.getInstance();
                        break;
                    case "ijkl":
                        controls = IJKLControls.getInstance();
                        break;
                    default:
                        controls = DefaultControls.getInstance();
                }
            }

        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public static Config getInstance() {
        if(instance == null)
            instance = new Config();
        return instance;
    }


    public void saveConfig(){
        try {
            File config = new File(".ttt_config.cfg");
            config.createNewFile();
            PrintWriter fileWriter = new PrintWriter(config);

            fileWriter.println(colorSchema.getName());
            fileWriter.println(controls.getName());

            fileWriter.close();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}

