package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.SearchFilter;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.result.IssueSearchResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"classpath:schema/schema.sql", "classpath:schema/data.sql"})
class IssueSearchRepositoryTest {

    @Autowired
    private IssueSearchRepository repository;

    @DisplayName("필터된 이슈들을 가지고 온다.")
    @Test
    void findFilteredIssues() {
        //given
        SearchFilter searchFilter = SearchFilter.builder()
                .isOpen(true)
                .assignee("nag")
                .label("라벨 1")
                .build();

        //when
        List<IssueSearchResult> actual = repository.findBySearchFilter(searchFilter);

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
        SearchFilter searchFilter = SearchFilter.builder()
                .isOpen(false)
                .assignee("nag")
                .label("라벨 2")
                .author("nag")
                .build();

        //when
        List<IssueSearchResult> actual = repository.findBySearchFilter(searchFilter);

        //then
        assertThat(actual).isEmpty();
    }

    @DisplayName("Filter의 isOpen가 null이면 필터로 검색했을 때 아무것도 나오지 않는다")
    @Test
    void findWithNullIsOpenFilter() {
        SearchFilter searchFilter = SearchFilter.builder()
                .isOpen(null)
                .assignee("nag")
                .label("라벨 2")
                .author("nag")
                .build();

        //when
        List<IssueSearchResult> actual = repository.findBySearchFilter(searchFilter);

        //then
        assertThat(actual).isEmpty();
    }
}
