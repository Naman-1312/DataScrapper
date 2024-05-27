package dataScrapper;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class RawFile {

	public static void main (String [] args) {
		String url = "https://kivihealth.com/jaipur/doctors";
		try {
			Document document = Jsoup.connect(url).get();
			Elements doctorData = document.select(".img-responsive docProfilePic");
		for (Element dD : doctorData) {
			String profileImage = dD
			
		}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
