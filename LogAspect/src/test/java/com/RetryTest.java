package com;

import java.util.concurrent.TimeoutException;

import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.AlwaysRetryPolicy;
import org.springframework.retry.policy.CompositeRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

public class RetryTest {

	public static void main(String[] args) throws Exception {
		RetryTemplate template = new RetryTemplate();

		SimpleRetryPolicy policy = new SimpleRetryPolicy();
		policy.setMaxAttempts(2);

		template.setRetryPolicy(policy);

		String result = template.execute(new RetryCallback<String, Exception>() {
			public String doWithRetry(RetryContext arg0) throws Exception {
				throw new NullPointerException("nullPointerException");
			}
		}, new RecoveryCallback<String>() {
			public String recover(RetryContext context) throws Exception {
				return "recovery callback";
			}
		});
		System.out.println(result);
		
		compositeRetryPolicyFun();
	}
	
	
//	创建CompositeRetryPolicy策略，并创建了RetryPolicy数组，数组有两个具体策略SimpleRetryPolicy与AlwaysRetryPolicy。
//	当CompositeRetryPolicy设置optimistic为true时，Spring-retry会顺序遍历RetryPolicy[]数组，如果有一个重试策略可重试，
//	例如SimpleRetryPolicy没有达到重试次数，那么就会进行重试。 如果optimistic选项设置为false。那么有一个重试策略无法重试，
//	那么就不进行重试。例如SimpleRetryPolicy达到重试次数不能再重试，而AlwaysRetryPolicy可以重试，那么最终是无法重试的
	private static void compositeRetryPolicyFun() throws Exception {
		RetryTemplate template = new RetryTemplate();

        CompositeRetryPolicy policy = new CompositeRetryPolicy();
        RetryPolicy[] polices = {new SimpleRetryPolicy(), new AlwaysRetryPolicy()};

        policy.setPolicies(polices);
        policy.setOptimistic(true);
        template.setRetryPolicy(policy);

        String result = template.execute(
                new RetryCallback<String, Exception>() {
                    public String doWithRetry(RetryContext arg0) throws Exception {
                        if(arg0.getRetryCount() >= 2) {
                            Thread.sleep(1000);
                            throw new NullPointerException();

                        }
                        throw new TimeoutException("TimeoutException");
                    }
                }
                ,
                new RecoveryCallback<String>() {
                    public String recover(RetryContext context) throws Exception {
                        return "recovery callback";
                    }
                }
        );
        System.out.println(result);
	}
}