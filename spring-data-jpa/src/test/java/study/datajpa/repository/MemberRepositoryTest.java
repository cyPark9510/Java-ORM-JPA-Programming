package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;

    @Test
    public void testMember() {
        // given
        Member member = new Member("memberA", 20);

        // when
        Member savedMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(savedMember.getId()).get();

        // then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        // 단건 조회 검증
        Member findMember1 = memberRepository.findById(member1.getId()).orElseGet(null);
        Member findMember2 = memberRepository.findById(member2.getId()).orElseGet(null);
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        // 리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        // 카운트 검증
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        // 삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);
        long deletedCount = memberRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void findByUSernameAndAgeGreaterThen() {
        // given
        Member m1 = new Member("memberA", 10);
        Member m2 = new Member("memberB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        // when
        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("memberB", 15);

        // then
        assertThat(result.get(0).getUsername()).isEqualTo("memberB");
        assertThat(result.get(0).getAge()).isEqualTo(20);
    }

    @Test
    public void testNamedQuery() {
        // given
        Member m1 = new Member("memberA", 10);
        Member m2 = new Member("memberB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        // when
        List<Member> result = memberRepository.findByUsername(m1.getUsername());
        Member findMember = result.get(0);

        // then
        assertThat(findMember).isEqualTo(m1);
    }

    @Test
    public void testQuery() {
        // given
        Member m1 = new Member("memberA", 10);
        Member m2 = new Member("memberB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        // when
        List<Member> result = memberRepository.findUser(m1.getUsername(), m1.getAge());
        Member findMember = result.get(0);

        // then
        assertThat(findMember).isEqualTo(m1);
    }

    @Test
    public void findUsernameList() {
        // given
        Member m1 = new Member("memberA", 10);
        Member m2 = new Member("memberB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        // when
        List<String> usernameList = memberRepository.findUsernameList();

        // then
        for (String s : usernameList) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void findMemberDto() {
        // given
        Team team = new Team("team");
        teamRepository.save(team);

        Member member = new Member("member", 20);
        member.changeTeam(team);
        memberRepository.save(member);

        // when
        List<MemberDto> result = memberRepository.findMemberDto();
        MemberDto memberDto = result.get(0);

        // then
        assertThat(memberDto.getId()).isEqualTo(member.getId());
        assertThat(memberDto.getUsername()).isEqualTo(member.getUsername());
        assertThat(memberDto.getTeamName()).isEqualTo(member.getTeam().getName());
    }

    @Test
    public void findByNames() {
        // given
        Member m1 = new Member("memberA", 10);
        Member m2 = new Member("memberB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        // when
        List<Member> result = memberRepository.findByNames(Arrays.asList("memberA", "memberB"));

        // then
        for (Member member : result) {
            System.out.println("member = " + member);
        }
    }

    @Test()
    public void returnType() {
        // given
        Member m1 = new Member("member", 10);
        Member m2 = new Member("member", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        // when
        List<Member> listByUsername = memberRepository.findListByUsername("m");
        Member memberByUsername = memberRepository.findMemberByUsername("m");
        Optional<Member> optionalByUsername = memberRepository.findOptionalByUsername("m");

        // then
        assertThat(listByUsername.size()).isEqualTo(0);
        assertThat(memberByUsername).isNull();
        assertThat(optionalByUsername).isEmpty();

        // 결과가 하나 이상이면 예외(IncorrectResultSizeDataAccessException) 발생
//        Optional<Member> member = memberRepository.findOptionalByUsername("member");
    }

    @Test
    public void paging() {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        int offset = 0;
        int limit = 3;
        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, "username"));

        // when
        Page<Member> page = memberRepository.findPageByAge(age, pageRequest);

        Page<MemberDto> toMap = page.map(m -> new MemberDto(m.getId(), m.getUsername(), null));

        // then
        assertThat(page.getContent().size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
    }

    @Test
    public void slice() {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        int offset = 0;
        int limit = 3;
        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, "username"));

        // when
        Slice<Member> page = memberRepository.findSliceByAge(age, pageRequest);

        // then
        assertThat(page.getContent().size()).isEqualTo(3);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
    }
}