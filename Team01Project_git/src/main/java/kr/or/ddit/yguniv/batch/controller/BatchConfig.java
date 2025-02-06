package kr.or.ddit.yguniv.batch.controller;

import java.util.Iterator;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.or.ddit.yguniv.batch.service.BatchServiceImpl;
import kr.or.ddit.yguniv.vo.AttendeeVO;
import kr.or.ddit.yguniv.vo.LectureCartVO;
import kr.or.ddit.yguniv.vo.PersonVO;
import kr.or.ddit.yguniv.vo.StudentVO;
import lombok.extern.slf4j.Slf4j;

//batchConfig
@Slf4j
@Configuration
@EnableBatchProcessing
public class BatchConfig {

  @Autowired
  private JobBuilderFactory jobBuilderFactory;

  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Autowired
  private BatchServiceImpl service;


  
//수강신청 => attendee Table Insert   
  @Bean
  public Job testJob() {
      return jobBuilderFactory.get("testJob")
              .start(stuDormantStep())
              .build();
  }
  
  @Bean
  public Step testStep() {
	  return stepBuilderFactory.get("testStep")
			  .<LectureCartVO, AttendeeVO>chunk(10)
			  .reader(testItemReader())
			  .processor(testItemProcessor())
			  .writer(testItemWriter())
			  .build();
  }
  
  @Bean
  public ItemReader<LectureCartVO> testItemReader() {
      return new ItemReader<LectureCartVO>() {
          private Iterator<LectureCartVO> customerIterator;

          @Override
          public LectureCartVO read() throws Exception {
              if (customerIterator == null) {
                  customerIterator = service.selectCartList().iterator();
              }
              return customerIterator.hasNext() ? customerIterator.next() : null;
          }
      };
  }

  @Bean
  public ItemProcessor<LectureCartVO, AttendeeVO> testItemProcessor() {
      return cart -> {
          log.info("Processing customer: {}", cart);
    	  AttendeeVO aVo = new AttendeeVO();
    	  aVo.setLectNo(cart.getLectNo());
    	  aVo.setStuId(cart.getStuId());
          return aVo;
      };
  }

  @Bean
  public ItemWriter<AttendeeVO> testItemWriter() {
      return items -> {
          for (AttendeeVO attendee : items) {
              int result = service.insertAttendee(attendee);
              log.info("Writing customer: {} , result : {}", attendee,result);
          }
      };
  }
  
  //-------------------------------------------------------------------------------
  
  //금일과 최근 접속일자의 차이가 3개월 => student 테이블에 domant 칼럼 업데이트
  @Bean
  public Job stuDormantJob() {
	  return jobBuilderFactory.get("stuDormantJob")
			  .start(stuDormantStep())
			  .build();
  }
  
  @Bean
  public Step stuDormantStep() {
	  return stepBuilderFactory.get("stuDormantJob")
			  .<StudentVO, StudentVO>chunk(10)
			  .reader(stuDormantItemReader())
			  .processor(stuDormantItemProcessor())
			  .writer(stuDormantItemWriter())
			  .build();
  }
  
  @Bean
  public ItemReader<StudentVO> stuDormantItemReader() {
	  return new ItemReader<StudentVO>() {
		  private Iterator<StudentVO> customerIterator;
		  
		  @Override
		  public StudentVO read() throws Exception {
			  if (customerIterator == null) {
				  customerIterator = service.selectStudentList().iterator();
			  }
			  return customerIterator.hasNext() ? customerIterator.next() : null;
		  }
	  };
  }
  
  @Bean
  public ItemProcessor<StudentVO, StudentVO> stuDormantItemProcessor() {
	  return student -> {
	        log.info("Processing student: {}", student);
	        
	        StudentVO dormantStudent = service.dateFormatter(student);
	        
	        return dormantStudent; // null이면 처리하지 않고, 아니면 처리함
	    };
  }
  
  @Bean
  public ItemWriter<StudentVO> stuDormantItemWriter() {
	  return items -> {
		  for (StudentVO student : items) {
			  int result = service.updateStudentDormant(student);
			  log.info("Writing student: {} , result : {}", student,result);
		  }
	  };
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
}

