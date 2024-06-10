package com.github.minsoozz.entitymanagerexample.domain;

import java.time.LocalDateTime;

public record Member(
        Long id,
        String name,
        LocalDateTime createDate
) {
}
