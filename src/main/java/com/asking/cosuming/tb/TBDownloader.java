package com.asking.cosuming.tb;

import java.io.IOException;

import org.apache.http.HttpRequest;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;

import com.asking.cosuming.download.Download;

public class TBDownloader implements Download {

	private CloseableHttpClient client;

	public TBDownloader(Integer retrytime) {
		final int retrytimeconst = retrytime;
		CookieStore cookieStore = new BasicCookieStore();
        StandardHttpRequestRetryHandler standardHandler = new StandardHttpRequestRetryHandler(5, true);
		HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler(){

			@Override
			public boolean retryRequest(IOException e, int retryTimes, HttpContext context) {
				HttpClientContext clientContext = HttpClientContext.adapt(context);
				HttpRequest request = clientContext.getRequest();
				return false;
			}
			
		};
		client = HttpClients.custom().setDefaultCookieStore(cookieStore).setRetryHandler(retryHandler).build();
	}

	@Override
	public CloseableHttpClient download() {
		return client;
	}

}
