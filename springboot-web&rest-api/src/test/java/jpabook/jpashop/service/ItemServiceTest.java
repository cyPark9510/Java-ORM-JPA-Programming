package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;

    @Test
    public void 아이템저장() throws Exception {
        // given
        Book book = new Book();
        book.setName("jpa");

        // when
        itemService.saveItem(book);

        // then
        assertEquals(book, itemRepository.findOne(book.getId()));
    }

    @Test(expected = NotEnoughStockException.class)
    public void 아이템_재고_감소_예외() throws Exception {
        // given
        Book book = new Book();
        book.setStockQuantity(0);

        // when
        book.removeStock(1);

        // then
        fail("예외가 발생해야 한다.");
    }
}