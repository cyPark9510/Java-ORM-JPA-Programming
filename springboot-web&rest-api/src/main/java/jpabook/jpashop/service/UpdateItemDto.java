package jpabook.jpashop.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateItemDto {
    private Long memberId;
    private Long itemId;
    private int count;
}
