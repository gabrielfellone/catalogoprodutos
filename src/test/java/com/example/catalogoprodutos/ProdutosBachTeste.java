package com.example.catalogoprodutos;

import com.example.catalogoprodutos.config.ProdutoBatchConfigTeste;
import com.example.catalogoprodutos.configuration.ProdutoBatchConfig;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBatchTest
@SpringJUnitConfig(classes = {ProdutoBatchConfig.class, ProdutoBatchConfigTeste.class})
class ProdutosBachTeste {

    public static final String INPUt_FILE = "produtos.csv";

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @AfterEach
    public void cleanUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    private JobParameters defaultJobParameters() {
        val paramsBuilder = new JobParametersBuilder();
        paramsBuilder.addString("inputFile", INPUt_FILE);
        paramsBuilder.addDate("timestamp", Calendar.getInstance().getTime());
        return paramsBuilder.toJobParameters();
    }


    @Test
    void testeSucessoBatchProdutosCsv(@Autowired Job job) throws Exception {
        this.jobLauncherTestUtils.setJob(job);

        val jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
        val actualJobInstance = jobExecution.getJobInstance();
        val actualJobExitStatus = jobExecution.getExitStatus();

        assertThat(actualJobInstance.getJobName()).isEqualTo("importProdutosJob");
        assertThat(actualJobExitStatus.getExitCode()).isEqualTo(ExitStatus.COMPLETED.getExitCode());

    }

}