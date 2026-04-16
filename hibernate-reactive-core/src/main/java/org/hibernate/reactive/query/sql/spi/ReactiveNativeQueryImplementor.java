/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.reactive.query.sql.spi;

import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
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
import org.hibernate.MappingException;
import org.hibernate.ScrollMode;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.metamodel.model.domain.BasicDomainType;
import org.hibernate.query.Page;
import org.hibernate.query.QueryFlushMode;
import org.hibernate.query.QueryParameter;
import org.hibernate.query.ResultListTransformer;
import org.hibernate.query.TupleTransformer;
import org.hibernate.query.named.NameableQuery;
import org.hibernate.query.named.NamedMutationMemento;
import org.hibernate.query.named.internal.NativeSelectionMementoImpl;
import org.hibernate.query.results.internal.dynamic.DynamicResultBuilderEntityStandard;
import org.hibernate.query.named.NamedNativeQueryMemento;
import org.hibernate.query.spi.ScrollableResultsImplementor;
import org.hibernate.reactive.query.ReactiveNativeQuery;
import org.hibernate.reactive.query.spi.ReactiveSelectionQueryImplementor;
import org.hibernate.reactive.query.spi.ReativeMutationQueryImplementor;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Parameter;
import jakarta.persistence.TemporalType;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public interface ReactiveNativeQueryImplementor<R> extends ReactiveSelectionQueryImplementor<R>,
		ReativeMutationQueryImplementor<R>, ReactiveNativeQuery<R>, NameableQuery {

	/**
	 * Best guess whether this is a select query.  {@code null}
	 * indicates unknown
	 */
	Boolean isSelectQuery();

	@Override
	NamedNativeQueryMemento toMemento(String name);

	@Override
	NamedMutationMemento<?> toMutationMemento(String name);

	@Override
	default ReactiveNativeQueryImplementor<R> asMutationQuery() {
		return (ReactiveNativeQueryImplementor<R>) ReativeMutationQueryImplementor.super.asMutationQuery();
	}

	@Override
	default ReactiveNativeQueryImplementor<R> asStatement() {
		return (ReactiveNativeQueryImplementor<R>) ReativeMutationQueryImplementor.super.asStatement();
	}

	@Override
	ReactiveNativeQueryImplementor<R> asSelectionQuery();

	@Override
	<X> ReactiveNativeQueryImplementor<X> asSelectionQuery(Class<X> type);

	@Override
	<X> ReactiveNativeQueryImplementor<X> asSelectionQuery(EntityGraph<X> entityGraph);

	@Override
	<X> ReactiveNativeQueryImplementor<X> ofType(Class<X> type);

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Executions

	@Override
	List<R> list();

	@Override
	default List<R> getResultList() {
		return ReactiveSelectionQueryImplementor.super.getResultList();
	}

	@Override
	ScrollableResultsImplementor<R> scroll();

	@Override
	ScrollableResultsImplementor<R> scroll(ScrollMode scrollMode);

	@Override
	default Stream<R> getResultStream() {
		return ReactiveSelectionQueryImplementor.super.getResultStream();
	}

	@Override
	default Stream<R> stream() {
		return ReactiveSelectionQueryImplementor.super.stream();
	}

	@Override
	R uniqueResult();

	@Override
	Optional<R> uniqueResultOptional();

	@Override
	R getSingleResult();

	@Override
	R getSingleResultOrNull();

	@Override
	int execute();

	@Override
	int executeUpdate();

	@Override
	CompletionStage<Integer> executeReactiveUpdate();

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// covariant overrides - NativeQuery

	@Override
	NativeSelectionMementoImpl<R> toSelectionMemento(String name);

	@Override
	ReactiveNativeQueryImplementor<R> addSynchronizedQuerySpace(String querySpace);

	@Override
	ReactiveNativeQueryImplementor<R> addSynchronizedEntityName(String entityName) throws MappingException;

	@Override
	ReactiveNativeQueryImplementor<R> addSynchronizedEntityClass(@SuppressWarnings("rawtypes") Class entityClass) throws MappingException;

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// covariant overrides - Query / QueryImplementor

	@Override
	CompletionStage<R> getReactiveSingleResult();

	@Override
	CompletionStage<R> getReactiveSingleResultOrNull();

	@Override
	ReactiveNativeQueryImplementor<R> setHint(String hintName, Object value);

	@Override
	ReactiveNativeQueryImplementor<R> setTimeout(Integer timeout);

	@Override
	ReactiveNativeQueryImplementor<R> setTimeout(Timeout timeout);

	@Override
	ReactiveNativeQueryImplementor<R> setTimeout(int timeout);

	@Override
	ReactiveNativeQueryImplementor<R> setQueryFlushMode(QueryFlushMode queryFlushMode);

	@Override
	ReactiveNativeQueryImplementor<R> setFlushMode(FlushModeType flushMode);

	@Override
	ReactiveNativeQueryImplementor<R> setQueryPlanCacheable(boolean queryPlanCacheable);

	@Override
	ReactiveNativeQueryImplementor<R> setCacheMode(CacheMode cacheMode);

	@Override
	ReactiveNativeQueryImplementor<R> setCacheable(boolean cacheable);

	@Override
	CacheMode getCacheMode();

	@Override
	CacheStoreMode getCacheStoreMode();

	@Override
	CacheRetrieveMode getCacheRetrieveMode();

	@Override
	ReactiveNativeQueryImplementor<R> setCacheRegion(String cacheRegion);

	@Override
	ReactiveNativeQueryImplementor<R> setFetchSize(int fetchSize);

	@Override
	ReactiveNativeQueryImplementor<R> setReadOnly(boolean readOnly);

	@Override
	LockMode getHibernateLockMode();

	@Override
	ReactiveNativeQueryImplementor<R> setHibernateLockMode(LockMode lockMode);

	@Override
	LockModeType getLockMode();

	@Override
	ReactiveNativeQueryImplementor<R> setLockMode(LockModeType lockMode);

	@Override
	ReactiveNativeQueryImplementor<R> setComment(String comment);

	@Override
	int getMaxResults();

	@Override
	ReactiveNativeQueryImplementor<R> setMaxResults(int maxResult);

	@Override
	int getFirstResult();

	@Override
	ReactiveNativeQueryImplementor<R> setFirstResult(int startPosition);

	@Override
	ReactiveNativeQueryImplementor<R> addQueryHint(String hint);

	@Override
	<T> ReactiveNativeQueryImplementor<T> setTupleTransformer(TupleTransformer<T> transformer);

	@Override
	ReactiveNativeQueryImplementor<R> setResultListTransformer(ResultListTransformer<R> transformer);

	@Override
	<X> ReactiveNativeQueryImplementor<X> withEntityGraph(EntityGraph<X> entityGraph);

	@Override
	ReactiveNativeQueryImplementor<R> setEntityGraph(EntityGraph<? super R> entityGraph);

	@Override
	ReactiveNativeQueryImplementor<R> setEntityGraph(EntityGraph<? super R> graph, GraphSemantic semantic);

	@Override
	ReactiveNativeQueryImplementor<R> disableFetchProfile(String profileName);

	@Override
	ReactiveNativeQueryImplementor<R> enableFetchProfile(String profileName);

	@Override
	ReactiveNativeQueryImplementor<R> setPage(Page page);

	@Override
	ReactiveNativeQueryImplementor<R> setCacheStoreMode(CacheStoreMode cacheStoreMode);

	@Override
	ReactiveNativeQueryImplementor<R> setCacheRetrieveMode(CacheRetrieveMode cacheRetrieveMode);

	@Override
	ReactiveNativeQueryImplementor<R> setFollowOnStrategy(Locking.FollowOn followOnStrategy);

	@Override
	ReactiveNativeQueryImplementor<R> setLockScope(PessimisticLockScope lockScope);

	@Override
	ReactiveNativeQueryImplementor<R> setParameter(String name, Object val);

	@Override
	<P> ReactiveNativeQueryImplementor<R> setParameter(String name, P val, Type<P> type);

	@Override
	<P> ReactiveNativeQueryImplementor<R> setParameter(String name, P val, Class<P> type);

	@Override
	ReactiveNativeQueryImplementor<R> setParameter(String name, Instant value, TemporalType temporalType);

	@Override
	ReactiveNativeQueryImplementor<R> setParameter(String name, Date value, TemporalType temporalType);

	@Override
	ReactiveNativeQueryImplementor<R> setParameter(String name, Calendar value, TemporalType temporalType);

	@Override
	ReactiveNativeQueryImplementor<R> setParameter(int position, Object val);

	@Override
	<P> ReactiveNativeQueryImplementor<R> setParameter(int position, P val, Class<P> type);

	@Override
	<P> ReactiveNativeQueryImplementor<R> setParameter(int position, P val, Type<P> type);

	@Override
	ReactiveNativeQueryImplementor<R> setParameter(int position, Instant value, TemporalType temporalType);

	@Override
	ReactiveNativeQueryImplementor<R> setParameter(int position, Date value, TemporalType temporalType);

	@Override
	ReactiveNativeQueryImplementor<R> setParameter(int position, Calendar value, TemporalType temporalType);

	@Override
	<P> ReactiveNativeQueryImplementor<R> setParameter(QueryParameter<P> parameter, P val);

	@Override
	<P> ReactiveNativeQueryImplementor<R> setParameter(QueryParameter<P> parameter, P val, Class<P> type);

	@Override
	<P> ReactiveNativeQueryImplementor<R> setParameter(QueryParameter<P> parameter, P val, Type<P> type);

	@Override
	<P> ReactiveNativeQueryImplementor<R> setParameter(Parameter<P> param, P value);

	@Override
	ReactiveNativeQueryImplementor<R> setParameter(Parameter<Date> param, Date value, TemporalType temporalType);

	@Override
	ReactiveNativeQueryImplementor<R> setParameter(Parameter<Calendar> param, Calendar value, TemporalType temporalType);

	@Override
	ReactiveNativeQueryImplementor<R> setParameterList(String name, @SuppressWarnings("rawtypes") Collection values);

	@Override
	<P> ReactiveNativeQueryImplementor<R> setParameterList(String name, Collection<? extends P> values, Class<P> type);

	@Override
	<P> ReactiveNativeQueryImplementor<R> setParameterList(String name, Collection<? extends P> values, Type<P> type);

	@Override
	ReactiveNativeQueryImplementor<R> setParameterList(String name, Object[] values);

	@Override
	<P> ReactiveNativeQueryImplementor<R> setParameterList(String name, P[] values, Class<P> type);

	@Override
	<P> ReactiveNativeQueryImplementor<R> setParameterList(String name, P[] values, Type<P> type);

	@Override
	ReactiveNativeQueryImplementor<R> setParameterList(int position, @SuppressWarnings("rawtypes") Collection values);

	@Override
	<P> ReactiveNativeQueryImplementor<R> setParameterList(int position, Collection<? extends P> values, Class<P> type);

	@Override
	<P> ReactiveNativeQueryImplementor<R> setParameterList(int position, Collection<? extends P> values, Type<P> type);

	@Override
	ReactiveNativeQueryImplementor<R> setParameterList(int position, Object[] values);

	@Override
	<P> ReactiveNativeQueryImplementor<R> setParameterList(int position, P[] values, Class<P> javaType);

	@Override
	<P> ReactiveNativeQueryImplementor<R> setParameterList(int position, P[] values, Type<P> type);

	@Override
	<P> ReactiveNativeQueryImplementor<R> setParameterList(QueryParameter<P> parameter, Collection<? extends P> values);

	@Override
	<P> ReactiveNativeQueryImplementor<R> setParameterList(QueryParameter<P> parameter, Collection<? extends P> values, Class<P> javaType);

	@Override
	<P> ReactiveNativeQueryImplementor<R> setParameterList(QueryParameter<P> parameter, Collection<? extends P> values, Type<P> type);

	@Override
	<P> ReactiveNativeQueryImplementor<R> setParameterList(QueryParameter<P> parameter, P[] values);

	@Override
	<P> ReactiveNativeQueryImplementor<R> setParameterList(QueryParameter<P> parameter, P[] values, Class<P> javaType);

	@Override
	<P> ReactiveNativeQueryImplementor<R> setParameterList(QueryParameter<P> parameter, P[] values, Type<P> type);

	@Override
	ReactiveNativeQueryImplementor<R> setProperties(Object bean);

	@Override
	ReactiveNativeQueryImplementor<R> setProperties(@SuppressWarnings("rawtypes") Map bean);

	@Override
	ReactiveNativeQueryImplementor<R> addScalar(String columnAlias);

	@Override
	ReactiveNativeQueryImplementor<R> addScalar(String columnAlias, BasicDomainType type);

	@Override
	ReactiveNativeQueryImplementor<R> addScalar(String columnAlias, @SuppressWarnings("rawtypes") Class javaType);

	@Override
	ReactiveNativeQueryImplementor<R> addScalar(int position, Class<?> type);

	@Override
	<C> ReactiveNativeQueryImplementor<R> addScalar(String columnAlias, Class<C> relationalJavaType, AttributeConverter<?,C> converter);

	@Override
	<O, J> ReactiveNativeQueryImplementor<R> addScalar(String columnAlias, Class<O> domainJavaType, Class<J> jdbcJavaType, AttributeConverter<O, J> converter);

	@Override
	<C> ReactiveNativeQueryImplementor<R> addScalar(String columnAlias, Class<C> relationalJavaType, Class<? extends AttributeConverter<?,C>> converter);

	@Override
	<O, J> ReactiveNativeQueryImplementor<R> addScalar(String columnAlias, Class<O> domainJavaType, Class<J> jdbcJavaType, Class<? extends AttributeConverter<O, J>> converter);

	@Override
	ReactiveNativeQueryImplementor<R> addAttributeResult(String columnAlias, @SuppressWarnings("rawtypes") Class entityJavaType, String attributePath);

	@Override
	ReactiveNativeQueryImplementor<R> addAttributeResult(String columnAlias, String entityName, String attributePath);

	@Override
	ReactiveNativeQueryImplementor<R> addAttributeResult(String columnAlias, @SuppressWarnings("rawtypes") SingularAttribute attribute);

	@Override
	DynamicResultBuilderEntityStandard addRoot(String tableAlias, String entityName);

	@Override
	ReactiveNativeQueryImplementor<R> addEntity(String entityName);

	@Override
	ReactiveNativeQueryImplementor<R> addEntity(String tableAlias, String entityName);

	@Override
	ReactiveNativeQueryImplementor<R> addEntity(String tableAlias, String entityName, LockMode lockMode);

	@Override
	ReactiveNativeQueryImplementor<R> addEntity(@SuppressWarnings("rawtypes") Class entityType);

	@Override
	ReactiveNativeQueryImplementor<R> addEntity(String tableAlias, @SuppressWarnings("rawtypes") Class entityType);

	@Override
	ReactiveNativeQueryImplementor<R> addEntity(String tableAlias, @SuppressWarnings("rawtypes") Class entityClass, LockMode lockMode);

	@Override
	ReactiveNativeQueryImplementor<R> addJoin(String tableAlias, String path);

	@Override
	ReactiveNativeQueryImplementor<R> addJoin(
			String tableAlias,
			String ownerTableAlias,
			String joinPropertyName);

	@Override
	ReactiveNativeQueryImplementor<R> addJoin(String tableAlias, String path, LockMode lockMode);

	@Override
	<P> ReactiveNativeQueryImplementor<R> setConvertedParameter(String name, P value, Class<? extends AttributeConverter<P, ?>> converter);

	@Override
	<P> ReactiveNativeQueryImplementor<R> setConvertedParameter(int position, P value, Class<? extends AttributeConverter<P, ?>> converter);

}
