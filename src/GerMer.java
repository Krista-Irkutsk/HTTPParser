import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GerMer {
	public static void main(String[] args) throws IOException {
		String url = "http://open.irkobl.ru/mayors/";
		//		Validate.isTrue(args.length == 1, "usage: supply url to fetch");

		//		print("%s;", url);

		Document doc = Jsoup.connect(url).get();
		Elements links = doc.select("a[href]");
		//		Elements media = doc.select("[src]");
		//		Elements imports = doc.select("link[href]");

		Elements officialname = doc.getElementsByClass("official_name");
		Elements officialcontacts = doc.getElementsByClass("official_contacts");

		String oldurl = "";
		for (Element link : links) {
			if (link.attr("abs:href").contains("mayors")) {
				if (!link.attr("abs:href").equals(oldurl)) {
					println(GetMer((link.attr("abs:href"))));
				}
				oldurl = link.attr("abs:href");
			}
		}

		println(";");
	}

	private static String GetMer(String filename) throws IOException {
		String result = filename + ";";

		Document doc = Jsoup.connect(filename).get();

		Elements officialname = doc.getElementsByClass("official_name");
		Elements officialcontacts = doc.getElementsByClass("official_contacts");

		for (Element offname : officialname) {

			Elements fiomer = offname.getElementsByTag("h2");
			Elements raion = offname.getElementsByTag("a");

			result = result + (fiomer.text() + ";");
			result = result + (raion.text() + ";");

		}
		//		println("\nofficial_contacts: (%d)", officialcontacts.size());
		for (Element offcontc : officialcontacts) {
			Elements elements = offcontc.getElementsByTag("p");
			for (Element element : elements) {
				result = result + (element.text() + ";");
			}
		}
		return result;
	}

	private static void print(String msg, Object... args) {
		System.out.print(String.format(msg, args));
	}

	private static void println(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}
}