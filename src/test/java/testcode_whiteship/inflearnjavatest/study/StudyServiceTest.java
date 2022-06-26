package testcode_whiteship.inflearnjavatest.study;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import testcode_whiteship.inflearnjavatest.domain.Member;
import testcode_whiteship.inflearnjavatest.domain.Study;
import testcode_whiteship.inflearnjavatest.member.MemberService;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    @Test
    @DisplayName("Stubbing 연습 문제")
    void createNewStudy() {
        //Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("j@gmail1.com");

        Study study = new Study(10, "테스트");

//        when(memberService.findById(1L)).thenReturn(Optional.of(member));
//        when(studyRepository.save(study)).thenReturn(study);

        //when 이 given/when/then 의 when behavior 와 헷갈릴 수 있으니 given으로 바꾸어준다.

        given(memberService.findById(1L)).willReturn(Optional.of(member));
        given(studyRepository.save(study)).willReturn(study);

        //When
        studyService.createNewStudy(1L, study);

        //Then
        assertEquals(member, study.getOwner());
        //호출 여부를 확인하는 것. notify가 study라는 매개변수를 가지고 1번 호출되었어야 한다. Verify 쓰면, n번 호출되는지 확인 가능.
        //만약 service에 이런 메서드가 없다면, wanted but not invoked 라는 에러가 난다.
        then(memberService).should(times(1)).notify(study);
        then(memberService).shouldHaveNoMoreInteractions();
    }

    @DisplayName("다른 사용자가 볼 수 있도록 스터디를 공개한다.")
    @Test
    void openStudy() {
        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        Study study = new Study(10, "더 자바, 테스트");
        assertNull(study.getOpenedDateTime());

        // TODO studyRepository Mock 객체의 save 메소드를호출 시 study를 리턴하도록 만들기.
        given(studyRepository.save(study)).willReturn(study);

        // When
        studyService.openStudy(study);

        // Then
        assertEquals(StudyStatus.OPENED, study.getStatus());
        assertNotNull(study.getOpenedDateTime());
        then(memberService).should(times(1)).notify(study);

        then(study.getOpenedDateTime()).should(notnull
                // TODO study의 status가 OPENED로 변경됐는지 확인
                // TODO study의 openedDataTime이 null이 아닌지 확인
                // TODO memberService의 notify(study)가 호출 됐는지 확인.
    }
}