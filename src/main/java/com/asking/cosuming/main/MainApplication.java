package com.asking.cosuming.main;

import org.bson.Document;

import com.asking.cosuming.constant.Constant;
import com.asking.cosuming.register.Register;

import net.sf.json.JSONObject;

/**
 * <register> <taskid></taskid> <routing></routing> <download></download>
 * <request></request> <retrytime></retrytime> <charset></charset> </register>
 * 
 * @author guohanhong
 *
 */

public class MainApplication {

	public static void main(String[] args) {
		JSONObject register = new JSONObject();
		JSONObject clazz = new JSONObject();
		register.put(Constant.TASKID, "淘宝电器销量");
		clazz.put(Constant.CLZSS, "com.asking.cosuming.routing.MongoDbRouting");
		register.put(Constant.ROUTING, clazz);
		JSONObject inf = new JSONObject();
		inf.put(Constant.INF, "com.asking.cosuming.tb");
		register.put(Constant.DOWNLOAD, inf);
		inf = new JSONObject();
		inf.put(Constant.INF, "com.asking.cosuming.tb");
		register.put(Constant.REQUEST, inf);
		register.put(Constant.RETRYTIME, 3);
		register.put(Constant.CHARSET, Constant.UTF8);
		JSONObject routingconfig = new JSONObject();

		String mongouri = "192.168.1.191:21000";
		String query = new Document().toJson();
		String database = "sharkCloud_57b81c3d0e645fe41c8b4567";
		String collection = "test";
		routingconfig.put(Constant.MONGOURI, mongouri);
		routingconfig.put(Constant.MONGOQUERY, query);
		routingconfig.put(Constant.DATABASE, database);
		routingconfig.put(Constant.COLLECTION, collection);
		
		register.put(Constant.ROUTINGCONFIG, routingconfig);
		
		inf = new JSONObject();
		inf.put(Constant.INF, "com.asking.cosuming.tb");
		register.put(Constant.FREQUENCY, inf);
		register.put(Constant.PERIOD, 1000L);
		register.put(Constant.DELAY, 10*1000L);
		
		register.put(Constant.TEMPLATE, "com.asking.template.tb");
		
		
		InitContext initContext = new InitContext();
		Register registerMachine = initContext.register(register.toString());
		
		RunTimeEnvironment runtime = new RunTimeEnvironment();	
		runtime.runTime(registerMachine);
	}
}
