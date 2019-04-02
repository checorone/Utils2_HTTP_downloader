package utils;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Scanner;

import javax.xml.namespace.QName;

/**
 * HTTPDownloader App!
 *
 */
public class App 
{
	public static void main( String[] args ) throws IOException, URISyntaxException 
	{
		String path = "";
		String uri = "";
		switch (args.length) {
		case 0:
			System.err.println("Nothing to do... Closing.");
			break;
		case 1:
			uri = args[0];
			break;
		case 2:
			uri = args[0];
			path = args[1];
			if (!path.endsWith("/"))
				path += "/";
			if(URIUtils.isDirectoryExists(path)) {
					if(URIUtils.isFileExists(path + URIUtils.buildName(new URI(uri), path))) {
						System.out.println("File with same name already exists");
						System.out.print("Proceed with replace? (y/n) ");
						Scanner scanner = new Scanner(System.in);
						String answerString = scanner.next();
						if(answerString.equals("y")) {
							break;
						}
						else if(answerString.equals("n")) {
							System.err.println("Negative answer. Aborting...");
							return;
						}
						else {
							System.err.println("Unknown answer. Aborting...");
							return;
						}
					}
			}
			else {
				path = path.replace("/", "_");
			}
			break;
		default:
			System.err.println("Usage: downloader uri path");
			break;
		}
		HTTPDownloader downloader = new HTTPDownloader();
		String pathToFile = downloader.downloadHTML(new URI(uri), path);
		HTTPParser parser = new HTTPParser();
		String html = new String(Files.readAllBytes(Paths.get(pathToFile)));
		LinkedList<URI> imgList = parser.parseImg(html, uri);
		if (imgList.isEmpty()) {
			System.err.println("no img");
		}
		String[] nameStrings = pathToFile.split("/");
		String prefix = nameStrings[nameStrings.length-1] + "_files/";
		if (!pathToFile.contains("/"))
			path = "";
		String pathtofiles = path + prefix;
		new File(pathtofiles).mkdir();
		for(URI imgUri : imgList) {
			downloader.downloadImage(imgUri, pathtofiles);
		}
		String newhtmlString = parser.changeUri(html, prefix);
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(pathToFile), "utf-8"))) {
			writer.write(newhtmlString);
		}
		File file = new File(pathToFile);
		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
		    Desktop.getDesktop().browse(file.toURI());
		}
	}
	
	
}
