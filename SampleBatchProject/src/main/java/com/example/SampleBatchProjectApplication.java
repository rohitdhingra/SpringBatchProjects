package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@SpringBootApplication
@EnableBatchProcessing
public class SampleBatchProjectApplication {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Flow deliveryFlow()
	{
		return new FlowBuilder<SimpleFlow>("deliveryFlow").start(driveToAddressStep())
				.on("FAILED").fail()
			.from(driveToAddressStep())
				.on("*").to(decider())
					.on("PRESENT").to(givePackageToCustomerStep())
					.next(itemDecider())
						.on("THANKS").to(thankToCustomerStep())
						.from(itemDecider())
						.on("REFUND").to(refundToCustomerStep())
				.from(decider())
					.on("NOT_PRESENT").to(leaveAtDoorStep()).build();
	}
	
	@Bean
	public JobExecutionDecider itemDecider()
	{
		return new ItemDecider();
	}
	
	@Bean
	public JobExecutionDecider decider()
	{
		return new DeliveryDecider();
	}
	
	@Bean
	public Step thankToCustomerStep()
	{
		return this.stepBuilderFactory.get("thanksToCustomer").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Thanks to the customer");
				return RepeatStatus.FINISHED;
			}
		}	).build();
		
	}
	@Bean
	public Step refundToCustomerStep()
	{
		return this.stepBuilderFactory.get("refundToCustomer").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Refund Money to the customer");
				return RepeatStatus.FINISHED;
			}
		}	).build();
		
	}

	@Bean
	public Step leaveAtDoorStep()
	{
		return this.stepBuilderFactory.get("leaveAtDoorStep").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Leaving the package at the door");
				return RepeatStatus.FINISHED;
			}
			
		}	).build();
		
	}
	
	@Bean
	public Step storePackageStep()
	{
		return this.stepBuilderFactory.get("storePackage").tasklet(new Tasklet()
				{

					@Override
					public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
							throws Exception {
						System.out.println("Storing the package while the customer address is located");
						return RepeatStatus.FINISHED;
					}
			
				}
			).build();
				
	}
	
	@Bean
	public Step givePackageToCustomerStep()
	{
		return this.stepBuilderFactory.get("givePackageToCustomer").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Given the package to the customer");
				return RepeatStatus.FINISHED;
			}
			
		}).build();
	}

	@Bean
	public Step driveToAddressStep()
	{
		boolean GOT_LOST = false;
		return this.stepBuilderFactory.get("driveToAddress").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				if(GOT_LOST)
				{
					throw new RuntimeException("Got Lost while driving:");
				}
				System.out.println("Successfully arrived at the address.");
				return RepeatStatus.FINISHED;
			}
			
		}).build();
	}
	
	@Bean
	public Step packageItemStep() {
		return this.stepBuilderFactory.get("packageItem").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				String item = chunkContext.getStepContext().getJobParameters().get("item").toString();
				String date = chunkContext.getStepContext().getJobParameters().get("run.date").toString();
				
				System.out.println(String.format("Step with %s to be executed on %s",item,date));
				return RepeatStatus.FINISHED;
			}
			
		}).build();
	}
	
	@Bean
	public Job deliverPackageJob()
	{
		return this.jobBuilderFactory.get("deliverPackageJob")
				.start(packageItemStep())
				.split(new SimpleAsyncTaskExecutor())
				.add(deliveryFlow(),billingFlow())
				.end()
				.build();
	}
	
	@Bean
	public StepExecutionListener selectFlowerListener()
	{
		return new FlowerSelectionStepExecutionListener();
	}
	
	@Bean
	public Step removeThornsStep()
	{
		return this.stepBuilderFactory.get("removeThornsStep").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Removing thorns from the flowers.");
				return RepeatStatus.FINISHED;
			}
		}).build();
		
	}
	
	
	@Bean
	public Step arrangeFlowersStep() {
		return this.stepBuilderFactory.get("arrangeFlowers").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Arrange Flowers in Order");
				return RepeatStatus.FINISHED;
			}
		}).build();
		
	}

	@Bean
	public Step selectFlowersStep() {
		return this.stepBuilderFactory.get("selectFlowers").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Select Flowers");
				return RepeatStatus.FINISHED;
			}
		}).listener(selectFlowerListener()).build();
	}
	
	@Bean
	public Job prepareFlowersJob()
	{
		return this.jobBuilderFactory.get("prepareFlowersJob")
		.start(selectFlowersStep())
			.on("TRIM_REQUIRED").to(removeThornsStep()).next(arrangeFlowersStep())
		.from(selectFlowersStep())
			.on("NO_TRIM_REQUIRED").to(arrangeFlowersStep())
		.from(arrangeFlowersStep())
			.on("*").to(deliveryFlow()) 
		.end()
		.build();
	}
	
	@Bean
	public Step nestedBillingStep()
	{
		return this.stepBuilderFactory.get("nestedBillingStep").job(billingJob()).build();
	}
	
	@Bean
	public Step sendInvoiceStep() {
		return this.stepBuilderFactory.get("sendInvoiceStep").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Invoice is sent to the customer");
				return RepeatStatus.FINISHED;
			}
		}).build();
		
	}
	
	
	@Bean
	public Job billingJob()
	{
		return this.jobBuilderFactory.get("billingJob").start(sendInvoiceStep()).build();
	}
	
	@Bean
	public Flow billingFlow()
	{
		return new FlowBuilder<SimpleFlow>("deliveryFlow").start(sendInvoiceStep()).build();
	}
	

	public static void main(String[] args) {
		SpringApplication.run(SampleBatchProjectApplication.class, args);
	}

}
