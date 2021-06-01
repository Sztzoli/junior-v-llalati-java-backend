package org.example.meetingrooms.domain;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class BaseEntity implements Serializable {

    private Long id;
}
