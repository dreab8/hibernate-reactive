/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.reactive.query.spi;

import org.hibernate.CacheMode;
import org.hibernate.LockMode;
import org.hibernate.Locking;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.query.IllegalMutationQueryException;
import org.hibernate.query.Page;
import org.hibernate.query.QueryFlushMode;
import org.hibernate.query.QueryParameter;
import org.hibernate.query.ResultListTransformer;
import org.hibernate.query.TupleTransformer;
import org.hibernate.query.named.TypedQueryReferenceProducer;
import org.hibernate.reactive.query.ReactiveQueryImplementor;
import org.hibernate.reactive.query.ReactiveSelectionQuery;

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
import java.util.Map;

public interface ReactiveSelectionQueryImplementor<R> extends ReactiveSelectionQuery<R>, ReactiveQueryImplementor<R>,
		TypedQueryReferenceProducer {

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Options

	@Override
	ReactiveSelectionQueryImplementor<R> setEntityGraph(EntityGraph<? super R> entityGraph);

	@Override
	ReactiveSelectionQueryImplementor<R> setEntityGraph(EntityGraph<? super R> graph, GraphSemantic semantic);

	@Override
	ReactiveSelectionQueryImplementor<R> enableFetchProfile(String profileName);

	ReactiveSelectionQueryImplementor<R> disableFetchProfile(String profileName);

	@Override
	ReactiveSelectionQueryImplementor<R> setFlushMode(FlushModeType flushMode);

	@Override
	ReactiveSelectionQueryImplementor<R> setQueryFlushMode(QueryFlushMode queryFlushMode);

	@Override
	ReactiveSelectionQueryImplementor<R> setTimeout(int timeout);

	@Override
	ReactiveSelectionQueryImplementor<R> setTimeout(Integer timeout);

	@Override
	ReactiveSelectionQueryImplementor<R> setTimeout(Timeout timeout);

	@Override
	ReactiveSelectionQueryImplementor<R> setComment(String comment);

	@Override
	ReactiveSelectionQueryImplementor<R> setFetchSize(int fetchSize);

	@Override
	ReactiveSelectionQueryImplementor<R> setReadOnly(boolean readOnly);

	@Override
	ReactiveSelectionQueryImplementor<R> setMaxResults(int maxResults);

	@Override
	ReactiveSelectionQueryImplementor<R> setFirstResult(int startPosition);

	@Override
	ReactiveSelectionQueryImplementor<R> setPage(Page page);

	@Override
	ReactiveSelectionQueryImplementor<R> setCacheMode(CacheMode cacheMode);

	@Override
	ReactiveSelectionQueryImplementor<R> setCacheStoreMode(CacheStoreMode cacheStoreMode);

	@Override
	ReactiveSelectionQueryImplementor<R> setCacheRetrieveMode(CacheRetrieveMode cacheRetrieveMode);

	@Override
	ReactiveSelectionQueryImplementor<R> setCacheable(boolean cacheable);

	@Override
	ReactiveSelectionQueryImplementor<R> setQueryPlanCacheable(boolean queryPlanCacheable);

	@Override
	ReactiveSelectionQueryImplementor<R> setCacheRegion(String cacheRegion);

	@Override
	ReactiveSelectionQueryImplementor<R> setLockMode(LockModeType lockMode);

	@Override
	ReactiveSelectionQueryImplementor<R> setLockTimeout(Timeout lockTimeout);

	@Override
	ReactiveSelectionQueryImplementor<R> setHibernateLockMode(LockMode lockMode);

	@Override
	ReactiveSelectionQueryImplementor<R> setFollowOnLockingStrategy(Locking.FollowOn followOnStrategy);

	@Override
	ReactiveSelectionQueryImplementor<R> setFollowOnStrategy(Locking.FollowOn followOnStrategy);

	@Override
	<T> ReactiveSelectionQueryImplementor<T> setTupleTransformer(TupleTransformer<T> transformer);

	@Override
	ReactiveSelectionQueryImplementor<R> setResultListTransformer(ResultListTransformer<R> transformer);

	@Override
	ReactiveSelectionQueryImplementor<R> setLockScope(PessimisticLockScope lockScope);

	@Override
	ReactiveSelectionQueryImplementor<R> addQueryHint(String hint);

	@Override
	ReactiveSelectionQueryImplementor<R> setHint(String hintName, Object value);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Parameter Handling

	@Override
	ReactiveSelectionQueryImplementor<R> setParameter(String name, Object value);

	@Override
	<P> ReactiveSelectionQueryImplementor<R> setParameter(String name, P value, Class<P> type);

	@Override
	<P> ReactiveSelectionQueryImplementor<R> setParameter(String name, P value, Type<P> type);

	@Override
	ReactiveSelectionQueryImplementor<R> setParameter(int position, Object value);

	@Override
	<P> ReactiveSelectionQueryImplementor<R> setParameter(int position, P value, Class<P> type);

	@Override
	<P> ReactiveSelectionQueryImplementor<R> setParameter(int position, P value, Type<P> type);

	@Override
	<T> ReactiveSelectionQueryImplementor<R> setParameter(QueryParameter<T> parameter, T value);

	@Override
	<P> ReactiveSelectionQueryImplementor<R> setParameter(QueryParameter<P> parameter, P value, Class<P> type);

	@Override
	<P> ReactiveSelectionQueryImplementor<R> setParameter(QueryParameter<P> parameter, P val, Type<P> type);

	@Override
	<T> ReactiveSelectionQueryImplementor<R> setParameter(Parameter<T> param, T value);

	@Override
	ReactiveSelectionQueryImplementor<R> setProperties(Object bean);

	@Override
	ReactiveSelectionQueryImplementor<R> setProperties(Map bean);

	@Override
	<P> ReactiveSelectionQueryImplementor<R> setConvertedParameter(String name, P value, Class<? extends AttributeConverter<P, ?>> converter);

	@Override
	<P> ReactiveSelectionQueryImplementor<R> setConvertedParameter(int position, P value, Class<? extends AttributeConverter<P, ?>> converter);

	@Override
	ReactiveSelectionQueryImplementor<R> setParameterList(String name, Collection values);

	@Override
	<P> ReactiveSelectionQueryImplementor<R> setParameterList(String name, Collection<? extends P> values, Class<P> javaType);

	@Override
	<P> ReactiveSelectionQueryImplementor<R> setParameterList(String name, Collection<? extends P> values, Type<P> type);

	@Override
	ReactiveSelectionQueryImplementor<R> setParameterList(String name, Object[] values);

	@Override
	<P> ReactiveSelectionQueryImplementor<R> setParameterList(String name, P[] values, Class<P> javaType);

	@Override
	<P> ReactiveSelectionQueryImplementor<R> setParameterList(String name, P[] values, Type<P> type);

	@Override
	ReactiveSelectionQueryImplementor<R> setParameterList(int position, Collection values);

	@Override
	<P> ReactiveSelectionQueryImplementor<R> setParameterList(int position, Collection<? extends P> values, Class<P> javaType);

	@Override
	<P> ReactiveSelectionQueryImplementor<R> setParameterList(int position, Collection<? extends P> values, Type<P> type);

	@Override
	ReactiveSelectionQueryImplementor<R> setParameterList(int position, Object[] values);

	@Override
	<P> ReactiveSelectionQueryImplementor<R> setParameterList(int position, P[] values, Class<P> javaType);

	@Override
	<P> ReactiveSelectionQueryImplementor<R> setParameterList(int position, P[] values, Type<P> type);

	@Override
	<P> ReactiveSelectionQueryImplementor<R> setParameterList(QueryParameter<P> parameter, Collection<? extends P> values);

	@Override
	<P> ReactiveSelectionQueryImplementor<R> setParameterList(QueryParameter<P> parameter, Collection<? extends P> values, Class<P> javaType);

	@Override
	<P> ReactiveSelectionQueryImplementor<R> setParameterList(QueryParameter<P> parameter, Collection<? extends P> values, Type<P> type);

	@Override
	<P> ReactiveSelectionQueryImplementor<R> setParameterList(QueryParameter<P> parameter, P[] values);

	@Override
	<P> ReactiveSelectionQueryImplementor<R> setParameterList(QueryParameter<P> parameter, P[] values, Class<P> javaType);

	@Override
	<P> ReactiveSelectionQueryImplementor<R> setParameterList(QueryParameter<P> parameter, P[] values, Type<P> type);

	@Override
	ReactiveSelectionQueryImplementor<R> setParameter(String name, Instant value, TemporalType temporalType);

	@Override
	ReactiveSelectionQueryImplementor<R> setParameter(String name, Calendar value, TemporalType temporalType);

	@Override
	ReactiveSelectionQueryImplementor<R> setParameter(String name, Date value, TemporalType temporalType);

	@Override
	ReactiveSelectionQueryImplementor<R> setParameter(int position, Instant value, TemporalType temporalType);

	@Override
	ReactiveSelectionQueryImplementor<R> setParameter(int position, Date value, TemporalType temporalType);

	@Override
	ReactiveSelectionQueryImplementor<R> setParameter(int position, Calendar value, TemporalType temporalType);

	@Override
	ReactiveSelectionQueryImplementor<R> setParameter(Parameter<Calendar> param, Calendar value, TemporalType temporalType);

	@Override
	ReactiveSelectionQueryImplementor<R> setParameter(Parameter<Date> param, Date value, TemporalType temporalType);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// MutationQuery Handling

	@Override
	default ReativeMutationQueryImplementor<R> asMutationQuery() {
		throw new IllegalMutationQueryException( "SelectionQuery cannot be treated as a MutationQuery", getQueryString() );
	}

	@Override
	default ReativeMutationQueryImplementor<R> asStatement() {
		throw new IllegalStateException( "SelectionQuery cannot be treated as a MutationQuery - " + getQueryString() );
	}

	@Override
	default int executeUpdate() {
		// per JPA, again, needs to be IllegalStateException
		throw new IllegalStateException( "SelectionQuery cannot be treated as a MutationQuery - " + getQueryString() );
	}
}
