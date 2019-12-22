package me.bruno.springcloudstreamconsumer;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.*;

@SpringBootApplication
public class SpringCloudStreamConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamConsumerApplication.class, args);
    }

}

@Slf4j
@RequiredArgsConstructor
@EnableBinding(Sink.class)
class ConsumerService {

    private final BookRepository repository;

    @StreamListener("input")
    public void consumeMessage(BookConsumer book) {
        Assert.notNull(book, "Fail to read book stream message");
        log.info(String.format("Consume payload: %s", book));
        saveBook(book);
    }

    private Book saveBook(BookConsumer book) {
        log.info("Saving book", book);
        Book bookSaved = repository.save(Book.builder()
                .resourceId(book.getId())
                .name(book.getName())
                .build());
        log.info(String.format("Book saved: %s", bookSaved));
        log.info(String.format("Books in database: [%s]", repository.findAll()));
        return bookSaved;
    }
}

@Repository
interface BookRepository extends JpaRepository<Book, Long> {
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "book",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"resourceId", "name"})})
class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer resourceId;
    private String name;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class BookConsumer {
    private Integer id;
    private String name;
}