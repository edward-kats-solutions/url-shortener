package com.company.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;


@TypeAlias("url_entity")
@Document(collection = "url_entity")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UrlEntity {

    @Id
    private String id;

    @Field(name = "long_url", targetType = FieldType.STRING)
    private String longUrl;
}
