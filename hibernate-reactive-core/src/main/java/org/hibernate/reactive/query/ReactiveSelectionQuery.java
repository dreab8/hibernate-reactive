/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.reactive.query;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.PessimisticLockScope;
import jakarta.persistence.Timeout;
import jakarta.persistence.metamodel.Type;
import org.hibernate.CacheMode;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Locking;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.spi.RootGraphImplementor;
import org.hibernate.query.Page;
import org.hibernate.query.QueryFlushMode;
import org.hibernate.query.QueryParameter;
import org.hibernate.query.ResultListTransformer;
import org.hibernate.query.SelectionQuery;
import org.hibernate.query.TupleTransformer;

import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Parameter;
import jakarta.persistence.TemporalType;
import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

/**
 * @see org.hibernate.query.SelectionQuery
 */
public interface ReactiveSelectionQuery<R> extends SelectionQuery<R>, ReactiveQuery<R> {

	default List<R> getResultList() {
		return list();
	}

	default Stream<R> getResultStream() {
		return stream();
	}

	default Stream<R> stream() {
		return list().stream();
	}

	/**
	 * The type of things returned from the query.
	 */
	Class<R> getResultType();

	String getQueryString();

	default CompletionStage<List<R>> getReactiveResultList() {
		return reactiveList();
	}

	CompletionStage<List<R>> reactiveList();

	CompletionStage<R> getReactiveSingleResult();

	CompletionStage<R> getReactiveSingleResultOrNull();

	CompletionStage<Long> getReactiveResultCount();

	CompletionStage<R> reactiveUnique();

	CompletionStage<Optional<R>> reactiveUniqueResultOptional();

	@Override
	ReactiveSelectionQuery<R> setHint(String hintName, Object value);

	@Override
	default ReactiveSelectionQuery<R> setEntityGraph(EntityGraph<? super R> entityGraph) {
		return setEntityGraph( entityGraph, GraphSemantic.LOAD );
	}

	ReactiveSelectionQuery<R> setEntityGraph(EntityGraph<? super R> graph, GraphSemantic semantic);

	// Covariant methods

	@Override
	ReactiveSelectionQuery<R> setFlushMode(FlushModeType flushMode);

	@Override
	ReactiveSelectionQuery<R> setTimeout(int timeout);

	@Override
	ReactiveSelectionQuery<R> setTimeout(Integer timeout);

	@Override
	ReactiveSelectionQuery<R> setTimeout(Timeout timeout);

	@Override
	ReactiveSelectionQuery<R> setComment(String comment);

	Integer getFetchSize();

	ReactiveSelectionQuery<R> setFetchSize(int fetchSize);

	boolean isReadOnly();

	ReactiveSelectionQuery<R> setReadOnly(boolean readOnly);

	ReactiveSelectionQuery<R> setMaxResults(int maxResult);

	int getFirstResult();

	int getMaxResults();

	ReactiveSelectionQuery<R> setFirstResult(int startPosition);

	ReactiveSelectionQuery<R> setPage(Page page);

	CacheMode getCacheMode();

	CacheStoreMode getCacheStoreMode();

	CacheRetrieveMode getCacheRetrieveMode();

	ReactiveSelectionQuery<R> setCacheMode(CacheMode cacheMode);

	ReactiveSelectionQuery<R> setCacheStoreMode(CacheStoreMode cacheStoreMode);

	/**
	 * @see #setCacheMode(CacheMode)
	 */
	ReactiveSelectionQuery<R> setCacheRetrieveMode(CacheRetrieveMode cacheRetrieveMode);

	boolean isCacheable();

	ReactiveSelectionQuery<R> setCacheable(boolean cacheable);

	String getCacheRegion();

	ReactiveSelectionQuery<R> setCacheRegion(String cacheRegion);

	LockOptions getLockOptions();

	LockModeType getLockMode();

	ReactiveSelectionQuery<R> setLockMode(LockModeType lockMode);

	LockMode getHibernateLockMode();

	ReactiveSelectionQuery<R> setHibernateLockMode(LockMode lockMode);

	@Override
	ReactiveSelectionQuery<R> setLockScope(PessimisticLockScope lockScope);

	@Override
	ReactiveSelectionQuery<R> setLockTimeout(Timeout lockTimeout);

	/**
	 * Specifies whether follow-on locking should be applied
	 */
	ReactiveSelectionQuery<R> setFollowOnStrategy(Locking.FollowOn followOnStrategy);

	/**
	 * Set a {@link TupleTransformer}.
	 */
	<X> ReactiveSelectionQuery<X> setTupleTransformer(TupleTransformer<X> transformer);

	/**
	 * Set a {@link ResultListTransformer}.
	 */
	ReactiveSelectionQuery<R> setResultListTransformer(ResultListTransformer<R> transformer);


	void applyGraph(RootGraphImplementor<?> graph, GraphSemantic semantic);

	ReactiveSelectionQuery<R> enableFetchProfile(String profileName);

	ReactiveSelectionQuery<R> disableFetchProfile(String profileName);

