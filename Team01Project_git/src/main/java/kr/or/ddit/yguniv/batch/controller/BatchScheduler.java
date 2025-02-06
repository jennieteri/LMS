package kr.or.ddit.yguniv.batch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchScheduler {
	 @Autowired
	    private JobLauncher jobLauncher;
	    
	    //수강신청 SUCC => Attendee Table Insert
	    @Autowired
	    private Job testJob;	    
//	    @Scheduled(cron = "0 46 * * * *")
	    public void runTestJob() throws Exception {
	    	JobParameters jobParameters = new JobParametersBuilder()
	    			.addLong("time", System.currentTimeMillis())
	    			.toJobParameters();
	    	
	    	jobLauncher.run(testJob, jobParameters);
	    }

	    //학생 3개월 시 휴면일자 업데이트
	    @Autowired
	    private Job stuDormantJob;
	   
	    @Scheduled(cron = "0 0 2 * * *") // 매일 새벽 2시에 휴면계정 작업을 실행
	    public void stuDormantJob() throws Exception {
	    	JobParameters jobParameters = new JobParametersBuilder()
	    			.addLong("time", System.currentTimeMillis())
	    			.toJobParameters();
	    	
	    	jobLauncher.run(stuDormantJob, jobParameters);
	    }
}
