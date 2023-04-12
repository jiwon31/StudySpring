package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() throws Exception {
        Member member = new Member();
        member.setName("hello");

        Long memberId = memberService.join(member);

        Member findMember = memberRepository.findById(memberId).get();
        assertThat(findMember.getName()).isEqualTo(member.getName());
    }

    @Test
    void 중복_회원_예외() throws Exception {
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        memberService.join(member1);
        // assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void 전체_회원조회() {
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring2");

        memberService.join(member1);
        memberService.join(member2);

        assertThat(memberService.findMembers().size()).isEqualTo(2);
    }

    @Test
    void 회원_한명_조회() {
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring2");

        memberService.join(member1);
        memberService.join(member2);

        Member findMember = memberService.findOne(member1.getId()).get();
        assertThat(findMember.getName()).isEqualTo(member1.getName());
    }
}