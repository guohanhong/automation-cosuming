package com.asking.cosuming.download;

import org.apache.http.impl.client.CloseableHttpClient;

public interface Download {
	
	public CloseableHttpClient download();
		
}
