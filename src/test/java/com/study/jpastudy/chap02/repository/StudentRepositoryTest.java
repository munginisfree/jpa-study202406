package com.study.jpastudy.chap02.repository;

import com.study.jpastudy.chap02.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Rollback
class StudentRepositoryTest {
    @Autowired
    StudentRepository studentRepository;

    @BeforeEach
    void insertData() {
        Student s1 = Student.builder()
                .name("쿠로미")
                .city("청양군")
                .major("경제학")
                .build();

        Student s2 = Student.builder()
                .name("춘식이")
                .city("서울시")
                .major("컴퓨터공학")
                .build();

        Student s3 = Student.builder()
                .name("어피치")
                .city("제주도")
                .major("화학공학")
                .build();

        studentRepository.save(s1);
        studentRepository.save(s2);
        studentRepository.save(s3);
    }

    @Test
    @DisplayName("")
    void dummyTest() {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("이름이 춘식이인 학생의 모든 정보를 조회한다")
    void findOneTest() {
        //given
        String name = "춘식이";
        //when
        List<Student> byName = studentRepository.findByName(name);
        //then
        assertEquals(1, byName.size());

        System.out.println("byName = " + byName);
    }

    @Test
    @DisplayName("도시이름과 전공을 학생을 조회")
    void findByCityAndMajorTest() {
        //given
        String city = "제주도";
        String major = "화학공학";
        //when
        List<Student> byCity = studentRepository.findByCityAndMajor(city, major);

        //then
        System.out.println("\n\n\n\n\n");
        System.out.println("byCity = " + byCity);
        System.out.println("\n\n\n\n\n");
    }

    @Test
    @DisplayName("전공이 공학으로 끝나는 학생들 조회")
    void findByMajorEndingTest() {
        //given
        String majorContaining = "공학";
        //when
        List<Student> students = studentRepository.findByMajorContaining(majorContaining);
        //then
        System.out.println("\n\n\n\n\n");
        System.out.println("students = " + students + "\n");
        System.out.println("\n\n\n\n\n");
    }

    @Test
    @DisplayName("도시 또는 이름으로 학생을 조회")
    void nativeSQLTest() {
        //given
        String name = "춘식이";
        String city = "제주도";
        //when
        List<Student> student = studentRepository.getStudentByNameOrCity(name, city);
        //then
        System.out.println("\n\n\n\n\n");
        System.out.println("students = " + student + "\n");
        System.out.println("\n\n\n\n\n");
    }
}