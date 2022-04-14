package com.leadme.core.service;

import com.leadme.core.entity.Guide;
import com.leadme.core.entity.Member;
import com.leadme.core.repository.GuideRepository;
import com.leadme.core.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GuideServiceTest {

    @Autowired MemberRepository memberRepository;
    @Autowired GuideRepository guideRepository;
    @Autowired GuideService guideService;

    @Test
    @DisplayName("가이드 등록")
    @Transactional
    void joinGuide() {
        // given
        Member member = Member.builder()
            .email("test@test.com")
            .name("testName")
            .pass("testPass")
            .phone("testPhone")
            .photo("testPhoto")
            .inDate(LocalDateTime.now())
            .outDate(null)
            .guide(null)
            .build();

        Member savedMember = memberRepository.save(member);

        String desc = "testDesc";

        // when
        Long joinedGuideId = guideService.joinGuide(savedMember.getMemberId(), desc).getGuideId();
        Guide foundGuide = guideRepository.findById(joinedGuideId).get();

        // then
        assertThat(foundGuide.getMember()).isSameAs(savedMember);
        assertThat(foundGuide.getDesc()).isEqualTo(desc);
    }

    @Test
    @DisplayName("가이드 삭제")
    @Transactional
    void deleteGuide() {
        // given
        Member member = Member.builder()
            .email("test@test.com")
            .name("testName")
            .pass("testPass")
            .phone("testPhone")
            .photo("testPhoto")
            .inDate(LocalDateTime.now())
            .outDate(null)
            .guide(null)
            .build();

        Member savedMember = memberRepository.save(member);

        String desc = "testDesc";

        // when
        Long joinedGuideId = guideService.joinGuide(savedMember.getMemberId(), desc).getGuideId();
        guideService.deleteGuide(joinedGuideId);

        Guide foundGuide = guideRepository.findById(joinedGuideId).get();

        //then
        assertThat(foundGuide.getOutDate()).isNotNull();
    }
}
