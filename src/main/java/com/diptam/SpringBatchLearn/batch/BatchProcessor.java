package com.diptam.SpringBatchLearn.batch;

import com.diptam.SpringBatchLearn.model.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class BatchProcessor implements ItemProcessor<User, User> {
    @Override
    public User process(User user) throws Exception {
        //do your processing here with data
        System.out.println("From Processor : "+user);
        return user;
    }
}
