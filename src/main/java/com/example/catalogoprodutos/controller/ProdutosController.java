package com.example.catalogoprodutos.controller;

import com.example.catalogoprodutos.controller.resources.ProdutoResponse;
import com.example.catalogoprodutos.entity.Produto;
import com.example.catalogoprodutos.service.ProdutoService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@RestController
@Slf4j
public class ProdutosController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/executaBatch")
    public BatchStatus load() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        val jobParameters = new JobParametersBuilder()
                .addDate("timestamp", Calendar.getInstance().getTime())
                .addString("inputFile", "C:/Users/gabri/IdeaProjects/catalogoprodutos/src/main/resources/produtos.csv")
                .toJobParameters();
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        while (jobExecution.isRunning()){
            System.out.println("..................");
        }
        return jobExecution.getStatus();
    }


    @GetMapping("/buscaProdutoPorId")
    public ResponseEntity<ProdutoResponse> buscaProdutoPorId(@RequestParam(value = "id", required = true) Long id) {
        log.info("Buscando produto por id {}", id);
        return ResponseEntity.ok(produtoService.buscaProdutoPorId(id));
    }
}
