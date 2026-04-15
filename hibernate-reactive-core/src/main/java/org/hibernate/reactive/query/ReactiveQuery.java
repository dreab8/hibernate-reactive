/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.reactive.query;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.PessimisticLockScope;
import jakarta.persistence.Timeout;
import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jakarta.persistence.metamodel.Type;
import org.hibernate.CacheMode;
import org.hibernate.LockMode;
import org.hibernate.Locking;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.query.Query;
import org.hibernate.query.QueryFlushMode;
import org.hibernate.query.QueryParameter;
import org.hibernate.query.ResultListTransformer;
import org.hibernate.query.TupleTransformer;
import org.hibernate.query.spi.QueryOptions;

import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Parameter;
import jakarta.persistence.TemporalType;
import java.util.Optional;

/**
 * @see org.hibernate.query.Query
 */
public interface ReactiveQuery<R> extends Query<R> {

	String getQueryString();

	@Override
	ReactiveSelectionQuery<R> asSelectionQuery();

	@Override
	<R> ReactiveSelectionQuery<R> ofType(Class<R> type);

	@Override
	<R> ReactiveSelectionQuery<R> withEntityGraph(EntityGraph<R> entityGraph);

	@Override
	ReactiveMutationQuery<R> asStatement();

	ReactiveQuery<R> setQueryFlushMode(QueryFlushMode queryFlushMode);

	@Override
	ReactiveQuery<R> setComment(String comment);

	ReactiveQuery<R> addQueryHint(String hint);

	@Override
	ReactiveQuery<R> setTimeout(int timeout);

	@Override
	ReactiveQuery<R> setTimeout(Integer timeout);

	@Override
	ReactiveQuery<R> setTimeout(Timeout timeout);

	boolean isQueryPlanCacheable();

	ReactiveQuery<R> setQueryPlanCacheable(boolean queryPlanCacheable);

	@Override
	ReactiveQuery<R> setHint(String hintName, Object value);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Parameter Handling

	/**
	 * {@inheritDoc}
	 */
	@Override
	ReactiveQuery<R> setParameter(String parameter, Object argument);

	/**
	 * {@inheritDoc}
	 */
	@Override
	<P> ReactiveQuery<R> setParameter(String parameter, P argument, Class<P> type);

	/**
	 * {@inheritDoc}
	 */
	@Override
	<P> ReactiveQuery<R> setParameter(String parameter, P argument, Type<P> type);

	/**
	 * {@inheritDoc}
	 */
	@Override
	ReactiveQuery<R> setParameter(int parameter, Object argument);

	/**
	 * {@inheritDoc}
	 */
	@Override
	<P> ReactiveQuery<R> setParameter(int parameter, P argument, Class<P> type);

	/**
	 * {@inheritDoc}
	 */
	@Override
	<P> ReactiveQuery<R> setParameter(int parameter, P argument, Type<P> type);

	/**
	 * {@inheritDoc}
	 */
	@Override
	<P> ReactiveQuery<R> setParameter(QueryParameter<P> parameter, P argument);

	/**
	 * {@inheritDoc}
	 */
	@Override
	<P> ReactiveQuery<R> setParameter(QueryParameter<P> parameter, P argument, Class<P> type);

	/**
	 * {@inheritDoc}
	 */
	@Override
	<P> ReactiveQuery<R> setParameter(QueryParameter<P> parameter, P argument, Type<P> type);

	/**
	 * {@inheritDoc}
	 */
	@Override
	<P> ReactiveQuery<R> setParameter(Parameter<P> parameter, P argument);

	/**
	 * {@inheritDoc}
	 */
	@Override
	<P> ReactiveQuery<R> setConvertedParameter(String name, P value, Class<? extends AttributeConverter<P, ?>> converter);

	/**
	 * {@inheritDoc}
	 */
	@Override
	<P> ReactiveQuery<R> setConvertedParameter(int position, P value, Class<? extends AttributeConverter<P, ?>> converter);

	/**
	 * {@inheritDoc}
	 */
	@Override
	ReactiveQuery<R> setProperties(Object bean);

	/**
	 * {@inheritDoc}
	 */
	@Override
	ReactiveQuery<R> setProperties(@SuppressWarnings("rawtypes") Map bean);

	/**
	 * {@inheritDoc}
	 */
	@Override
	ReactiveQuery<R> setParameterList(String parameter, @SuppressWarnings("rawtypes") Collection arguments);

	/**
	 * {@inheritDoc}
	 */
	@Override
	<P> ReactiveQuery<R> setParameterList(String parameter, Collection<? extends P> arguments, Class<P> javaType);

