# cacheTestProject

##用spring+redis 实现扩展跨平台缓存
```xml
<!-- 支持缓存注解 -->
	<cache:annotation-driven cache-manager="cacheManager" />

	<!-- spring cache + redis -->
	<bean id="cacheManager" class="com.util.cache.springredis.RedisCacheManager">
		<property name="caches">
			<set>
				<bean class="com.util.cache.springredis.RedisCacheFactoryBean"
					p:name="default" />

				<bean class="com.util.cache.springredis.RedisCacheFactoryBean"
					p:name="helloCache" />
			</set>
		</property>

		<property name="connectionFactory" ref="jedisConnectionFactory"></property>
	</bean>
```	
```java
/**
	 * 加缓存
	 * @Title: findById
	 * @data:2015年3月8日下午6:42:28
	 * @author:zhanghongliang@hiveview.com
	 *
	 * @param id
	 * @return
	 */
	@Cacheable(value = "helloCache")
	public HelloEntity findById(int id) {
		logger.info("service  获取hello entity");
		HelloEntity result = dao.getById(id);
		return result;
	}

	/**
	 * 清楚缓存
	 * @Title: delete
	 * @data:2015年3月8日下午6:42:23
	 * @author:zhanghongliang@hiveview.com
	 *
	 * @param entity
	 * @return
	 */
	@CacheEvict(value = "helloCache", key = "#entity.id")
	public HelloEntity delete(HelloEntity entity) {
		logger.info("service delete hello entity");
		return this.dao.delete(entity);
	}

	/**
	 * 更新缓存
	 * @Title: update
	 * @data:2015年3月8日下午6:42:15
	 * @author:zhanghongliang@hiveview.com
	 *
	 * @param entity
	 * @return
	 */
	@CachePut(value = "helloCache", key = "#entity.getId()")
	public HelloEntity update(HelloEntity entity) {
		return this.dao.save(entity);
	}
```

```java
public class RedisCache implements Cache {
	private Jedis cache;
	private final String name;

	public RedisCache(String name) {
		this(null, name);
	}

	public RedisCache(Jedis cache, String name) {
		this.cache = cache;
		this.name = name;
	}

	public void setCache(Jedis cache) {
		this.cache = cache;
	}

	@Override
	public void clear() {
		cache.del(name.getBytes());
	}

	@Override
	public void evict(Object arg0) {
		Item key = new Item(arg0);
		byte[] k = serializeObject(key);
		cache.hdel(name.getBytes(), k);
	}

	@Override
	public ValueWrapper get(Object arg0) {
		if (arg0 == null) {
			return null;
		}

		Item key = new Item(arg0);

		byte[] k = serializeObject(key);
		byte[] result = cache.hget(this.getName().getBytes(), k);

		if (result != null) {
			Item vi = (Item) deserializeObject(result);
			ValueWrapper v = new SimpleValueWrapper(vi.getValue());

			return v;
		}

		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Object getNativeCache() {
		return cache;
	}

	@Override
	public void put(Object arg0, Object arg1) {
		Item key = new Item(arg0);
		Item val = new Item(arg1);
		byte[] k = serializeObject(key);
		byte[] v = serializeObject(val);

		cache.hset(getName().getBytes(), k, v);
		// cache.set( k, v );
	}

	protected byte[] serializeObject(Object obj) {
		SerializingConverter sc = new SerializingConverter();
		return sc.convert(obj);
	}

	protected Object deserializeObject(byte[] b) {
		DeserializingConverter dc = new DeserializingConverter();
		return dc.convert(b);
	}

	protected final static class Item implements Serializable {
		private static final long serialVersionUID = 1L;
		private final Object value;

		// private final long timestamp;

		Item(Object value) {
			this.value = value;
			// this.timestamp = Timestamper.next();;
		}

		public Object getValue() {
			return value;
		}
	}
}
```


##用spring+hibernate validation 扩展对象校验
```java
	public String saveModel(
			@NotNull 
			@CrossGroup(fileds={
					@CrossGroupFiled(key="name",message="名称不能为空",vParams="min=1,max=10",vType=ValidEnum.length)
					,@CrossGroupFiled(key="id",message="{age error}",vParams="min=1,max=130",vType=ValidEnum.range)
					,@CrossGroupFiled(key="idcard",message="{idcard error}",vType=ValidEnum.idCardNumber)
			})
			HelloEntity hello,String a){
		logger.debug("saveModel:{},{}",hello.toString(),a);
		return "ccc";
	}
```
