import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

import net.sf.json.JSONObject;

public class RequestGernerateMain {

	public static void main(String[] args) {
		JSONObject request = new JSONObject();
		request.put("protocol", "get");
		request.put("url",
				"https://item.jd.com/11183230975.html");
		request.put("host", null);
		request.put("referer", null);
		request.put("template", null);
		request.put("ip", null);
		request.put("keyword", "com.asking.template.tb.ProductTemplate");

		MongoClient client = new MongoClient("192.168.1.191:21000");
		MongoCollection<Document> collection = client.getDatabase("sharkCloud_57b81c3d0e645fe41c8b4567")
				.getCollection("test");
		
		collection.insertOne(new Document().parse(request.toString()));
	}
}