	/**
	 * {@inheritDoc}
	 */
	@Override
	<P> ReactiveQuery<R> setParameterList(String parameter, Collection<? extends P> arguments, Type<P> type);

	/**
	 * {@inheritDoc}
	 */
	@Override
	ReactiveQuery<R> setParameterList(String parameter, Object[] values);

	/**
	 * {@inheritDoc}
	 */
	@Override
	<P> ReactiveQuery<R> setParameterList(String parameter, P[] arguments, Class<P> javaType);

	/**
	 * {@inheritDoc}
	 */
	@Override
	<P> ReactiveQuery<R> setParameterList(String parameter, P[] arguments, Type<P> type);

	/**
	 * {@inheritDoc}
	 */
	@Override
	ReactiveQuery<R> setParameterList(int parameter, @SuppressWarnings("rawtypes") Collection arguments);

	/**
	 * {@inheritDoc}
	 */
	@Override
	<P> ReactiveQuery<R> setParameterList(int parameter, Collection<? extends P> arguments, Class<P> javaType);

	/**
	 * {@inheritDoc}
	 */
	@Override
	<P> ReactiveQuery<R> setParameterList(int parameter, Collection<? extends P> arguments, Type<P> type);

	/**
	 * {@inheritDoc}
	 */
	@Override
	ReactiveQuery<R> setParameterList(int parameter, Object[] arguments);

	@Override
	<P> ReactiveQuery<R> setParameterList(int parameter, P[] arguments, Class<P> javaType);

	@Override
	<P> ReactiveQuery<R> setParameterList(int parameter, P[] arguments, Type<P> type);

	@Override
	<P> ReactiveQuery<R> setParameterList(QueryParameter<P> parameter, Collection<? extends P> arguments);

	@Override
	<P> ReactiveQuery<R> setParameterList(QueryParameter<P> parameter, Collection<? extends P> arguments, Class<P> javaType);

	@Override
	<P> ReactiveQuery<R> setParameterList(QueryParameter<P> parameter, Collection<? extends P> arguments, Type<P> type);

	@Override
	<P> ReactiveQuery<R> setParameterList(QueryParameter<P> parameter, P[] arguments);

	@Override
	<P> ReactiveQuery<R> setParameterList(QueryParameter<P> parameter, P[] arguments, Class<P> javaType);

	@Override
	<P> ReactiveQuery<R> setParameterList(QueryParameter<P> parameter, P[] arguments, Type<P> type);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Methods which inherently imply either selection or mutation queries

	@Deprecated
	Integer getFetchSize();

	@Deprecated
	ReactiveQuery<R> setFetchSize(int fetchSize);

	boolean isReadOnly();

	ReactiveQuery<R> setReadOnly(boolean readOnly);

	@Deprecated
	boolean isCacheable();

	/**
	 * Enable/disable second level query (result) caching for this query.
	 *
	 * @see #isCacheable
	 *
	 * @deprecated Use {@linkplain ReactiveSelectionQuery} instead as caching is only relevant for
	 * selection queries
	 */
	@Deprecated(since = "8.0", forRemoval = true)
	ReactiveQuery<R> setCacheable(boolean cacheable);

	@Deprecated
	CacheMode getCacheMode();

	@Deprecated
	ReactiveQuery<R> setCacheMode(CacheMode cacheMode);

	@Deprecated
	String getCacheRegion();

	@Deprecated
	ReactiveQuery<R> setCacheRegion(String cacheRegion);

	/**
	 * @deprecated Use {@linkplain ReactiveSelectionQuery} instead as second-level cache
	 * interaction is only relevant for queries which return results.
	 */
	@Deprecated
	@Override
	ReactiveQuery<R> setCacheStoreMode(CacheStoreMode cacheStoreMode);

	/**
	 * @deprecated Use {@linkplain ReactiveSelectionQuery} instead as second-level cache
	 * interaction is only relevant for queries which return results.
	 */
	@Override @Deprecated
	ReactiveQuery<R> setCacheRetrieveMode(CacheRetrieveMode cacheRetrieveMode);

	/**
	 * @deprecated Use {@linkplain ReactiveSelectionQuery} instead as applying result limits
	 * is only relevant for queries which return results.
	 */
	@Deprecated
	@Override
	ReactiveQuery<R> setMaxResults(int maxResults);

	@Deprecated
	@Override
	ReactiveQuery<R> setFirstResult(int startPosition);


	@Override
	@Deprecated
	ReactiveQuery<R> setLockMode(LockModeType lockMode);

	@Deprecated
	LockMode getHibernateLockMode();

