package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class Util {
	public static String getIndexFromFormattedSequence(String sequenceStr) {
		String[] strArray = sequenceStr.split(":");
		return strArray[0];
	}
	
	public static String getSequenceFromFormattedSequence(String sequenceStr) {
		String[] strArray = sequenceStr.split(":");
		return strArray[1];
	}
	
	public static boolean isNumber(String content) {
		if (content == null) {
			return false;
		}
		content = content.trim();
		if (content.equals("")) {
			return false;
		}
		for (int i = 0; i <= content.length() - 1; i++) {
			if (i == 0 && content.charAt(i) == '-') {
				continue;
			}
			char c = content.charAt(i);
			if (!(c >= '0' && c <= '9')) {
				return false;
			}
		}
		return true;
	}
	
	// delete all sub-files and sub-folders, then the folder itself
	// also works for a single file
	public static void delFileOrFolder(String absFileOrFolderName) throws IOException {
	    File file = new File(absFileOrFolderName);
	    if (!file.exists()) {
	    	throw new IOException("not exist.");
	    }
	    if (file.isFile()) {
	    	file.delete();
	    	return;
	    }
	    if (file.isDirectory()) {
	    	String[] fileList = file.list();
	 	    for (int i = 0; i <= fileList.length-1; i++) {
	 	    	// generate absFileName
	 	    	String absFileName;
	 	    	if (absFileOrFolderName.endsWith("\\")) {
	 	    		absFileName = absFileOrFolderName + fileList[i];
	 	        } else {
	 	        	absFileName = absFileOrFolderName + "\\" + fileList[i];
	 	        }
	 	    	delFileOrFolder(absFileName);
	 	    }
	 	    file.delete();
	    }
	    return;
	}
	
	// copy one file to a specified place
	public static boolean copyFile(String absSourceFilePath, String absDestFilePath) {
		File sourceFile = new File(absSourceFilePath);
		File destFile = new File(absDestFilePath);
		if (!sourceFile.exists()) {
			return false;
		}
		if (destFile.exists()) {
			try {
				Util.delFileOrFolder(absDestFilePath);
				destFile.createNewFile();
			} catch (IOException e) {
				return false;
			}
		}
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(sourceFile);
            fo = new FileOutputStream(destFile);
            in = fi.getChannel();
            out = fo.getChannel();
            in.transferTo(0, in.size(), out);
        } catch (IOException e) {
            return false;
        } finally {
            try {
            	in.close();
                fi.close();
                out.close();
                fo.close();                
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }
}
