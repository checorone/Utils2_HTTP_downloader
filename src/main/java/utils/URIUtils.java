package utils;

import java.io.File;
import java.net.URI;

public class URIUtils {
	public static String buildName(URI uri, String path) {
		String oldpathString = path;
		String fileName;
		if (isDirectoryExists(path)) 
			fileName = "";
		else 
			fileName = oldpathString;
		String  hostname = uri.getHost();
		String[] nameStrings = uri.getPath().split("/");
		if(nameStrings[nameStrings.length-1].equals(hostname))
			fileName += "index.html";
		else
			fileName += nameStrings[nameStrings.length-1];
		return fileName;
	}
	
	public static boolean isDirectoryExists(String path) {
		File f = new File(path);
		if (f.exists() && f.isDirectory())
			return true;
		else return false;
	}
	
	public static boolean isFileExists(String path) {
		File f = new File(path);
		if (f.exists() && f.isFile())
			return true;
		else return false;
	}
}
