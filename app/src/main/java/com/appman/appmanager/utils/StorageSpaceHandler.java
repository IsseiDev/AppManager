package com.appman.appmanager.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Created by rudhraksh.pahade on 21-12-2015.
 */

/**
 * THIS CLASS IS USED TO CALCULATE THE STORAGE PERCENTAGE
 * OF DEVICE STORAGE SPACE.
 */
public class StorageSpaceHandler {

    // Total Space
    public static float getInternalStorageSpace() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
        //StatFs statFs = new StatFs("/data");
        float total = ((float)statFs.getBlockCount() * statFs.getBlockSize()) / 1048576;
        return total;
    }

    // Free Space
    public static float getInternalFreeSpace() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
        //StatFs statFs = new StatFs("/data");
        float free  = ((float)statFs.getAvailableBlocks() * statFs.getBlockSize()) / 1048576;
        return free;
    }

    // Occupied Space
    public static float getInternalUsedSpace() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
        //StatFs statFs = new StatFs("/data");
        float total = ((float)statFs.getBlockCount() * statFs.getBlockSize()) / 1048576;
        float free  = ((float)statFs.getAvailableBlocks() * statFs.getBlockSize()) / 1048576;
        float busy  = total - free;
        return busy;
    }

    public static float getExternalStorageSpace(){
        /*File path = Environment.getExternalStorageDirectory();
        StatFs fs = new StatFs(path.getPath());
        float total = ((float)fs.getBlockCount() * fs.getBlockSize()) / 1048576;
        return total;*/

        File path = getExternalStorage();
        StatFs fs = new StatFs(path.getPath());
        float total = ((float)fs.getBlockCount() * fs.getBlockSize()) / 1048576;
        return total;
    }

    public static float getExternalUsedSpace(){
        /*File path = Environment.getExternalStorageDirectory();
        StatFs fs = new StatFs(path.getPath());
        float total = ((float)fs.getBlockCount() * fs.getBlockSize()) / 1048576;
        float free  = ((float)fs.getAvailableBlocks() * fs.getBlockSize()) / 1048576;
        float busy  = total - free;
        return busy;*/

        File path = getExternalStorage();
        StatFs fs = new StatFs(path.getPath());
        float total = ((float)fs.getBlockCount() * fs.getBlockSize()) / 1048576;
        float free  = ((float)fs.getAvailableBlocks() * fs.getBlockSize()) / 1048576;
        float busy  = total - free;
        return busy;
    }

    public static File getExternalStorage() {
        //returns the path to the external storage or null if it doesn't exist
        String path = System.getenv("SECONDARY_STORAGE");
        return path!=null ? new File(path) : null;
    }


}
