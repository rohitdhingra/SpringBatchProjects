package com.example;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
public class ScheduleBatchJobsApplication {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Scheduled(cron = "0/30 * * * * *")
	public void runJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException
	{
		JobParametersBuilder paramBuilder = new JobParametersBuilder();
		paramBuilder.addDate("runTime", new Date());
		this.jobLauncher.run(job(), paramBuilder.toJobParameters());
	}
	
	@Bean
	public Step step() {
		// TODO Auto-generated method stub
		return this.stepBuilderFactory.get("step").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

				System.out.println("This run time is "+ LocalDateTime.now());
				return RepeatStatus.FINISHED;
			}
			
		}).build();
	}
	
	@Bean
	public Job job()
	{
		return this.jobBuilderFactory.get("job").start(step()).build();
	}
	
	

	public static void main(String[] args) {
		SpringApplication.run(ScheduleBatchJobsApplication.class, args);
	}

}
