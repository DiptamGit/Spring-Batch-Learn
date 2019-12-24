package com.diptam.SpringBatchLearn.batch;

import com.diptam.SpringBatchLearn.model.User;
import com.diptam.SpringBatchLearn.repository.UserRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBWriter implements ItemWriter<User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void write(List<? extends User> users) throws Exception {
        System.out.println("Writing Data to DB : "+users);
        userRepository.saveAll(users);
    }
}
