package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final 붙은 필드들만 가지고 생성자를 만들어준다.
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional // 우선순위가 클래스에 붙인 거 보다 높음
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 검사
        memberRepository.save(member);
        return member.getId(); // 값 보장이 됨
    }

    private void validateDuplicateMember(Member member) {
        // 중복 시 Exception
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
