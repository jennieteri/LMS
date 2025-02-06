package kr.or.ddit.yguniv.bucket;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import io.github.bucket4j.TokensInheritanceStrategy;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Configuration
public class BucketConfig {
	
	private Bucket bucket;
	

    @Bean
    public Bucket bucket() {
        int second = 5;
        int token = 3;
        
        final Refill refill = Refill.intervally(token, Duration.ofSeconds(second));
        final Bandwidth limit = Bandwidth.classic(token, refill);
        
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
        
        return this.bucket;
    }

    public void reloadBucket(int newSecond, int newToken) {
        final Refill newRefill = Refill.intervally(newToken, Duration.ofSeconds(newSecond));
        final Bandwidth newLimit = Bandwidth.classic(newToken, newRefill);
        
        BucketConfiguration newConfiguration = BucketConfiguration.builder()
                .addLimit(newLimit)
                .build();
        
        this.bucket.replaceConfiguration(newConfiguration, TokensInheritanceStrategy.RESET);
        log.info("버킷 재설정 {}분에  {} 개 만큼 요청 제한",  (newSecond/60), newToken);
        
    }

	private Bucket secondBucket;
	
    @Bean
    public Bucket secondBucket() {
    	int second = 5;
    	int token = 3;
    	
    	final Refill refill = Refill.intervally(token, Duration.ofSeconds(second));
    	final Bandwidth limit = Bandwidth.classic(token, refill);
    	
    	this.secondBucket = Bucket.builder()
    			.addLimit(limit)
    			.build();
    	
    	return this.secondBucket;
    }
    
    public void reloadSecondBucket(int newSecond, int newToken) {
    	final Refill newRefill = Refill.intervally(newToken, Duration.ofSeconds(newSecond));
    	final Bandwidth newLimit = Bandwidth.classic(newToken, newRefill);
    	
    	BucketConfiguration newConfiguration = BucketConfiguration.builder()
    			.addLimit(newLimit)
    			.build();
    	
    	this.secondBucket.replaceConfiguration(newConfiguration, TokensInheritanceStrategy.RESET);
    	log.info("버킷 재설정 {}분에  {} 개 만큼 요청 제한",  (newSecond/60), newToken);
    	
    }
    
    
    
    
    
    
    
    
}











