package com.lamfire.jmongo.config;

import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.lamfire.jmongo.MongoOpts;
import com.lamfire.logger.Logger;
import com.lamfire.utils.PropertiesUtils;
import com.lamfire.utils.StringUtils;

public class Configuration {
	private static final Logger LOGGER = Logger.getLogger(Configuration.class);
	private static final String CONFIG_FILE = "mongo.properties";
	private Map<String, MongoOpts> optsMap = new HashMap<String, MongoOpts>();
	private Map<String, Map<String, String>> confMap = new HashMap<String, Map<String, String>>();
	
	private static final Configuration instance = new Configuration();;
	
	public static final Configuration getInstance(){
		return instance;
	}
	
	private Configuration(){
		loadConfigureFile();
	}
	
	private void loadConfigureFile(){
		Map<String,String> map = PropertiesUtils.loadAsMap(CONFIG_FILE, Configuration.class);
		for(Entry<String,String> e : map.entrySet()){
			String key = e.getKey();
			String val = e.getValue();
			LOGGER.info("[Mongo.Configure]:" + key +" = " + val );
			String[] keys = StringUtils.split(key,".",2);
			if(keys.length != 2){
				continue;
			}
			String id = keys[0];
			String opt = keys[1];
			setConfigValue(id,opt,val);
		}
		for(String id : confMap.keySet()){
			MongoOpts opts = buildMongoOpts(id);
			optsMap.put(id, opts);
		}
	}
	
	private void setConfigValue(String id,String optKey,String optVal){
		Map<String, String> map = confMap.get(id);
		if(map == null){
			map = new HashMap<String, String>();
			confMap.put(id, map);
		}
		map.put(optKey, optVal);
	}
	
	public MongoOpts getMongoOpts(String id){
		MongoOpts opts = optsMap.get(id);
		if(opts == null){
			opts = buildMongoOpts(id);
			optsMap.put(id, opts);
		}
		return opts;
	}
	
	public Map<String, MongoOpts> getAllMongoOpts(){
		return Collections.unmodifiableMap(this.optsMap);
	}
	
	private MongoOpts buildMongoOpts(String id){
		Map<String, String> conf = confMap.get(id);
		if(conf == null){
			return null;
		}
		LOGGER.info("[BUILDING '"+id+"']:"+conf.toString());
		MongoOpts opts = new MongoOpts(id);
		String seeds = conf.get("servers");
		
		if(seeds == null){
			throw new RuntimeException("the property '"+id+".servers' was required,please check config file 'mongo.properties'.");
		}
		
		try {
			opts.addServers(seeds);
		} catch (UnknownHostException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
		String connectionsPerHost = conf.get("connectionsPerHost");
		String connectTimeout=conf.get("connectTimeout");
		String maxAutoConnectRetryTime=conf.get("maxAutoConnectRetryTime");
		String maxWaitTime=conf.get("maxWaitTime");
		String socketTimeout=conf.get("socketTimeout");
		String autoConnectRetry=conf.get("autoConnectRetry");
		String socketKeepAlive=conf.get("socketKeepAlive");
		String threadsAllowedToBlockForConnectionMultiplier = conf.get("threadsAllowedToBlockForConnectionMultiplier");
		

		if(StringUtils.isNotBlank(connectionsPerHost)){
			opts.setConnectionsPerHost(Integer.parseInt(connectionsPerHost));
		}
		
		if(StringUtils.isNotBlank(threadsAllowedToBlockForConnectionMultiplier)){
			opts.setThreadsAllowedToBlockForConnectionMultiplier(Integer.parseInt(threadsAllowedToBlockForConnectionMultiplier));
		}
		
		if(StringUtils.isNotBlank(connectTimeout)){
			opts.setConnectTimeout(Integer.parseInt(connectTimeout));
		}
		
		if(StringUtils.isNotBlank(maxAutoConnectRetryTime)){
			opts.setMaxAutoConnectRetryTime(Integer.parseInt(maxAutoConnectRetryTime));
		}
		
		if(StringUtils.isNotBlank(maxWaitTime)){
			opts.setMaxWaitTime(Integer.parseInt(maxWaitTime));
		}
		
		if(StringUtils.isNotBlank(socketTimeout)){
			opts.setSocketTimeout(Integer.parseInt(socketTimeout));
		}
		
		if(StringUtils.isNotBlank(autoConnectRetry)){
			opts.setAutoConnectRetry(Boolean.parseBoolean(autoConnectRetry));
		}
		
		if(StringUtils.isNotBlank(socketKeepAlive)){
			opts.setSocketKeepAlive(Boolean.parseBoolean(socketKeepAlive));
		}
		return opts;
	}
}
