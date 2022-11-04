package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
        // given
        Member member = new Member("memberA", 20);

        // when
        Member savedMember = memberJpaRepository.save(member);
        Member findMember = memberJpaRepository.find(savedMember.getId());

        // then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }
    
    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        // 단건 조회 검증
        Member findMember1 = memberJpaRepository.findById(member1.getId()).orElseGet(null);
        Member findMember2 = memberJpaRepository.findById(member2.getId()).orElseGet(null);
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        // 리스트 조회 검증
        List<Member> all = memberJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        // 카운트 검증
        long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);

        // 삭제 검증
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);
        long deletedCount = memberJpaRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThan() {
        // given
        Member m1 = new Member("memberA", 10);
        Member m2 = new Member("memberB", 20);
        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);

        // when
        List<Member> result = memberJpaRepository.findByUsernameAndAgeGreaterThan("memberB", 15);

        // then
        assertThat(result.get(0).getUsername()).isEqualTo("memberB");
        assertThat(result.get(0).getAge()).isEqualTo(20);
    }

    @Test
    public void testNamedQuery() {
        // given
        Member m1 = new Member("memberA", 10);
        Member m2 = new Member("memberB", 20);
        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);

        // when
        List<Member> result = memberJpaRepository.findByUsername(m1.getUsername());
        Member findMember = result.get(0);

        // then
        assertThat(findMember).isEqualTo(m1);
    }
}