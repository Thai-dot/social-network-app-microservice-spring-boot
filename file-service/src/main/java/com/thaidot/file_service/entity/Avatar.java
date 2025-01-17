package com.thaidot.file_service.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(value = "avatar")
public class Avatar {

    @MongoId
    String id;
    String url;
    String userID;
}
