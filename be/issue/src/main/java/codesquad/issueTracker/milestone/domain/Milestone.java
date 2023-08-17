package codesquad.issueTracker.milestone.domain;

import codesquad.issueTracker.global.exception.CustomException;
import codesquad.issueTracker.global.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class Milestone {

    private Long id;
    private String name;
    private String description;
    private LocalDate doneDate;
    private Boolean isClosed;
    private Boolean isDeleted;


    @Builder
    public Milestone(Long id, String name, String description, LocalDate doneDate, Boolean isClosed,
                     Boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.doneDate = doneDate;
        this.isClosed = isClosed;
        this.isDeleted = isDeleted;
    }


    public void validateDate() {
        LocalDate currentDate = LocalDate.now();
        if (doneDate != null && doneDate.isBefore(currentDate)) {
            throw new CustomException(ErrorCode.INVALIDATE_DATE);
        }
    }
}