package com.tianyalan.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.client.RestTemplate;

import com.tianyalan.orders.model.Account;

@SpringCloudApplication
@EnableBinding(Sink.class)
public class OrderApplication {

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public ReactiveRedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory();
	}

	@Bean
	ReactiveRedisTemplate<String, String> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
		return new ReactiveRedisTemplate<>(factory, RedisSerializationContext.string());
	}

	@Bean
	ReactiveRedisTemplate<String, Account> redisOperations(ReactiveRedisConnectionFactory factory) {
		Jackson2JsonRedisSerializer<Account> serializer = new Jackson2JsonRedisSerializer<>(Account.class);

		RedisSerializationContext.RedisSerializationContextBuilder<String, Account> builder = RedisSerializationContext
				.newSerializationContext(new StringRedisSerializer());

		RedisSerializationContext<String, Account> context = builder.value(serializer).build();

		return new ReactiveRedisTemplate<>(factory, context);
	}

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}
}
