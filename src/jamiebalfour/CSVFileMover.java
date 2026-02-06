package jamiebalfour;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVFileMover {

  static boolean doIt = true;

  public boolean copyFile(String sourcePath, String destPath) {
    Path source = Paths.get(sourcePath);
    Path destination = Paths.get(destPath);

    try{
      Files.copy(
              source,
              destination,
              StandardCopyOption.REPLACE_EXISTING,
              StandardCopyOption.COPY_ATTRIBUTES
      );
      return true;
    } catch (IOException e){
      return false;
    }

  }

  public CSVFileMover(String inputDirectory, String[] files, String[] outputDirectories, String appendix, String outputFolderPath) {

    for (int i = 0; i < files.length; i++) {
      String outputPath = outputDirectories[i];

      if(!outputFolderPath.isEmpty()){
        if(!outputFolderPath.endsWith("/")){
          outputPath = outputFolderPath + "/" + outputPath;
        } else{
          outputPath = outputFolderPath + outputPath;
        }

      }

      File actualPath = new File(inputDirectory + "/" + files[i] + appendix);
      File outputFile = new File(outputPath + "/" + files[i] + appendix);

      int no = 1;
      while(outputFile.exists()){
        outputFile = new File(outputPath + "/" + files[i] + " copy " + no + appendix);
      }

      /*try {
        CSVFileMover.writeFile(actualPath.getAbsolutePath(), "t", false);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }*/


      if (!actualPath.exists()) {
        System.err.println("File does not exist: " + actualPath);
      } else {
        if (doIt) {
          if (!new File(outputPath).exists()) {
            new File(outputPath).mkdirs();
          }


          //Move the file to the new directory
          //boolean result = actualPath.renameTo(outputFile);

          boolean result = copyFile(actualPath.getAbsolutePath(), outputPath);


          if (!result) {
            System.err.println("Failed to copy file: " + actualPath + " to " + outputFile);
          } else {
            System.out.println("Copied file: " + actualPath.toString() + " to " + outputFile);
          }
        } else {
          System.out.println("Would copy file: " + actualPath);
          System.out.println("To file: " + outputFile);
        }

      }


    }

  }

  public static void writeFile(String filename, String content, boolean append) throws IOException {
    String[] contents = new String[1];
    contents[0] = content;
    writeFile(filename, contents, append);
  }

  public static void writeFile(String filename, String[] content, boolean append) throws IOException {
    PrintWriter out;
    out = new PrintWriter(new BufferedWriter(new FileWriter(filename, append)));
    for (int i = 0; i < content.length; i++) {
      out.print(content[i]);
    }
    out.close();
  }

  public static void main(String[] args) {

    String appendix = "";
    String outputFolderPath = "";

    if (args.length == 0) {
      System.out.println("Usage: inputDirectory csvFile [fileAppendix] [outputFolderPath]");
    }

    if (!(args.length >= 2)) {
      System.err.println("Please provide a directory to read from as the first argument.");

      System.exit(1);
    }

    if (args.length >= 3) {
      appendix = args[2];
    }

    if (args.length >= 4) {
      outputFolderPath = args[3];
    }

    if(args.length >= 5){
      doIt = false;
    }

    String inputDirectory = args[0];

    System.out.println("Input directory: " + inputDirectory);

    String csvFile = args[1];

    System.out.println("CSV file: " + csvFile);

    List<String> filesList = new ArrayList<>();
    List<String> outputDirsList = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
      String line;

      while ((line = br.readLine()) != null) {
        // Skip empty lines
        if (line.trim().isEmpty()) continue;

        String[] parts = line.split(",", -1);

        if (parts.length != 2) {
          throw new IllegalArgumentException("Invalid CSV line: " + line);
        }

        filesList.add(parts[0].trim());
        outputDirsList.add(parts[1].trim());
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    // Convert to arrays
    String[] files = filesList.toArray(new String[0]);
    String[] outputDirs = outputDirsList.toArray(new String[0]);

    System.out.println("You have selected the following files: " + Arrays.toString(files));

    System.out.println("Would you like to copy these files? (y/n)");

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    try {
      if(in.readLine().equals("y")){
        CSVFileMover csvFileMover = new CSVFileMover(inputDirectory, files, outputDirs, appendix, outputFolderPath);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }


  }

}
