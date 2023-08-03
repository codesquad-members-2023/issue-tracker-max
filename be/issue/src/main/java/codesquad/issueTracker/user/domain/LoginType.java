package codesquad.issueTracker.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum LoginType {

    LOCAL("local"),
    GITHUB("github");

    private String type;

    LoginType(String type) {
        this.type = type;
    }

    @JsonCreator
    public static LoginType findByTypeString(String type) {
        return Arrays.stream(values())
                .filter(loginType -> loginType.type.equals(type))
                .findAny()
                .orElseThrow();
    }

    @JsonValue
    public String getType() {
        return type;
    }
}
