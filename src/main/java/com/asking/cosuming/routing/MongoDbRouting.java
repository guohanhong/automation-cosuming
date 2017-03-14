package com.asking.cosuming.routing;

import org.bson.Document;

import com.asking.cosuming.constant.Constant;
import com.asking.cosuming.routing.LastModified;
import com.asking.cosuming.routing.Routing;
import com.asking.cosuming.util.ParserTools;
import com.asking.getting.request.Request;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;

import net.sf.json.JSONObject;

public class MongoDbRouting extends LastModified implements Routing {

	private MongoClient client;

	private MongoCursor<Document> itr;

	public MongoDbRouting() {

	}

	public MongoDbRouting(String config) {
		JSONObject configjf = JSONObject.fromObject(config);
		String mongouri = configjf.getString(Constant.MONGOURI);
		String query = configjf.getString(Constant.MONGOQUERY);
		String database = configjf.getString(Constant.DATABASE);
		String collection = configjf.getString(Constant.COLLECTION);

		client = new MongoClient(mongouri);
		itr = client.getDatabase(database).getCollection(collection).find(Document.parse(query)).iterator();
	}

	@Override
	public Request routing() {
		modified();		//垃圾回收机制标记
		while (itr.hasNext()) {
			Document document = itr.next();
			Request request = ParserTools.toRequest(document);
			// documetn to request
			return request;
		}
		return null;
	}

	@Override
	public void modified() {
		super.modified = System.currentTimeMillis();
	}

	@Override
	public void close() {
		
	}
	
}
