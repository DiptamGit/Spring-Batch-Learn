package com.diptam.SpringBatchLearn.config;

import com.diptam.SpringBatchLearn.model.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<User> userReader,
                   ItemProcessor<User, User> userProcessor,
                   ItemWriter<User> userWriter){

        Step step = stepBuilderFactory.get("csv-to-db").<User, User>chunk(100)
                    .reader(userReader).processor(userProcessor).writer(userWriter).build();

        return jobBuilderFactory.get("spring-to-mysql")
                .incrementer(new RunIdIncrementer())
                .start(step) // if you need multiple step use flow and next
                .build();
    }

    @Bean
    public FlatFileItemReader<User> userReader(@Value("${input}") Resource resource){
        FlatFileItemReader<User> userFlatFileItemReader = new FlatFileItemReader<>();
        userFlatFileItemReader.setResource(resource);
        userFlatFileItemReader.setName("CSV Reader");
        userFlatFileItemReader.setLinesToSkip(1);
        userFlatFileItemReader.setLineMapper(lineMapper());

        return userFlatFileItemReader;
    }

    @Bean
    public LineMapper<User> lineMapper() {

        DefaultLineMapper<User> mapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"id","name", "dept", "salary"});

        BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(User.class);

        mapper.setLineTokenizer(lineTokenizer);
        mapper.setFieldSetMapper(fieldSetMapper);

        return mapper;
    }
}
