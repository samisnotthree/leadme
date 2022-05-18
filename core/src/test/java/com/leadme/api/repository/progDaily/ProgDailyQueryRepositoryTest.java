package com.leadme.api.repository.progDaily;

import com.leadme.api.dto.ProgDailyDto;
import com.leadme.api.dto.condition.ProgDailySearchCondition;
import com.leadme.api.dto.sdto.ProgDailyProgDto;
import com.leadme.api.entity.Prog;
import com.leadme.api.entity.ProgDaily;
import com.leadme.api.repository.prog.ProgRepository;
import com.leadme.api.service.ProgDailyService;
import com.leadme.api.service.ProgService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProgDailyQueryRepositoryTest {
    @Autowired ProgDailyQueryRepository progDailyQueryRepository;
    @Autowired
    ProgDailyService progDailyService;
    @Autowired
    ProgService progService;
    @Autowired
    ProgRepository progRepository;

    @BeforeEach
    void init_data() {
        Prog prog = Prog.builder()
                .name("name11")
                .desc("desc11")
                .build();
        progService.addProg(prog);

        ProgDaily progDaily = ProgDaily.builder()
                .progDate("202205151500")
                .prog(prog)
                .build();
        progDailyService.addProgDaily(progDaily);

        ProgDaily progDaily2 = ProgDaily.builder()
                .progDate("202205151630")
                .prog(prog)
                .build();
        progDailyService.addProgDaily(progDaily2);
    }

    @Test
    @DisplayName("특정 날짜 일정 조회")
    @Transactional
    void search_schedules() {
        //given
        Prog prog = progRepository.findAll().get(0);

        ProgDailySearchCondition condition = new ProgDailySearchCondition();
        condition.setProgId(prog.getProgId());
        condition.setProgDate("20220515");

        //when
        List<ProgDailyProgDto> schedules = progDailyQueryRepository.findSchedules(condition);

        //then
        assertThat(schedules).extracting("progDate").containsExactly("202205151500", "202205151630");
    }
}
