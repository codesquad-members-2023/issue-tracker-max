package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.vo.IssueVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.Filter;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FilteredIssueRepositoryTest {

    @Autowired
    private FilteredIssueRepository repository;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @BeforeEach
    void setUp() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.insert("testSchema.createTables");
            session.insert("testSchema.insertData");
        }
    }

    @DisplayName("필터된 이슈들을 가지고 온다.")
    @Test
    void findFilteredIssues() {
        //given
        Filter filter = Filter.builder()
                .isOpen(true)
                .assignee("nag")
                .label("라벨 1")
                .build();

        //when
        List<IssueVO> actual = repository.findByFilter(filter);

        //then
        assertSoftly(assertions -> {
           assertions.assertThat(actual).hasSize(2);
           assertions.assertThat(actual.get(0).getId()).isEqualTo(3L);
           assertions.assertThat(actual.get(1).getAuthor()).isEqualTo("nag");
        });
    }

    @DisplayName("필터조건에 맞는 이슈가 없으면 빈 러스트를 반환한다.")
    @Test
    void findEmptyIssues() {
        Filter filter = Filter.builder()
                .isOpen(false)
                .assignee("nag")
                .label("라벨 2")
                .author("nag")
                .build();

        //when
        List<IssueVO> actual = repository.findByFilter(filter);

        //then
        assertThat(actual).isEmpty();
    }
}
