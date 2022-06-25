package testcode_whiteship.inflearnjavatest.member;


import java.util.Optional;
import testcode_whiteship.inflearnjavatest.domain.Member;
import testcode_whiteship.inflearnjavatest.domain.Study;

public interface MemberService {

    Optional<Member> findById(Long memberId);

    void validate(Long memberId);

    void notify(Study newstudy);

    void notify(Member member);
}
