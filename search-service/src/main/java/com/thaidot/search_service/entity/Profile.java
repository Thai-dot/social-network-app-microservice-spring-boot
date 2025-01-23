package com.thaidot.search_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "profile_index")
public class Profile {

    @Id
    private String userID;
    private String firstName;
    private String lastName;
    private String city;

    @Field(type = FieldType.Date, format = DateFormat.date)
    private LocalDate dob;
    private String avatarUrl;
}
