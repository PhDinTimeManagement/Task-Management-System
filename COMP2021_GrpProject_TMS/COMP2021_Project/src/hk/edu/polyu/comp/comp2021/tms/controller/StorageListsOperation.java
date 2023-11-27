package hk.edu.polyu.comp.comp2021.tms.controller;

import hk.edu.polyu.comp.comp2021.tms.model.StorageLists;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the file operation.
 * The file operation is used to read and write the data to the file.
 * It is consists of two static methods: writeFile and readFile.
 */
public class StorageListsOperation {
    private static StorageLists storageLists = new StorageLists();
    private static final List<StorageLists> storageListsUndo = new ArrayList<>();
    private static final int MAX_UNDO = 20;
    private static final List<StorageLists> storageListsRedo = new ArrayList<>();

    /**
     * Get the storage lists.
     * @return storageLists The storage lists.
     */
    public static StorageLists getStorageLists(){
        return storageLists;
    }

    /**
     * Set the storage lists.
     * @throws Exception The exception to the operation.
     */
    public static void transaction() throws Exception{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(storageLists);
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream in = new ObjectInputStream(bis);
        if(storageListsUndo.size() > MAX_UNDO) storageListsUndo.remove(0);

        //add the current storageLists to undo list
        storageListsUndo.add((StorageLists) in.readObject());

        //after transaction, clear redo list
        storageListsRedo.clear();
    }

    /**
     * Get the storage lists redo.
     * @return storageListsRedo The storage lists redo.
     * @throws Exception The exception to the operation.
     */
    public static String undo() throws Exception {
        if (!storageListsUndo.isEmpty()) {
            try {
                storageListsRedo.add(storageLists);
                storageLists = storageListsUndo.remove(storageListsUndo.size() - 1);
            } catch (Exception e) {
                return "The undo operation has been error.";
            }
        } else throw new Exception("Nothing can be undo.");
        return "The undo operation has been done.";
    }

    /**
     * Get the storage lists redo.
     * @return storageListsRedo The storage lists redo.
     * @throws Exception The exception to the operation.
     */
    public static String redo() throws Exception {
        if (!storageListsRedo.isEmpty()) {
            try {
                storageListsUndo.add(storageLists);
                storageLists = storageListsRedo.remove(storageListsRedo.size() - 1);
            } catch (Exception e) {
                return "The redo operation has been error.";
            }
        } else throw new Exception("Nothing can be redo.");
        return "The redo operation has been done.";
    }

    /**
     * @param storageLists The storage lists to be set.
     */
    public static void setStorageLists(StorageLists storageLists) {
        StorageListsOperation.storageLists = storageLists;
    }
}