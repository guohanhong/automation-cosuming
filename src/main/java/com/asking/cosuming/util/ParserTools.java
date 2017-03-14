package com.asking.cosuming.util;

import org.bson.Document;

import com.asking.cosuming.constant.Constant;
import com.asking.getting.request.Request;


public class ParserTools {
	
	public static Request toRequest(Document document){
		Request request = Request.build();
		
		String protocol = document.getString(Constant.PROTOCOL);
		String url = document.getString(Constant.URL);
		String host = document.getString(Constant.HOST);
		String referer = document.getString(Constant.REFERER);
		String ip = document.getString(Constant.IP);
		String keyword = document.getString(Constant.KEYWORD);
		if( protocol != null ) request.protocol(protocol);
		if( url != null ) request.url(url);
		if( host != null ) request.host(host);
		if( referer != null ) request.referer(referer);
		if( ip != null ) request.ip(ip);
		if( keyword != null ) request.keyword(keyword);
		
		return request;
	}

	

}
