package me.bruno.springcloudstreampublisher;

import lombok.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@EnableBinding(Source.class)
public class SpringCloudStreamPublisherApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamPublisherApplication.class, args);
    }

}

@RestController
@RequiredArgsConstructor
class BookRestController {

    private final MessageChannel output;

    @PostMapping("/publish")
    public ResponseEntity<Book> publishEvent(@RequestBody Book book) {
        Assert.notNull(book, "Book must not be null");
        output.send(MessageBuilder.withPayload(book).build());
        return ResponseEntity.ok(book);
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Book {
    private Integer id;
    private String name;
}
