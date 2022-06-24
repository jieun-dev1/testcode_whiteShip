package testcode_whiteship.inflearnjavatest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudyTest {

     @Test
     @DisplayName("스터디 만들기")
     void create() {
        Study study = new Study();
        assertNotNull(study);
        assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다");

     }

     @BeforeAll
    static void beforeAll() {
        System.out.println("before all");
     }

     @AfterAll
     static void afterAll() {
         System.out.println("after all");
     }

     @BeforeEach
    void beforeEach() {
         System.out.println("before each");

     }
     @AfterEach
    void afterEach() {
         System.out.println("after each");

     }

}