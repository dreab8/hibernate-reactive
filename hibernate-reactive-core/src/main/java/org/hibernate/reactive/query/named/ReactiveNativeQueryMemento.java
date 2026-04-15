/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.reactive.query.named;

import jakarta.persistence.Timeout;
import java.util.Map;
import java.util.Set;

import org.hibernate.FlushMode;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.query.IllegalMutationQueryException;
import org.hibernate.query.IllegalSelectQueryException;
import org.hibernate.query.named.internal.NativeMutationMementoImpl;
import org.hibernate.query.named.internal.NativeSelectionMementoImpl;
import org.hibernate.query.spi.MutationQueryImplementor;
import org.hibernate.query.spi.QueryEngine;
import org.hibernate.query.named.NamedNativeQueryMemento;
import org.hibernate.query.sql.spi.NativeQueryImplementor;
import org.hibernate.reactive.query.sql.internal.ReactiveNativeQueryImpl;

/**
 * @see NamedNativeQueryMemento
 */
public class ReactiveNativeQueryMemento<E> implements NamedNativeQueryMemento<E> {

	private final NamedNativeQueryMemento<E> delegate;

	public ReactiveNativeQueryMemento(NamedNativeQueryMemento<E> delegate) {
		this.delegate = delegate;
	}

	@Override
	public String getSqlString() {
		return delegate.getSqlString();
	}

	@Override
	public String getOriginalSqlString() {
		return delegate.getOriginalSqlString();
	}

	@Override
	public Set<String> getQuerySpaces() {
		return delegate.getQuerySpaces();
	}

	@Override
	public String getResultMappingName() {
		return delegate.getResultMappingName();
	}

	@Override
	public Integer getFirstResult() {
		return delegate.getFirstResult();
	}

	@Override
	public Integer getMaxResults() {
		return delegate.getMaxResults();
	}

	@Override
	public NativeQueryImplementor<E> toSelectionQuery(SharedSessionContractImplementor session) {
		return toSelectionQuery( session, null );
	}

	@Override
	public <X> NativeQueryImplementor<X> toSelectionQuery(SharedSessionContractImplementor session, Class<X> javaType) {
		if ( delegate instanceof NativeSelectionMementoImpl nativeSelectionMemento ) {
			return new ReactiveNativeQueryImpl<>( nativeSelectionMemento, javaType, null, session );
		}
		throw new IllegalSelectQueryException( "Not a NamedSelectionQuery" );

	}

	@Override
	public MutationQueryImplementor<E> toMutationQuery(SharedSessionContractImplementor session) {
		if( delegate instanceof NativeMutationMementoImpl nativeMutationMemento ) {
			return new ReactiveNativeQueryImpl<>( nativeMutationMemento, session );
		}
		throw new IllegalMutationQueryException( "Not a NamedMutationMemento" );
	}

	@Override
	public <X> MutationQueryImplementor<X> toMutationQuery(
			SharedSessionContractImplementor session,
			Class<X> targetType) {
		if( delegate instanceof NativeMutationMementoImpl nativeMutationMemento ) {
			return new ReactiveNativeQueryImpl<>( nativeMutationMemento, session );
		}
		throw new IllegalMutationQueryException( "Not a NamedMutationMemento" );
	}

	@Override
	public NativeQueryImplementor<E> toQuery(SharedSessionContractImplementor session) {
		return toSelectionQuery( session );
	}

	@Override
	public <T> NativeQueryImplementor<T> toQuery(SharedSessionContractImplementor session, String resultSetMapping) {
		//noinspection unchecked,rawtypes
		if( delegate instanceof NativeSelectionMementoImpl nativeSelectionMemento ) {
			return new ReactiveNativeQueryImpl( nativeSelectionMemento, null, resultSetMapping, session );
		}
		throw new IllegalSelectQueryException( "Not a NamedSelectionMemento", delegate.getSqlString() );
	}

	@Override
	public NamedNativeQueryMemento<E> makeCopy(String name) {
		return new ReactiveNativeQueryMemento( delegate.makeCopy( name ));
	}

	@Override
	public <X> NativeQueryImplementor<X> toQuery(SharedSessionContractImplementor session, Class<X> javaType) {
		return toSelectionQuery( session, javaType );
	}

	@Override
	public String getRegistrationName() {
		return delegate.getRegistrationName();
	}

	@Override
	public FlushMode getFlushMode() {
		return delegate.getFlushMode();
	}

	@Override
	public String getName() {
		return delegate.getName();
	}

	@Override
	public Timeout getTimeout() {
		return delegate.getTimeout();
	}

	@Override
	public String getComment() {
		return delegate.getComment();
	}

	@Override
	public Map<String, Object> getHints() {
		return delegate.getHints();
	}

	@Override
	public void validate(QueryEngine queryEngine) {
		delegate.validate( queryEngine );
	}

	public NamedNativeQueryMemento<E> getDelegate() {
		return delegate;
	}
}
