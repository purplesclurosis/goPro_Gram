
/**
 * @author Cameron Cleary
 * @version (1.0 - 8/8/17 - 5:24 UTC)
 * 
 * Purpose: This program will read a folder and its sub-folders, and delete files. Modifcations will be made from
 * code taken from >>https://stackoverflow.com/questions/32130837/read-and-delete-files-from-folder<< in order that
 * files with the ".MP4" extension (or any modified extensions) be saved, while all other files deleted.
 * 
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GoPro_Gram
{
    public static void main(String[] args) 
    {
        File currentDir = new File("C:\\Users\\Cosmi\\Videos\\GoPro Session"); // current directory
        File[] fileList = displayDirectoryContents(currentDir);
        readAndDeleteFiles(fileList);
    }

    public static File[] displayDirectoryContents(File dir) 
    {
        File[] files = null;
        try 
        {
            files = dir.listFiles();
            for (File file : files) 
            {
                if (file.isDirectory()) 
                {
                    System.out.println("directory:" + file.getCanonicalPath());
                    //recursive call to fetch file list in all levels of sub-directories
                    displayDirectoryContents(file); 
                } 
                else
                    System.out.println("     file:" + file.getCanonicalPath());
            }
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return files;
    }

    public static void readAndDeleteFiles(File[] fileList)
    {
        BufferedReader br = null;
        for(File file : fileList)
        {
            if (!file.isDirectory())
            {
                try 
                {
                    String sCurrentLine;
                    br = new BufferedReader(new FileReader(file.getCanonicalPath()));
                    if (br != null){
                        System.out.println("Reading file : " + file.getCanonicalPath());
                        while ((sCurrentLine = br.readLine()) != null) {
                            System.out.println(sCurrentLine);
                        }
                        br.close();
                        System.out.println("Finished reading file : " + file.getCanonicalPath());
                    }

                    //deleting files without the following extension: .MP4
                    String fileName = new String();
                    fileName = file.getName();
                    int fileLength = file.getName().length();

                    if (!fileName.substring(fileLength -1, fileLength - 5).equals(".MP4"))
                    {
                        if(file.delete())
                            System.out.println(file.getCanonicalPath() + " is deleted!");
                        else
                            System.out.println(file.getCanonicalPath() + " could not be deleted!"); 
                    }
                } 
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            else
            //recursive call to fetch file list in all levels of sub-directories and then read & delete each of them
                readAndDeleteFiles(file.listFiles());
        }
    }
}   