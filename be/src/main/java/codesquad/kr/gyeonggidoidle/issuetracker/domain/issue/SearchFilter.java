package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue;

import codesquad.kr.gyeonggidoidle.issuetracker.exception.ParsingFilterConditionException;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class SearchFilter {

    private final static SearchFilter NULL_SEARCH_FILTER = SearchFilter.builder().build();

    private final Boolean isOpen;
    private final String assignee;
    private final String label;
    private final String milestone;
    private final String author;

    @Builder
    private SearchFilter(Boolean isOpen, String assignee, String label, String milestone, String author) {
        this.isOpen = isOpen;
        this.assignee = assignee;
        this.label = label;
        this.milestone = milestone;
        this.author = author;
    }

    public static SearchFilter from(String filterCondition) {
        try {
            Map<String, String> parsedFilterCondition = parseFilterCondition(filterCondition);

            return SearchFilter.builder()
                    .isOpen(checkOpen(parsedFilterCondition.get("is")))
                    .assignee(parsedFilterCondition.get("assignee"))
                    .label(parsedFilterCondition.get("label"))
                    .milestone(parsedFilterCondition.get("milestone"))
                    .author(parsedFilterCondition.get("author"))
                    .build();
        } catch (ParsingFilterConditionException e) {
            return NULL_SEARCH_FILTER;
        }
    }

    private static Map<String, String> parseFilterCondition(String filterCondition) {
        try {
            String[] conditionWithoutBlank = filterCondition.split(" ");

            return Arrays.stream(conditionWithoutBlank)
                    .map(s -> s.split(":"))
                    .collect(Collectors.toUnmodifiableMap(
                            splitString -> splitString[0],
                            splitString -> splitString[1]
                    ));
        } catch (RuntimeException e) {
            throw new ParsingFilterConditionException();
        }
    }

    private static Boolean checkOpen(String value) {
        if (value == null) {
            return null;
        }
        if (value.equals("open")) {
            return true;
        }
        if (value.equals("closed")) {
            return false;
        }
        return null;
    }
}
