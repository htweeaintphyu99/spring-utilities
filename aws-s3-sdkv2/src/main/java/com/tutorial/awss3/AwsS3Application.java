package com.tutorial.awss3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.*;

@SpringBootApplication
public class AwsS3Application {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SpringApplication.run(AwsS3Application.class, args);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int sum = 0;
                for (int i = 1; i <= 100; i++) {
                    sum += i;
                    Thread.sleep(1000);
                }
                return sum;
            }
        });
        System.out.println("Waiting for the result...");
        int result = future.get();
        System.out.println("The sum of the first 100 numbers is: " + result);
        executor.shutdown();
    }
}

