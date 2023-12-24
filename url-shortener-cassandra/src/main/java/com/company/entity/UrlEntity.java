package com.company.entity;

import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "url_entity")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UrlEntity {
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private String id;

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, value = "long_url")
    private String longUrl;
}

