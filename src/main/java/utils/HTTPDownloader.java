package utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import javax.imageio.ImageIO;

public class HTTPDownloader {
	public String downloadHTML(URI uri, String path) throws IOException {
		String fileName = URIUtils.buildName(uri, path);
		if (fileName.contains(path))
			path = "";
		URL url = uri.toURL();
		URLConnection connection = url.openConnection();
		String encoding = connection.getContentEncoding();
		if(encoding == null)
			encoding = "UTF-8";
		StringBuilder str = new StringBuilder();
		try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding))) {
			try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(path + fileName), 
					Charset.forName(encoding))) {
				String inputLine;
				while ((inputLine = in.readLine()) != null) 
					str.append(inputLine);
				writer.write(str.toString());
			}
		}
		return path+fileName;
	}
	
	public String downloadImage(URI uri, String path) throws IOException {
		System.out.println(uri.toString());
		URL url = uri.toURL();
		BufferedImage image = ImageIO.read(url);
		String[] nameStrings = uri.getPath().split("/");
		String filenameString = nameStrings[nameStrings.length-1];
		ImageIO.write(image, "jpg", new File(path + filenameString));
		return filenameString;
	}
	
}
