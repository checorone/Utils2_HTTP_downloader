package utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTTPParser {
	public  LinkedList<URI> parseImg(String html, String baseUri) throws URISyntaxException {
		Document doc = Jsoup.parse(html);
		doc.setBaseUri(baseUri);
		Elements imgs = doc.select("img");
		
		if (imgs == null) {
			System.err.println("No imgs");
			return null;
		}
		LinkedList<URI> list = new LinkedList<URI>();
		for (Element img : imgs) {
			String imgLink = img.absUrl("src"); 
			String imgText = img.text(); 
			System.out.println(imgLink + "  " + imgText);
			list.add(new URI(imgLink));
		}
		return list;
	}
	public String changeUri(String html, String prefix) {
		Document doc = Jsoup.parse(html);
		Elements imgs = doc.select("img");
		for (Element element : imgs) {
			String prevString = element.attr("src");
			element.attr("src", prefix + prevString);
		}
		return doc.html();
	}
}
