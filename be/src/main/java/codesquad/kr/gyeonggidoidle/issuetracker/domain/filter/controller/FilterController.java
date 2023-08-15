package codesquad.kr.gyeonggidoidle.issuetracker.domain.filter.controller;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.filter.controller.response.FilterResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.filter.service.FilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/filters")
public class FilterController {

    private final FilterService filterService;

    @GetMapping("/main")
    public FilterResponse getMainFilter() {
        return FilterResponse.from(filterService.getMainFilter());
    }

    @GetMapping("/detail")
    public FilterResponse getDetailFilter() {
        return FilterResponse.from(filterService.getDetailFilter());
    }
}
