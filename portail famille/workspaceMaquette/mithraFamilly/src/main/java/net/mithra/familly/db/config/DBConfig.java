package net.mithra.familly.db.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

@Configuration
@EnableMongoRepositories("net.mithra.familly")
@ComponentScan("net.mithra.familly")
@PropertySource("classpath:openconvDB.properties")
public class DBConfig {

	@Autowired
	private Environment env;
	
	public @Bean
	MongoDbFactory mongoDbFactory() throws Exception {
		SimpleMongoDbFactory smdf= new SimpleMongoDbFactory(new MongoClient(env.getProperty("mongo.host"), Integer.parseInt(env.getProperty("mongo.port"))), env.getProperty("mongo.dbname"));
		smdf.setWriteConcern(WriteConcern.SAFE);
		return smdf;
	}
 
	public @Bean
	MongoTemplate mongoTemplate() throws Exception {
 
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
 
		return mongoTemplate;
 
	}
}
