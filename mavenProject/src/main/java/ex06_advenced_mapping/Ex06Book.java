package ex06_advenced_mapping;

import javax.persistence.Entity;

@Entity
public class Ex06Book extends Ex06Item {

    private String author;
    private String isbn;
}
