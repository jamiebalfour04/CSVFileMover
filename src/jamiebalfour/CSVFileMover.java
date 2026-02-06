package jamiebalfour;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVFileMover {

  boolean doIt = false;

  public CSVFileMover(String inputDirectory, String[] files, String[] outputDirectories, String appendix, String outputFolderPath) {

    for (int i = 0; i < files.length; i++) {
      File actualPath = new File(inputDirectory + "/" + files[i] + appendix);
      File outputPath = new File(outputFolderPath + outputDirectories[i] + "/" + files[i]);

      /*try {
        CSVFileMover.writeFile(actualPath.getAbsolutePath(), "t", false);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }*/


      if (!actualPath.exists()) {
        System.err.println("File does not exist: " + actualPath);
      } else {
        if (doIt) {
          if (new File(outputFolderPath + outputDirectories[i]).exists()) {
            new File(outputDirectories[i]).mkdirs();
          }


          //Move the file to the new directory
          boolean result = actualPath.renameTo(outputPath);


          if (!result) {
            System.err.println("Failed to move file: " + actualPath.toString());
          } else {
            System.out.println("Moved file: " + actualPath.toString());
          }
        } else {
          System.out.println("Would move file: " + actualPath);
          System.out.println("To directory: " + outputPath);
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

    CSVFileMover csvFileMover = new CSVFileMover(inputDirectory, files, outputDirs, appendix, outputFolderPath);
  }

}
