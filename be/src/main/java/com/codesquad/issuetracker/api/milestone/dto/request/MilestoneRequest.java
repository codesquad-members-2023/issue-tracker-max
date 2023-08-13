package com.codesquad.issuetracker.api.milestone.dto.request;

import static com.codesquad.issuetracker.common.unit.ConverterFormatter.DATE_TIME_PATTERN;

import com.codesquad.issuetracker.api.milestone.domain.Milestone;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MilestoneRequest {

    private String title;
    private String description;
    private String dueDate;

    public MilestoneRequest(String title, String description, String dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    public Milestone toEntityByOrganizationId(Long organizationId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        LocalDate localDate = LocalDate.parse(this.dueDate, formatter);
        return Milestone.builder()
                .organizationId(organizationId)
                .title(this.title)
                .description(this.description)
                .isClosed(false)
                .dueDate(localDate)
                .build();
    }

    public Milestone toEntityByMilestoneId(Long milestoneId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        LocalDate localDate = LocalDate.parse(this.dueDate, formatter);
        return Milestone.builder()
                .id(milestoneId)
                .title(this.title)
                .description(this.description)
                .isClosed(false)
                .dueDate(localDate)
                .build();
    }
}
