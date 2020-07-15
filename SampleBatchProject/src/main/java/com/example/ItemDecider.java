package com.example;

import java.util.Random;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class ItemDecider implements JobExecutionDecider{

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
		String result = new Random(100).nextInt()<70? "THANKS":"REFUND";
		System.out.println("Item Decider Result is "+ result);
		return new FlowExecutionStatus(result);
	}

}
