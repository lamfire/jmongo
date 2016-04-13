jmongo
===========
This library provides clean and powerful mapping between Java POJOs and MongoDB DBObject.

step 1 setting jmongo.properties in resources and class path
-----------------------
    db1.servers=192.168.180.49:27000
    db1.connectionsPerHost=16
    db1.threadsAllowedToBlockForConnectionMultiplier = 10
    db1.connectTimeout=60000
    db1.maxAutoConnectRetryTime=10
    db1.maxWaitTime=120000
    db1.socketTimeout=30000
    db1.autoConnectRetry=true
    db1.socketKeepAlive=true


step 2 define Entity
-----------------------

    import java.util.List;
    import org.bson.types.ObjectId;
    import com.lamfire.jmongo.annotations.Entity;
    import com.lamfire.jmongo.annotations.Id;


    @Entity
    public class User {
    	@Id
    	private String id;
        private String username;
        private String password;
        private Integer age;

    	public String getId() {
    		return id;
    	}

    	public void setId(String id) {
    		this.id = id;
    	}

    	public String getUsername() {
    		return name;
    	}

    	public void setUsername(String name) {
    		this.name = name;
    	}

    	... more set and get method
    }

step 3 Using DAO template
------------------
    User user = new User();
    user.setId("10001");
    user.setAge(18);
    user.setUsername("lamfire");
    user.setPassword("password");

    DAO<User,String> dao = DAOFactory.get("db1","test",User.class);

    //save
    dao.save(user);
    System.out.println(dao.count());

    //get
    user = dao.get("10001");

    //query
    List<User> users = dao.createQuery().asList();

No use configure file
------------------
    //register mongodb host
    MongoOpts opts = new MongoOpts("db1");
    opts.addHost("192.168.180.49:27000");
    JMongo.register(opts);

    //new entity instance
    User user = new User();
    user.setId("10001");
    user.setAge(18);
    user.setUsername("lamfire");
    user.setPassword("password");

    //get dao instance
    DAO<User,String> dao = DAOFactory.get("db1","test",User.class);

    //save
    dao.save(user);
    System.out.println(dao.count());

    //get
    user = dao.get("10001");

    //query
    List<User> users = dao.createQuery().asList();