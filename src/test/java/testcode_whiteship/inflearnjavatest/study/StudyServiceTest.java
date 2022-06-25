package testcode_whiteship.inflearnjavatest.study;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import testcode_whiteship.inflearnjavatest.domain.Member;
import testcode_whiteship.inflearnjavatest.domain.Study;
import testcode_whiteship.inflearnjavatest.member.MemberService;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    //방법 1

//    @Mock
//    MemberService memberService;
//
//    @Mock
//    StudyRepository studyRepository;
//
//    @Test
//    void createStudyService() {
//        StudyService studyService = new StudyService(memberService, studyRepository);
//        assertNotNull(studyService);
//    }

    //방법 2

    @Test
    void createNewStudy(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("j@gmail1.com");

        //Stubbing 방법1 - when: findById(1L)가 호출이 되면, then 방금 만든 member 객체를 Optional에 감싸져서 리턴한다.
//        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        //Stubbing 방법 2 - any() 어떤 인자가 들어가든, 값을 리던해준다. 아무거나 주세요 - 범용적일 때 쓰임.
        when(memberService.findById(any())).thenReturn(Optional.of(member));
        //Stubbing 방법 3 - 값을 리턴하는 게 아니라, 예외를 던져줄 수 있다.
//        when(memberService.findById(1L)).thenThrow(new RuntimeException());

//        Optional<Member> findById = memberService.findById(1L);

        assertEquals("j@gmail1.com", memberService.findById(1L).get().getEmail());
        assertEquals("j@gmail1.com", memberService.findById(2L).get().getEmail());

        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

        assertThrows(IllegalArgumentException.class, () -> {
            memberService.validate(1L);
        });

        memberService.validate(2L);
    }

    @Test
    @DisplayName("연속적인 테스트하기")
    void createNewStudyV2(@Mock MemberService memberService,
            @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("j@gmail1.com");

        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());

        Optional<Member> byId = memberService.findById(1L);

        //첫 번째 호출
        assertEquals("j@gmail1.com", byId.get().getEmail());
        //두 번째 호출
        assertThrows(RuntimeException.class, () -> {
            memberService.findById(1L);
        });
        //세 번째 호출
        assertEquals(Optional.empty(), memberService.findById(3L));

    }


}