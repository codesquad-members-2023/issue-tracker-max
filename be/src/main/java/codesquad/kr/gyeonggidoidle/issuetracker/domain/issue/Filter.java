package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue;

import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class Filter {

    private final Boolean isOpen;
    private final String assignee;
    private final String label;
    private final String milestone;
    private final String author;

    @Builder
    private Filter(Boolean isOpen, String assignee, String label, String milestone, String author) {
        this.isOpen = isOpen;
        this.assignee = assignee;
        this.label = label;
        this.milestone = milestone;
        this.author = author;
    }

    public static Filter from(String filterCondition) {
        Map<String, String> parsedFilterCondition = parseFilterCondition(filterCondition);

        return Filter.builder()
                .isOpen(checkOpen(parsedFilterCondition.get("is")))
                .assignee(parsedFilterCondition.get("assignee"))
                .label(parsedFilterCondition.get("label"))
                .milestone(parsedFilterCondition.get("milestone"))
                .author(parsedFilterCondition.get("author"))
                .build();
    }

    private static Map<String, String> parseFilterCondition(String filterCondition) {
        String[] conditionWithoutBlank = filterCondition.split(" ");

        return Arrays.stream(conditionWithoutBlank)
                .map(s -> s.split(":"))
                .collect(Collectors.toUnmodifiableMap(
                        splitString -> splitString[0],
                        splitString -> splitString[1]
                ));
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
