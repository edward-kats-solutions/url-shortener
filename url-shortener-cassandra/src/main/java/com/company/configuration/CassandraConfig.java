package com.company.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.cassandra.keyspace-name}")
    private String keyspaceName;

    @Value("${spring.cassandra.schema-action}")
    private SchemaAction schemaAction;

    @Value("${spring.cassandra.contact-points}")
    private String contactPoints;

    @Nonnull
    @Override
    protected String getKeyspaceName() {
        return this.keyspaceName;
    }

    @Nonnull
    @Override
    protected String getContactPoints() {
        return this.contactPoints;
    }

    @Nonnull
    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        return Collections.singletonList(
                CreateKeyspaceSpecification.createKeyspace(keyspaceName)
                        .ifNotExists()
                        .with(KeyspaceOption.DURABLE_WRITES, true)
                        .withSimpleReplication(1)
        );
    }

    @Nonnull
    @Override
    public SchemaAction getSchemaAction() {
        return this.schemaAction;
    }
}
