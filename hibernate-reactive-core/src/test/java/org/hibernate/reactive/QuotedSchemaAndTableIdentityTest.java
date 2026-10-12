/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.reactive;

import io.vertx.junit5.Timeout;
import java.util.Collection;
import java.util.List;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.reactive.containers.DatabaseConfiguration;
import org.hibernate.reactive.provider.Settings;
import org.hibernate.reactive.testing.SqlStatementTracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.vertx.junit5.VertxTestContext;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import static java.util.concurrent.TimeUnit.MINUTES;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Reproducer for https://github.com/hibernate/hibernate-reactive/issues/4096.
 * <p>
 * The entity's table name and schema are both explicitly quoted (using the
 * classic Hibernate back-tick quoting convention), so that the generated
 * insert looks like:
 * <pre>
 *     insert into "pippo"."FILM" (title, id) values (?, default)
 * </pre>
 * which mirrors the {@code insert into "SAKILA"."FILM" (...) values (..., default)}
 * statement reported in the issue for an identity-generated entity on Oracle.
 * The {@code pippo} schema is created automatically via
 * {@link Settings#HBM2DDL_CREATE_SCHEMAS}.
 */
@Timeout(value = 10, timeUnit = MINUTES)
public class QuotedSchemaAndTableIdentityTest extends BaseReactiveTest {

	private static SqlStatementTracker sqlTracker;

	@Override
	protected Collection<Class<?>> annotatedEntities() {
		return List.of( QuotedFilm.class );
	}

	@Override
	protected Configuration constructConfiguration() {
		Configuration configuration = super.constructConfiguration();

		sqlTracker = new SqlStatementTracker( s -> true, configuration.getProperties() );
		return configuration;
	}

	@Override
	protected void addServices(StandardServiceRegistryBuilder builder) {
		sqlTracker.registerService( builder );
	}

	@BeforeEach
	public void clearTracker() {
		sqlTracker.clear();
	}

	@Test
	public void testInsertWithQuotedSchemaAndTable(VertxTestContext context) {
		final QuotedFilm film = new QuotedFilm( "Hibernate Reactive, the Movie" );

		test( context, getMutinySessionFactory().withTransaction( s -> s
				.persist( film )
				.call( s::flush )
				.invoke( () -> {
					assertThat( film.getId() ).isNotNull();
					assertThat( sqlTracker.getLoggedQueries() ).isNotEmpty();
					assertThat( sqlTracker.getLoggedQueries().get( 0 ) ).contains( "insert" );
				} )
		) );
	}

	@Entity(name = "QuotedFilm")
	@Table(name = "`FILM`", schema = "\"" + DatabaseConfiguration.USERNAME + "\"")
	public static class QuotedFilm {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		private String title;

		public QuotedFilm() {
		}

		public QuotedFilm(String title) {
			this.title = title;
		}

		public Long getId() {
			return id;
		}

		public String getTitle() {
			return title;
		}
	}
}
