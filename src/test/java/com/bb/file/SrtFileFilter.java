package com.bb.file;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by admin on 2016/11/23.
 */
public class SrtFileFilter implements FileFilter {

    /**
     * @param args
     * @author Ben Zeph
     */
    String condition = ".srt";

    public SrtFileFilter(String condition) {
        this.condition = condition;
    }

    @Override
    public boolean accept(File pathname) {
        // TODO Auto-generated method stub
        String filename = pathname.getName();
        if (filename.lastIndexOf(condition) != -1) {
            return true;
        } else
            return false;
    }

}