	@Deprecated
	ReactiveQuery<R> setHibernateLockMode(LockMode lockMode);

	@Deprecated
	Timeout getLockTimeout();

	@Deprecated
	ReactiveQuery<R> setLockTimeout(Timeout lockTimeout);

	@Deprecated
	ReactiveQuery<R> setLockScope(PessimisticLockScope lockScope);

	@Deprecated
	ReactiveQuery<R> setFollowOnLockingStrategy(Locking.FollowOn strategy);

	@Deprecated
	ReactiveQuery<R> setFollowOnStrategy(Locking.FollowOn strategy);

	<X> ReactiveQuery<X> setTupleTransformer(TupleTransformer<X> transformer);

	ReactiveQuery<R> setResultListTransformer(ResultListTransformer<R> transformer);

	@Deprecated
	List<R> list();

//	@Override
//	@Deprecated
//	default List<R> getResultList() {
//		return list();
//	}

	@Deprecated
	ScrollableResults<R> scroll();

	@Deprecated
	ScrollableResults<R> scroll(ScrollMode scrollMode);

//	@Override
//	@Deprecated
//	default Stream<R> getResultStream() {
//		return stream();
//	}
//
//	@Deprecated
//	default Stream<R> stream() {
//		return list().stream();
//	}

	@Deprecated
	R uniqueResult();

	@Override
	@Deprecated
	R getSingleResult();

	@Deprecated
	Optional<R> uniqueResultOptional();

	@Override
	@Deprecated
	int executeUpdate();

//	@Override @Deprecated
//	default FlushModeType getFlushMode() {
//		final QueryFlushMode queryFlushMode = getQueryFlushMode();
//		if ( queryFlushMode == null ) {
//			return FlushModeType.AUTO;
//		}
//		return queryFlushMode.toJpaFlushMode();
//	}

	@Override @Deprecated
	default ReactiveQuery<R> setFlushMode(FlushModeType flushMode) {
		setQueryFlushMode( QueryFlushMode.fromJpaMode( flushMode ) );
		return this;
	}

	/**
	 * Get the execution options for this {@code Query}. Many of the setters
	 * of this object update the state of the returned {@link QueryOptions}.
	 * This is useful because it gives access to s primitive value in its
	 * (nullable) wrapper form, rather than the primitive form as required
	 * by JPA. This allows us to distinguish whether a value has been
	 * explicitly set by the client.
	 *
	 * @return Return the encapsulation of this query's options.
	 *
	 * @deprecated The various ReactiveQuery<R> subtypes already expose all relevant options;
	 * plus exposing QueryOptions is layer-breaking as it is an SPI contract
	 * exposed on an API.
	 */
	@Deprecated(since = "8.0", forRemoval = true)
	QueryOptions getQueryOptions();

	/**
	 * {@link jakarta.persistence.Query} override
	 */
	@Override @Deprecated
	ReactiveQuery<R> setParameter(Parameter<Calendar> parameter, Calendar argument, TemporalType temporalType);

	/**
	 * {@link jakarta.persistence.Query} override
	 */
	@Override @Deprecated
	ReactiveQuery<R> setParameter(Parameter<Date> parameter, Date argument, TemporalType temporalType);

	/**
	 * Bind an {@link Instant} value to the named ReactiveQuery<R> parameter using
	 * just the portion indicated by the given {@link TemporalType}.
	 */
	@Deprecated
	ReactiveQuery<R> setParameter(String parameter, Instant argument, TemporalType temporalType);

	/**
	 * {@link jakarta.persistence.Query} override
	 */
	@Override @Deprecated
	ReactiveQuery<R> setParameter(String parameter, Calendar argument, TemporalType temporalType);

	/**
	 * {@link jakarta.persistence.Query} override
	 */
	@Override @Deprecated
	ReactiveQuery<R> setParameter(String parameter, Date argument, TemporalType temporalType);

	/**
	 * Bind an {@link Instant} value to the ordinal ReactiveQuery<R> parameter using
	 * just the portion indicated by the given {@link TemporalType}.
	 */
	@Deprecated
	ReactiveQuery<R> setParameter(int parameter, Instant argument, TemporalType temporalType);

	/**
	 * {@link jakarta.persistence.Query} override
	 */
	@Override @Deprecated
	ReactiveQuery<R> setParameter(int parameter, Date argument, TemporalType temporalType);

	/**
	 * {@link jakarta.persistence.Query} override
	 */
	@Override @Deprecated
	ReactiveQuery<R> setParameter(int parameter, Calendar argument, TemporalType temporalType);
}
