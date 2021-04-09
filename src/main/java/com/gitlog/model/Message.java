package com.gitlog.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {
    private String message1;


    @Builder
    public Message(String message1){
        this.message1 = message1;

    }
}
