package com.bookstore;

import com.bookstore.service.BookstoreService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/*
   Running this application should result in the following error:
     ERROR: duplicate key value violates unique constraint "author_pkey"
     Detail: Key (id)=(2) already exists.
*/

@SpringBootApplication
public class MainApplication {

    private final BookstoreService bookstoreService;

    public MainApplication(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {
            bookstoreService.save3Authors();
            bookstoreService.saveAuthorNative();
        };
    }
}
/*
 * Hibernate hi/lo Algorithm And External Systems Issue

Description: This is a Spring Boot sample that exemplifies how the hi/lo algorithm may cause issues when the database is used by external systems as well. Such systems can safely generate non-duplicated identifiers (e.g., for inserting new records) only if they know about the hi/lo presence and its internal work. So, better rely on pooled or pooled-lo algorithm which doesn't cause such issues.

Key points:

use the SEQUENCE generator type (e.g., in PostgreSQL)
configure the hi/lo algorithm as in Author.java entity
insert a few records via hi/lo
insert a few records natively (this acts as an external system that relies on NEXTVAL('hilo_sequence') and is not aware of hi/lo presence and/or behavior)
Output sample: Running this application should result in the following error:
ERROR: duplicate key value violates unique constraint "author_pkey"
Detail: Key (id)=(2) already exists.


 * 
 */
