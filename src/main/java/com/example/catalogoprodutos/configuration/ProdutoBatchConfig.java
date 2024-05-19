package com.example.catalogoprodutos.configuration;


import com.example.catalogoprodutos.entity.Produto;
import com.example.catalogoprodutos.entity.dto.ProdutoDto;
import com.example.catalogoprodutos.interfaces.ProdutoMapper;
import com.example.catalogoprodutos.repository.ProdutoRepository;
import lombok.val;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;


@Configuration
public class ProdutoBatchConfig {


    @Bean
    public Job importProdutosJob(final JobRepository jobRepository, final PlatformTransactionManager transactionManager,
                                final ProdutoRepository produtoRepository, final ProdutoMapper produtoMapper) throws IOException {
        return new JobBuilder("importProdutosJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(importProdutosStep(jobRepository, transactionManager, produtoRepository, produtoMapper))
                .build();
    }

    @Bean
    public Step importProdutosStep(final JobRepository jobRepository, final PlatformTransactionManager transactionManager,
                                   final ProdutoRepository produtoRepository, final ProdutoMapper produtoMapper) throws IOException {
        return new StepBuilder("importProdutosStep", jobRepository)
                .<ProdutoDto, Produto>chunk(100, transactionManager)
                .reader(flatFileItemReader("C:/Users/gabri/IdeaProjects/catalogoprodutos/src/main/resources/produtos.csv"))
                .processor(itemProcessor(produtoMapper))
                .writer(produtoItemWriter(produtoRepository))
                .build();
    }

    @Bean
    public ItemProcessor<ProdutoDto, Produto> itemProcessor(final ProdutoMapper produtosMapper) {
        return new ProdutoItemProcessor(produtosMapper);
    }

    @Bean
    public ProdutoItemWriter produtoItemWriter(final ProdutoRepository produtoRepository) {
        return new ProdutoItemWriter(produtoRepository);
    }


    @Bean
    @StepScope
    public FlatFileItemReader<ProdutoDto> flatFileItemReader(@Value("#{jobParameters['inputFile']}") final String produtoFile) throws IOException {
        val flatFileItemReader = new FlatFileItemReader<ProdutoDto>();
        flatFileItemReader.setName("PRODUTOS_READER");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(linMapper());
        flatFileItemReader.setStrict(false);
        flatFileItemReader.setResource(new FileSystemResource(produtoFile));
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<ProdutoDto> linMapper() {
        val defaultLineMapper = new DefaultLineMapper<ProdutoDto>();
        val lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setNames("id", "nome", "descricao", "estoque");
        lineTokenizer.setStrict(false);
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(new ProdutoFieldSetMapper());
        return defaultLineMapper;

    }

}