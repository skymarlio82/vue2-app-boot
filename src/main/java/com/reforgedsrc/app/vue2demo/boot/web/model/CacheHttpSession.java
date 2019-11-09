
package com.reforgedsrc.app.vue2demo.boot.web.model;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class CacheHttpSession extends HttpSessionWrapper {

	private String sid = null;
	private byte[] sid_bytes = null;
	@SuppressWarnings("rawtypes")
	private RedisTemplate redisTemplate = null;
	private StringRedisSerializer stringSerializer = new StringRedisSerializer();
	private RedisSerializer<Object> objectSerializer = new JdkSerializationRedisSerializer();

	@SuppressWarnings("rawtypes")
	public CacheHttpSession(HttpSession session, String sid, RedisTemplate redisTemplate) {
		super(session);
		this.sid = sid;
		this.sid_bytes = stringSerializer.serialize(sid);
		this.redisTemplate = redisTemplate;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Enumeration<String> getAttributeNames() {
		final byte[] key = stringSerializer.serialize(sid);
		Object result = redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				Set<byte[]> set = connection.keys(key);
				return set;
			}
		});
		if (result != null) {
			Set<byte[]> s = (Set<byte[]>) result;
			Set<String> ss = new HashSet<String>();
			for (byte[] b : s) {
				ss.add(stringSerializer.deserialize(b));
			}
			Enumeration<String> en = new Vector(ss).elements();
			return en;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setAttribute(String name, Object value) {
		final byte[] key = stringSerializer.serialize(name);
		final byte[] val = objectSerializer.serialize(value);
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.hSet(sid_bytes, key, val);
				return null;
			}
		});
	}

	@Override
	public Object getAttribute(String name) {
		final byte[] key = stringSerializer.serialize(name);
		@SuppressWarnings("unchecked")
		Object value = redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				return objectSerializer.deserialize(connection.hGet(sid_bytes, key));
			}
		});
		return value;
	}

	@Override
	public String getId() {
		return sid;
	}
}