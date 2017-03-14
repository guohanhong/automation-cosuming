package com.asking.cosuming.thread;

import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import com.asking.cosuming.download.Download;
import com.asking.getting.constant.Constant;
import com.asking.getting.request.GenerateRequestChain;
import com.asking.getting.request.Request;
import com.asking.template.Template;

public class RequestTask implements Runnable {

	public RequestTask() {

	}

	public static RequestTask build() {
		return new RequestTask();
	}

	private ThreadLocal<Download> locals;

	public void locals(ThreadLocal<Download> locals) {
		this.locals = locals;
	}

	public Download localsValue() {
		return this.locals.get();
	}

	private Request request;

	public Request request() {
		return this.request;
	}

	public void request(Request request) {
		this.request = request;
	}

	private TreeSet<GenerateRequestChain> requestChains;

	public void requestChains(TreeSet<GenerateRequestChain> requestChains) {
		this.requestChains = requestChains;
	}

	public TreeSet<GenerateRequestChain> requestChains() {
		return requestChains;
	}

	private String charset;

	public void charset(String charset) {
		this.charset = charset;
	}

	public String charset() {
		return charset;
	}

	@Override
	public void run() {
		CloseableHttpClient client = localsValue().download();

		String extract = null;
		
		String protocol = request.protocol();
		try {
			switch (protocol) {
			case Constant.PROTOCOL_POST: {
				extract = handlePost(request, client);
				break;
			}
			case Constant.PROTOCOL_GET: {
				extract = handleGet(request, client);
				break;
			}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(extract);
	}

	protected String handleGet(Request request, CloseableHttpClient client)
			throws ClientProtocolException, IOException {
		String extract = null;
	
		String protocol = request.protocol();
		Template template = request.template();

		Iterator<GenerateRequestChain> itr = requestChains.iterator();
		while (itr.hasNext()) {
			GenerateRequestChain requestChain = itr.next();
			requestChain.doRequest(request);
		}

		HttpGet requestget = (HttpGet) request.handlerProtocol();

		CloseableHttpResponse response = client.execute(requestget);
		HttpEntity entity = response.getEntity();
		String html = null;
		if (entity != null) {
			html = EntityUtils.toString(entity, charset);
			extract = template.extract(html);
		}
		return extract;
	}

	protected String handlePost(Request request, CloseableHttpClient client)
			throws ClientProtocolException, IOException {
		String extract = null;
		
		String protocol = request.protocol();
		Template template = request.template();

		Iterator<GenerateRequestChain> itr = requestChains.iterator();
		while (itr.hasNext()) {
			GenerateRequestChain requestChain = itr.next();
			requestChain.doRequest(request);
		}

		HttpPost requestget = (HttpPost) request.handlerProtocol();

		CloseableHttpResponse response = client.execute(requestget);
		HttpEntity entity = response.getEntity();
		String html = null;
		if (entity != null) {
			html = EntityUtils.toString(entity, charset);
			extract = template.extract(html);
		}
		return extract;

	}

}
