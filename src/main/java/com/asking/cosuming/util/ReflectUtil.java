package com.asking.cosuming.util;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.asking.cosuming.download.Download;
import com.asking.cosuming.routing.Routing;
import com.asking.cusuming.frequency.Frequency;
import com.asking.getting.request.GenerateRequestChain;
import com.asking.template.Template;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;

public class ReflectUtil {

	public static Routing reflectInitRouting(String className, String config)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		Routing routing = (Routing) Class.forName(className).getConstructor(String.class).newInstance(config);
		return routing;
	}

	public static Download reflectInitDownload(String packageName, int retrytime)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		String[] otherPackageName = new String[1];
		Lists.newArrayList(packageName).toArray(otherPackageName);
		FastClasspathScanner clazzScaner = new FastClasspathScanner(otherPackageName);
		ScanResult result = clazzScaner.scan();
		List<String> classNames = result.getNamesOfClassesImplementing(Download.class);
		Download download = (Download) Class.forName(classNames.get(0)).getConstructor(Integer.class)
				.newInstance(new Integer(retrytime));
		return download;
	}

	public static List<GenerateRequestChain> reflectInitDefaultRequestChain(String packageName)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		List<GenerateRequestChain> requestChains = Lists.<GenerateRequestChain> newArrayList();
		String[] otherPackageName = new String[1];
		Lists.newArrayList(packageName).toArray(otherPackageName);
		FastClasspathScanner clazzScaner = new FastClasspathScanner(otherPackageName);
		ScanResult result = clazzScaner.scan();
		List<String> classNames = result.getNamesOfClassesImplementing(GenerateRequestChain.class);
		for (String name : classNames) {
			GenerateRequestChain chain = (GenerateRequestChain) Class.forName(name).getConstructor().newInstance();
			requestChains.add(chain);
		}
		return requestChains;
	}

	public static List<GenerateRequestChain> reflectInitRequestChain(String packageName, Class<?> inf)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		List<GenerateRequestChain> requestChains = Lists.<GenerateRequestChain> newArrayList();
		String[] otherPackageName = new String[1];
		Lists.newArrayList(packageName).toArray(otherPackageName);
		FastClasspathScanner clazzScaner = new FastClasspathScanner(otherPackageName);
		ScanResult result = clazzScaner.scan();
		List<String> classNames = result.getNamesOfSubclassesOf(inf);
		for (String name : classNames) {
			GenerateRequestChain chain = (GenerateRequestChain) Class.forName(name).getConstructor().newInstance();
			requestChains.add(chain);
		}
		return requestChains;
	}

	public static TreeSet<GenerateRequestChain> mergeRequestChain(List<GenerateRequestChain> requestChains,
			List<GenerateRequestChain> otherRequestChains) {
		TreeSet<GenerateRequestChain> sorted = Sets.newTreeSet();
		List<Integer> removeIndexes = Lists.<Integer> newArrayList();
		for (GenerateRequestChain otherValue : otherRequestChains) {
			for (int i = 0; i < requestChains.size(); i++) {
				GenerateRequestChain requestChainItr = requestChains.get(i);
				if (otherValue.name().equals(requestChainItr.name())) {
					removeIndexes.add(i);
				}
			}
		}

		for (Integer index : removeIndexes)
			requestChains.remove(index);

		for (GenerateRequestChain value : otherRequestChains)
			if (value.code() > 0)
				requestChains.add(value);

		for (GenerateRequestChain value : requestChains)
			sorted.add(value);

		return sorted;
	}

	public static Frequency reflectFrequencyTimer(String packageName, long period, long delay)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		String[] otherPackageName = new String[1];
		Lists.newArrayList(packageName).toArray(otherPackageName);
		FastClasspathScanner clazzScaner = new FastClasspathScanner(otherPackageName);
		ScanResult result = clazzScaner.scan();
		List<String> classNames = result.getNamesOfClassesImplementing(Frequency.class);
		Frequency frequency = (Frequency) Class.forName(classNames.get(0)).getConstructor().newInstance();
		frequency.delay(delay);
		frequency.period(period);
		return frequency;
	}

	public static Map<String, Template> refectTemplates(String packageName)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		Map<String, Template> templates = Maps.<String, Template> newHashMap();
		String[] otherPackageName = new String[1];
		Lists.newArrayList(packageName).toArray(otherPackageName);
		FastClasspathScanner clazzScaner = new FastClasspathScanner(otherPackageName);
		ScanResult result = clazzScaner.scan();
		List<String> classNames = result.getNamesOfSubclassesOf(Template.class);
		for (String className : classNames) {
			Template template = (Template) Class.forName(className).getConstructor().newInstance();
			templates.put(template.name(), template);
		}
		return templates;
	}
}
