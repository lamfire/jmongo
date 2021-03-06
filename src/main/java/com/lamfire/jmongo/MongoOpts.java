package com.lamfire.jmongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.lamfire.jmongo.logger.Logger;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

public class MongoOpts {
	private static final Logger LOGGER = Logger.getLogger(MongoOpts.class);
	protected final List<ServerAddress> seeds = new ArrayList<ServerAddress>();
	protected final MongoOptions options = new MongoOptions();
	protected String id;

	public MongoOpts(String id){
		this.id = id;
		options.autoConnectRetry=true;
		options.socketKeepAlive = true;
		options.connectionsPerHost = 16;
		options.threadsAllowedToBlockForConnectionMultiplier = 10;
	}
	
	public void addHost(String host,int port) throws UnknownHostException{
		seeds.add(new ServerAddress(host, port));
		LOGGER.info("[HOST '"+id+"']: " + host +":" + port);
	}
	
	public void addHost(String connectString) throws UnknownHostException{
		String [] val = connectString.split(":");
        addHost(val[0], Integer.parseInt(val[1]));
	}
	
	public void addHosts(String[] connects) throws UnknownHostException{
		for(String s : connects){
            addHost(s);
		}
	}
	
	public void addHosts(String seeds) throws UnknownHostException{
		String [] servers = seeds.split(",");
        addHosts(servers);
	}

	public void setAutoConnectRetry(boolean autoConnectRetry){
		options.autoConnectRetry=autoConnectRetry;
	}
	
	public void setSocketKeepAlive(boolean socketKeepAlive){
		options.socketKeepAlive=socketKeepAlive;
	}
	
	public void setThreadsAllowedToBlockForConnectionMultiplier(int size){
		options.threadsAllowedToBlockForConnectionMultiplier = size;
	}
	
	public void setConnectionsPerHost(int connectionsPerHost){
		options.connectionsPerHost = connectionsPerHost;
	}
	
	public void setConnectTimeout(int connectTimeout){
		options.connectTimeout = connectTimeout;
	}
	
	public void setMaxAutoConnectRetryTime(long maxAutoConnectRetryTime){
		options.maxAutoConnectRetryTime = maxAutoConnectRetryTime;
	}
	
	public void setMaxWaitTime(int maxWaitTime){
		options.maxWaitTime = maxWaitTime;
	}
	
	public void setSocketTimeout(int socketTimeout){
		options.socketTimeout = socketTimeout;
	}

	public List<ServerAddress> getSeeds() {
		return seeds;
	}

	public MongoOptions getOptions() {
		return options;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