	@Override
	ReactiveSelectionQuery<R> setParameter(String name, Object value);

	@Override
	<P> ReactiveSelectionQuery<R> setParameter(String name, P value, Class<P> type);

	@Override
	<P> ReactiveSelectionQuery<R> setParameter(String name, P value, Type<P> type);

	@Override
	ReactiveSelectionQuery<R> setParameter(String name, Instant value, TemporalType temporalType);

	@Override
	ReactiveSelectionQuery<R> setParameter(String name, Calendar value, TemporalType temporalType);

	@Override
	ReactiveSelectionQuery<R> setParameter(String name, Date value, TemporalType temporalType);

	@Override
	ReactiveSelectionQuery<R> setParameter(int position, Object value);

	@Override
	<P> ReactiveSelectionQuery<R> setParameter(int position, P value, Class<P> type);

	@Override
	<P> ReactiveSelectionQuery<R> setParameter(int position, P value, Type<P> type);

	@Override
	ReactiveSelectionQuery<R> setParameter(int position, Instant value, TemporalType temporalType);

	@Override
	ReactiveSelectionQuery<R> setParameter(int position, Date value, TemporalType temporalType);

	@Override
	ReactiveSelectionQuery<R> setParameter(int position, Calendar value, TemporalType temporalType);

	@Override
	<T> ReactiveSelectionQuery<R> setParameter(QueryParameter<T> parameter, T value);

	@Override
	<P> ReactiveSelectionQuery<R> setParameter(QueryParameter<P> parameter, P value, Class<P> type);

	@Override
	<P> ReactiveSelectionQuery<R> setParameter(QueryParameter<P> parameter, P val, Type<P> type);

	@Override
	<T> ReactiveSelectionQuery<R> setParameter(Parameter<T> param, T value);

	@Override
	ReactiveSelectionQuery<R> setParameter(Parameter<Calendar> param, Calendar value, TemporalType temporalType);

	@Override
	ReactiveSelectionQuery<R> setParameter(Parameter<Date> param, Date value, TemporalType temporalType);

	@Override
	ReactiveSelectionQuery<R> setProperties(Object bean);

	@Override
	ReactiveSelectionQuery<R> setProperties(@SuppressWarnings("rawtypes") Map bean);

	@Override
	<P> ReactiveSelectionQuery<R> setConvertedParameter(String name, P value, Class<? extends AttributeConverter<P, ?>> converter);

	@Override
	<P> ReactiveSelectionQuery<R> setConvertedParameter(int position, P value, Class<? extends AttributeConverter<P, ?>> converter);


	@Override
	ReactiveSelectionQuery<R> setParameterList(String name, Collection values);

	@Override
	<P> ReactiveSelectionQuery<R> setParameterList(String name, Collection<? extends P> values, Class<P> javaType);

	@Override
	<P> ReactiveSelectionQuery<R> setParameterList(String name, Collection<? extends P> values, Type<P> type);

	@Override
	ReactiveSelectionQuery<R> setParameterList(String name, Object[] values);

	@Override
	<P> ReactiveSelectionQuery<R> setParameterList(String name, P[] values, Class<P> javaType);

	@Override
	<P> ReactiveSelectionQuery<R> setParameterList(String name, P[] values, Type<P> type);

	@Override
	ReactiveSelectionQuery<R> setParameterList(int position, @SuppressWarnings("rawtypes") Collection values);

	@Override
	<P> ReactiveSelectionQuery<R> setParameterList(int position, Collection<? extends P> values, Class<P> javaType);

	@Override
	<P> ReactiveSelectionQuery<R> setParameterList(int position, Collection<? extends P> values, Type<P> type);

	@Override
	ReactiveSelectionQuery<R> setParameterList(int position, Object[] values);

	@Override
	<P> ReactiveSelectionQuery<R> setParameterList(int position, P[] values, Class<P> javaType);

	@Override
	<P> ReactiveSelectionQuery<R> setParameterList(int position, P[] values, Type<P> type);

	@Override
	<P> ReactiveSelectionQuery<R> setParameterList(QueryParameter<P> parameter, Collection<? extends P> values);

	@Override
	<P> ReactiveSelectionQuery<R> setParameterList(QueryParameter<P> parameter, Collection<? extends P> values, Class<P> javaType);

	@Override
	<P> ReactiveSelectionQuery<R> setParameterList(QueryParameter<P> parameter, Collection<? extends P> values, Type<P> type);

	@Override
	<P> ReactiveSelectionQuery<R> setParameterList(QueryParameter<P> parameter, P[] values);

	@Override
	<P> ReactiveSelectionQuery<R> setParameterList(QueryParameter<P> parameter, P[] values, Class<P> javaType);

	@Override
	<P> ReactiveSelectionQuery<R> setParameterList(QueryParameter<P> parameter, P[] values, Type<P> type);

	@Override
	ReactiveSelectionQuery<R> setQueryFlushMode(QueryFlushMode queryFlushMode);

	@Override
	ReactiveSelectionQuery<R> setQueryPlanCacheable(boolean queryPlanCacheable);


}
