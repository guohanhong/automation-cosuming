package com.asking.cosuming.register;

import java.util.TreeSet;

import com.asking.cosuming.download.Download;
import com.asking.cosuming.routing.Routing;
import com.asking.cusuming.frequency.Frequency;
import com.asking.getting.request.GenerateRequestChain;
import com.asking.template.Template;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;

public class Register {
	
	public static Register build(){
		return new Register();
	}
	
	public boolean exists(){
		return taskid != null && !taskid.equals("") ? true : false ;
	}

	private String taskid;

	public String taskid() {
		return taskid;
	}

	public void taskid(String taskid) {
		this.taskid = taskid;
	}

	private Routing routing;

	public void routing(Routing routing) {
		this.routing = routing;
	}

	public Routing routing() {
		return routing;
	}

	private Download download;

	public void download(Download download) {
		this.download = download;
	}

	public Download download() {
		return this.download;
	}

	private TreeSet<GenerateRequestChain> requestChains = Sets.<GenerateRequestChain> newTreeSet();

	public void requestChains(TreeSet<GenerateRequestChain> requestChains) {
		this.requestChains = requestChains;
	}

	public TreeSet<GenerateRequestChain> requestChains() {
		return this.requestChains;
	}

	private int retrytime;

	public int retrytime() {
		return this.retrytime;
	}

	public void retrytime(int retrytime) {
		this.retrytime = retrytime;
	}

	private String charset;

	public void charset(String charset) {
		this.charset = charset;
	}

	public String charset() {
		return charset;
	}

	private Frequency frequency; 
	
	public void frequency(Frequency frequency){
		this.frequency = frequency;
	}
	public Frequency frequency(){
		return this.frequency;
	}
	
	private Map<String, Template> templates = null ;
	
	public void templates(Map<String, Template> templates){
		this.templates = templates;
	}
	public Map<String, Template> templates(){
		return this.templates;
	}

}
