package testcode_whiteship.inflearnjavatest.study;

import java.util.Optional;
import testcode_whiteship.inflearnjavatest.domain.Member;
import testcode_whiteship.inflearnjavatest.domain.Study;
import testcode_whiteship.inflearnjavatest.member.MemberService;

public class StudyService {

    private final MemberService memberService;

    private final StudyRepository repository;

    public StudyService(MemberService memberService, StudyRepository repository) {
        assert memberService != null;
        assert repository != null;
        this.memberService = memberService;
        this.repository = repository;
    }

    public Study createNewStudy(Long memberId, Study study) {
        Optional<Member> member = memberService.findById(memberId);
        study.setOwner(member.orElseThrow(() -> new IllegalArgumentException(
                "Member doesn't exist for id: '" + memberId + "'")));
        Study newstudy = repository.save(study);
        memberService.notify(newstudy);
//        memberService.notify(member.get());
        return newstudy;
    }

    public Study openStudy(Study study) {
        study.open();
        Study openedStudy = repository.save(study);
        memberService.notify(openedStudy);
        return openedStudy;
    }
}
