package com.asking.cosuming.main;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.asking.common.util.ArithUtil;
import com.asking.cosuming.constant.Constant;
import com.asking.cosuming.download.Download;
import com.asking.cosuming.register.Register;
import com.asking.cosuming.request.Request;
import com.asking.cosuming.routing.Routing;
import com.asking.cosuming.util.ReflectUtil;
import com.asking.cusuming.frequency.Frequency;
import com.asking.getting.request.GenerateRequestChain;
import com.asking.getting.request.normal.DefaultRequestChain;
import com.asking.template.Template;

import net.sf.json.JSONObject;

public class InitContext {

	public Register register(String order) {
		
		Register register = Register.build();

		try {
			JSONObject preorder = JSONObject.fromObject(order);

			String taskid = preorder.getString(Constant.TASKID);

			String classRouting = preorder.getJSONObject(Constant.ROUTING).getString(Constant.CLZSS);
			String routingconfig = preorder.getString(Constant.ROUTINGCONFIG);
			Routing routing = ReflectUtil.reflectInitRouting(classRouting, routingconfig);

			String downloadinf = preorder.getJSONObject(Constant.DOWNLOAD).getString(Constant.INF);
			int retrytime = preorder.getInt(Constant.RETRYTIME);
			Download download = ReflectUtil.reflectInitDownload(downloadinf,retrytime);

			TreeSet<GenerateRequestChain> requestChains = ReflectUtil.mergeRequestChain(
					ReflectUtil.reflectInitDefaultRequestChain(Constant.REQUESTCHAIN),
					ReflectUtil.reflectInitRequestChain(
							preorder.getJSONObject(Constant.REQUEST).getString(Constant.INF), Request.class));
			
			if(ArithUtil.odd(requestChains.size())){
				requestChains.add(DefaultRequestChain.build());
			}
			
			String charset = preorder.getString(Constant.CHARSET);
			
			long period = preorder.getLong(Constant.PERIOD);
			long delay = preorder.getLong(Constant.DELAY);
			Frequency frequency = ReflectUtil.reflectFrequencyTimer(preorder.getJSONObject(Constant.FREQUENCY).getString(Constant.INF), period, delay);
			
			Map<String, Template> templates = ReflectUtil.refectTemplates(preorder.getString(Constant.TEMPLATE));

			register.taskid(taskid);
			register.routing(routing);
			register.download(download);
			register.requestChains(requestChains);
			register.retrytime(retrytime);
			register.charset(charset);
			register.frequency(frequency);
			register.templates(templates);
			
			return register;

		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return register;
	}

}
