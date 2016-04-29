package com.appman.appmanager.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import java.io.File;

/**
 * Created by Rudraksh on 01-Jan-16.
 */
public class SpaceStatistics {

    // Global Definitions
    private static String APPS = "/storage/emulated/0/data/app";
    private static String ALARMS = Environment.DIRECTORY_ALARMS;
    private static String DCIM =  Environment.DIRECTORY_DCIM;
    private static String DOCUMENTS = Environment.DIRECTORY_DOCUMENTS;
    private static String DOWNLOADS = Environment.DIRECTORY_DOWNLOADS;
    private static String MUSIC = Environment.DIRECTORY_MUSIC;
    private static String MOVIES = Environment.DIRECTORY_MOVIES;
    private static String PICTURES = Environment.DIRECTORY_PICTURES;
    private static String RINGTONES = Environment.DIRECTORY_RINGTONES;


    /**
     * THIS METHOD IS USED TO CALCULATE THE TOTAL SIZE OF APPS FOLDER
     * @param ctx
     * @return size WITH formatter
     */

    public static String getAppsStatistics(Context ctx){
        long size = 0;

        /*try{
            File path = new File(APPS);
            if (path.isFile()){
                size += path.length();
            }else {
                size += path.getTotalSpace();
            }
        }catch (Exception e){
            e.getMessage().toString();
        }*/

        try{
            File path = FileUtils.getPublicDirectory(APPS);
            StatFs fs = new StatFs(path.getPath());
            size = (long)fs.getBlockCount() * fs.getBlockSize();
        }catch (Exception e){
            e.printStackTrace();
        }
        return Formatter.formatFileSize(ctx, size);
    }


    /**
     * THIS METHOD IS USED TO CALCULATE THE TOTAL SIZE OF ALARM FOLDER
     * @param ctx
     * @return size WITH formatter
     */

    public static String getAlarmsStatistics(Context ctx){
        long size = 0;

        /*try{
            File path = new File(ALARMS);
            if (path.isFile()){
                size += path.length();
            }else {
                size += path.getTotalSpace();
            }
        }catch (Exception e){
            e.getMessage().toString();
        }*/

        try{
            File path = FileUtils.getPublicDirectory(ALARMS);
            size = Long.parseLong(FileUtils.getSize(ctx, path));
            return Formatter.formatFileSize(ctx, size);
        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }


    /**
     * THIS METHOD IS USED TO CALCULATE THE TOTAL SIZE OF DCIM FOLDER
     * @param ctx
     * @return size WITH formatter
     */

    public static String getImagesStatistics(Context ctx){
        long size = 0;
        String s;

        /*try{
            File path = new File(DCIM);
            if (path.isFile()){
                size += path.length();
            }else {
                size += path.getTotalSpace();
            }
        }catch (Exception e){
            e.getMessage().toString();
        }*/

        try{
            File path = FileUtils.getPublicDirectory(DCIM);
            //size = Long.parseLong(FileUtils.getSize(ctx, path));
            s = FileUtils.getSize(ctx, path);
            return s;
        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }


    /**
     * THIS METHOD IS USED TO CALCULATE THE TOTAL SIZE OF DOCUMENTS FOLDER
     * @param ctx
     * @return size WITH formatter
     */

    public static String getDocumentsStatistics(Context ctx){
        long size = 0;

        /*try{
            File path = new File(DOCUMENTS);
            if (path.isFile()) {
                size += path.length();
            }
            else {
                size += path.getTotalSpace();
            }
        }catch (Exception e){
            e.getMessage().toString();
        }*/

        try{
            File path = FileUtils.getPublicDirectory(DOCUMENTS);
            size = Long.parseLong(FileUtils.getSize(ctx, path));

        }catch (Exception e){
            e.getMessage();
        }
        return Formatter.formatFileSize(ctx, size);
    }

    /**
     * THIS METHOD IS USED TO CALCULATE THE TOTAL SIZE OF DOWNLOADS FOLDER
     * @param ctx
     * @return size WITH formatter
     */

    public static String getDownloadsStatistics(Context ctx){
        long size = 0;

        /*try{
            File path = new File(DOWNLOADS);
            if (path.isFile()){
                size += path.length();
            }else {
                size += path.getTotalSpace();
            }
        }catch (Exception e){
            e.getMessage().toString();
        }*/

        try{
            File path = FileUtils.getPublicDirectory(DOWNLOADS);
            size = Long.parseLong(FileUtils.getSize(ctx, path));
        }catch (Exception e){
            e.getMessage();
        }
        return Formatter.formatFileSize(ctx, size);
    }


    /**
     * THIS METHOD IS USED TO CALCULATE THE TOTAL SIZE OF MUSIC FOLDER
     * @param ctx
     * @return
     */
    public static String getMusicStatistics(Context ctx){
        long size = 0;

        /*try{
            File path = new File(MUSIC);
            if (path.isFile()){
                size += path.length();
            }else {
                size += path.getTotalSpace();
            }
        }catch (Exception e){
            e.getMessage().toString();
        }*/

        try{
            File path = FileUtils.getPublicDirectory(MUSIC);
            size = Long.parseLong(FileUtils.getSize(ctx, path));
        }catch (Exception e){
            e.getMessage();
        }
        return Formatter.formatFileSize(ctx, size);
    }

    public static String getMoviesStatistics(Context ctx){
        long size = 0;

        /*try{
            File path = new File(MOVIES);
            if (path.isFile()){
                size += path.length();
            }else {
                size += path.getTotalSpace();
            }
        }catch (Exception e){
            e.getMessage().toString();
        }*/

        try{
            File path = FileUtils.getPublicDirectory(MOVIES);
            size = Long.parseLong(FileUtils.getSize(ctx, path));
        }catch (Exception e){
            e.getMessage();
        }
        return Formatter.formatFileSize(ctx, size);
    }

    public static String getPicturesStatistics(Context ctx){
        long size = 0;

        /*try{
            File path = new File(PICTURES);
            if (path.isFile()){
                size += path.length();
            }else {
                size += path.getTotalSpace();
            }
        }catch (Exception e){
            e.getMessage().toString();
        }*/
        try{
            File path = FileUtils.getPublicDirectory(PICTURES);
            size = Long.parseLong(FileUtils.getSize(ctx, path));
        }catch (Exception e){
            e.getMessage();
        }
        return Formatter.formatFileSize(ctx, size);
    }

    public static String getRingtonesStatistics(Context ctx){
        long size = 0;

        /*try{
            File path = new File(RINGTONES);
            if (path.isDirectory()){
                size += path.length();
            }else {
                size += path.getTotalSpace();
            }
        }catch (Exception e){
            e.getMessage().toString();
        }*/

        try{
            File path = FileUtils.getPublicDirectory(RINGTONES);
            size = Long.parseLong(FileUtils.getSize(ctx, path));
        }catch (Exception e){
            e.getMessage();
        }
        return Formatter.formatFileSize(ctx, size);
    }

}
