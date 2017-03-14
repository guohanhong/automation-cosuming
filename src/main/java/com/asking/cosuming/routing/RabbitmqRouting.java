package com.asking.cosuming.routing;

import com.asking.cosuming.routing.LastModified;
import com.asking.cosuming.routing.Routing;
import com.asking.getting.request.Request;

import net.sf.json.JSONObject;

public class RabbitmqRouting extends LastModified implements Routing{
	
	public RabbitmqRouting(){
		
	}
	
	public RabbitmqRouting(String config){
		JSONObject configjf = JSONObject.fromObject(config);
		
		
	}
	@Override
	public Request routing() {
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
