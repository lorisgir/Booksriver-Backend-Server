package com.ghibo.bookserver.queue;

import com.ghibo.bookserver.configuration.MQConfig;
import com.ghibo.bookserver.domain.dto.RabbitMqWrapper;
import com.ghibo.bookserver.domain.dto.UserReviewView;
import com.ghibo.bookserver.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Consumer {

    private final BookService bookService;


    @RabbitListener(queues = MQConfig.QUEUE)
    public void listen(RabbitMqWrapper<UserReviewView> wrapper) {
        bookService.syncReview(wrapper);
    }

}