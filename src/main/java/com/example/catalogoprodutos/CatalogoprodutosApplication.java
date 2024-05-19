package com.example.catalogoprodutos;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;

import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.nio.file.*;
import java.util.Calendar;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
@EnableRabbit
@Slf4j
public class CatalogoprodutosApplication {
    private final JobLauncher jobLauncher;
    private final Job job;
    public CatalogoprodutosApplication(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(CatalogoprodutosApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args)
                .registerShutdownHook();
    }


    @Scheduled(fixedRate = 300000)
    public void runJob() throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        log.info("Rodando a job de capturar valores do csv para o banco");
        {
            val jobParameters = new JobParametersBuilder()
                    .addDate("timestamp", Calendar.getInstance().getTime())
                    .addString("inputFile", "C:/Users/gabri/IdeaProjects/catalogoprodutos/src/main/resources/produtos.csv") // caminho onde esta o arquivo CSV
                    .toJobParameters();
            val jobExecution = jobLauncher.run(job, jobParameters);
            while (jobExecution.isRunning()){
                System.out.println("..................");
            }
        }
    }


}
