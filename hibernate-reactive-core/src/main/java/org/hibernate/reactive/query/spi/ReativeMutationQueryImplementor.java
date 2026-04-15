/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.reactive.query.spi;

import org.hibernate.ScrollMode;
import org.hibernate.query.QueryFlushMode;
import org.hibernate.query.QueryParameter;
import org.hibernate.query.named.StatementReferenceProducer;
import org.hibernate.query.spi.ParameterMetadataImplementor;
import org.hibernate.query.spi.ScrollableResultsImplementor;
import org.hibernate.reactive.query.ReactiveMutationQuery;
import org.hibernate.reactive.query.ReactiveQueryImplementor;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Parameter;
import jakarta.persistence.PessimisticLockScope;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Timeout;
import jakarta.persistence.metamodel.Type;
import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public interface ReativeMutationQueryImplementor<R> extends ReactiveMutationQuery<R>, ReactiveQueryImplementor<R>,
		StatementReferenceProducer {

	@Override
	default String getMutationString() {
		return getQueryString();
	}

	@Override
	Class<R> getTargetType();

	@Override
	default ReativeMutationQueryImplementor<R> asStatement() {
		return this;
	}

	@Override
	default ReativeMutationQueryImplementor<R> asMutationQuery() {
		return this;
	}

	@Override
	ReativeMutationQueryImplementor<R> setTimeout(int timeout);

	@Override
	ReativeMutationQueryImplementor<R> setTimeout(Integer integer);

	@Override
	ReativeMutationQueryImplementor<R> setTimeout(Timeout timeout);

	@Override
	ReativeMutationQueryImplementor<R> setComment(String comment);

	@Override
	ReativeMutationQueryImplementor<R> setQueryFlushMode(QueryFlushMode queryFlushMode);

	@Override
	ReativeMutationQueryImplementor<R> addQueryHint(String hint);

	@Override
	ReativeMutationQueryImplementor<R> setHint(String hintName, Object value);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Parameters

	@Override
	ParameterMetadataImplementor getParameterMetadata();

	@Override
	ReativeMutationQueryImplementor<R> setParameter(String name, Object value);

	@Override
	<P> ReativeMutationQueryImplementor<R> setParameter(String name, P value, Class<P> type);

	@Override
	<P> ReativeMutationQueryImplementor<R> setParameter(String name, P value, Type<P> type);

	@Override
	ReativeMutationQueryImplementor<R> setParameter(int position, Object value);

	@Override
	<P> ReativeMutationQueryImplementor<R> setParameter(int position, P value, Class<P> type);

	@Override
	<P> ReativeMutationQueryImplementor<R> setParameter(int position, P value, Type<P> type);

	@Override
	<P> ReativeMutationQueryImplementor<R> setParameter(QueryParameter<P> parameter, P value);

	@Override
	<P> ReativeMutationQueryImplementor<R> setParameter(QueryParameter<P> parameter, P value, Class<P> type);

	@Override
	<P> ReativeMutationQueryImplementor<R> setParameter(QueryParameter<P> parameter, P val, Type<P> type);

	@Override
	<P> ReativeMutationQueryImplementor<R> setParameter(Parameter<P> param, P value);

	@Override
	ReativeMutationQueryImplementor<R> setParameterList(String name, @SuppressWarnings("rawtypes") Collection values);

	@Override
	<P> ReativeMutationQueryImplementor<R> setParameterList(String name, Collection<? extends P> values, Class<P> javaType);

	@Override
	<P> ReativeMutationQueryImplementor<R> setParameterList(String name, Collection<? extends P> values, Type<P> type);

	@Override
	ReativeMutationQueryImplementor<R> setParameterList(String name, Object[] values);

	@Override
	<P> ReativeMutationQueryImplementor<R> setParameterList(String name, P[] values, Class<P> javaType);

	@Override
	<P> ReativeMutationQueryImplementor<R> setParameterList(String name, P[] values, Type<P> type);

	@Override
	ReativeMutationQueryImplementor<R> setParameterList(int position, @SuppressWarnings("rawtypes") Collection values);

	@Override
	<P> ReativeMutationQueryImplementor<R> setParameterList(int position, Collection<? extends P> values, Class<P> javaType);

	@Override
	<P> ReativeMutationQueryImplementor<R> setParameterList(int position, Collection<? extends P> values, Type<P> type);

	@Override
	ReativeMutationQueryImplementor<R> setParameterList(int position, Object[] values);

	@Override
	<P> ReativeMutationQueryImplementor<R> setParameterList(int position, P[] values, Class<P> javaType);

	@Override
	<P> ReativeMutationQueryImplementor<R> setParameterList(int position, P[] values, Type<P> type);

	@Override
	<P> ReativeMutationQueryImplementor<R> setParameterList(QueryParameter<P> parameter, Collection<? extends P> values);

	@Override
	<P> ReativeMutationQueryImplementor<R> setParameterList(QueryParameter<P> parameter, Collection<? extends P> values, Class<P> javaType);

	@Override
	<P> ReativeMutationQueryImplementor<R> setParameterList(QueryParameter<P> parameter, Collection<? extends P> values, Type<P> type);

	@Override
	<P> ReativeMutationQueryImplementor<R> setParameterList(QueryParameter<P> parameter, P[] values);

	@Override
	<P> ReativeMutationQueryImplementor<R> setParameterList(QueryParameter<P> parameter, P[] values, Class<P> javaType);

	@Override
	<P> ReativeMutationQueryImplementor<R> setParameterList(QueryParameter<P> parameter, P[] values, Type<P> type);

	@Override
	ReativeMutationQueryImplementor<R> setProperties(Object bean);

	@Override
	ReativeMutationQueryImplementor<R> setProperties(@SuppressWarnings("rawtypes") Map bean);

	@Override
	<P> ReativeMutationQueryImplementor<R> setConvertedParameter(String name, P value, Class<? extends AttributeConverter<P, ?>> converter);

	@Override
	<P> ReativeMutationQueryImplementor<R> setConvertedParameter(int position, P value, Class<? extends AttributeConverter<P, ?>> converter);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// SelectionQuery stuff

	@Override
	default <X> ReactiveSelectionQueryImplementor<X> ofType(Class<X> aClass) {
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override
	default<X> ReactiveSelectionQueryImplementor<X> withEntityGraph(EntityGraph<X> entityGraph) {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override @SuppressWarnings("removal")
	default LockModeType getLockMode() {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override
	default ReactiveQueryImplementor<R> setLockMode(LockModeType lockMode) {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override
	default ReactiveQueryImplementor<R> setLockScope(PessimisticLockScope lockScope) {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override @SuppressWarnings("removal")
	default CacheStoreMode getCacheStoreMode() {
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override
	default ReactiveQueryImplementor<R> setCacheStoreMode(CacheStoreMode cacheStoreMode) {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override @SuppressWarnings("removal")
	default CacheRetrieveMode getCacheRetrieveMode() {
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override
	default ReactiveQueryImplementor<R> setCacheRetrieveMode(CacheRetrieveMode cacheRetrieveMode) {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override @SuppressWarnings("removal")
	default int getMaxResults() {
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override
	default ReactiveQueryImplementor<R> setMaxResults(int maxResults) {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}


	@Override @SuppressWarnings("removal")
	default int getFirstResult() {
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override
	default ReactiveQueryImplementor<R> setFirstResult(int startPosition) {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override
	default ScrollableResultsImplementor<R> scroll() {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override
	default ScrollableResultsImplementor<R> scroll(ScrollMode scrollMode) {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override
	default List<R> list() {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override @SuppressWarnings("deprecation")
	default List<R> getResultList() {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString()  );	}

	@Override @SuppressWarnings("deprecation")
	default Stream<R> getResultStream() {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString()  );	}


	@Override @SuppressWarnings("deprecation")
	default Stream<R> stream() {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString()  );
	}

	@Override
	default R uniqueResult() {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString()  );
	}

	@Override
	default Optional<R> uniqueResultOptional() {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString()  );
	}

	@Override
	default R getSingleResult() {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString()  );
	}


	@Override @SuppressWarnings("removal")
	default R getSingleResultOrNull() {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString()  );
	}


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// deprecations


	@Override @SuppressWarnings("deprecation")
	ReativeMutationQueryImplementor<R> setFlushMode(FlushModeType flushMode);

	@Override @Deprecated
	ReativeMutationQueryImplementor<R> setParameter(String name, Instant value, TemporalType temporalType);

	@Override @Deprecated
	ReativeMutationQueryImplementor<R> setParameter(String name, Calendar value, TemporalType temporalType);

	@Override @Deprecated
	ReativeMutationQueryImplementor<R> setParameter(String name, Date value, TemporalType temporalType);

	@Override @Deprecated
	ReativeMutationQueryImplementor<R> setParameter(int position, Instant value, TemporalType temporalType);

	@Override @Deprecated
	ReativeMutationQueryImplementor<R> setParameter(int position, Date value, TemporalType temporalType);

	@Override @Deprecated
	ReativeMutationQueryImplementor<R> setParameter(int position, Calendar value, TemporalType temporalType);

	@Override @Deprecated
	ReativeMutationQueryImplementor<R> setParameter(Parameter<Calendar> param, Calendar value, TemporalType temporalType);

	@Override @Deprecated
	ReativeMutationQueryImplementor<R> setParameter(Parameter<Date> param, Date value, TemporalType temporalType);
}
