package hk.edu.polyu.comp.comp2021.tms.controller;

import hk.edu.polyu.comp.comp2021.tms.model.StorageLists;

import java.io.*;

/**
 * This class represents the file operation.
 * The file operation is used to read and write the data to the file.
 * It is consists of two static methods: writeFile and readFile.
 */
public class FileOperation {
    /**
     * Write the data to the file.
     * @param storageLists The data to be stored.
     * @param filePath     The file path.
     * @return The message of the operation.
     * @throws Exception The exception to the operation.
     */
    public static String writeFile(StorageLists storageLists, String filePath) throws Exception {
        try {
            ObjectOutputStream ObjOut;
            try {
                FileOutputStream fileOut = new FileOutputStream(filePath);
                ObjOut = new ObjectOutputStream(fileOut);
            } catch (Exception e) {
                return "not find file!";
            }
            ObjOut.writeObject(storageLists);
        } catch (FileNotFoundException e) {
            throw new Exception("The file path is illegal.");
        } catch (IOException e) {
            throw new Exception("IO exception.");
        }
        return "The data has been stored to the file.";
    }

    /**
     * Read the data from the file.
     * @param filePath The file path.
     * @return The message of the operation.
     * @throws Exception The exception to the operation.
     */
    public static String readFile(String filePath) throws Exception {
        try {
            ObjectInputStream ObjIn;
            try {
                FileInputStream fileIn = new FileInputStream(filePath);
                ObjIn = new ObjectInputStream(fileIn);
            } catch (Exception e) {
                return "not find file!";
            }
            StorageListsOperation.transaction();
            StorageListsOperation.setStorageLists((StorageLists) ObjIn.readObject());
        } catch (FileNotFoundException e) {
            throw new Exception("The file cannot be found.");
        } catch (IOException e) {
            throw new Exception("IO exception.");
        } catch (ClassNotFoundException e) {
            throw new Exception("The file content is unreadable.");
        }
        return "File has been loaded";
    }
}