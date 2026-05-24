package project.commercePJT.domain;

import lombok.Getter;

@Getter
public enum Level {

    BRONZE("브론즈"),
    SILVER("실버"),
    GOLD("골드");

    private final String grade;

    Level(String grade) {
        this.grade = grade;
    }

}
