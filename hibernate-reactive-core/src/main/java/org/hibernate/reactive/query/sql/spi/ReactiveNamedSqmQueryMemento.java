/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.reactive.query.sql.spi;

import jakarta.persistence.Timeout;
import java.util.Map;
import java.util.Objects;

import org.hibernate.FlushMode;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.query.named.NamedNativeQueryMemento;
import org.hibernate.query.spi.MutationQueryImplementor;
import org.hibernate.query.spi.QueryEngine;
import org.hibernate.query.spi.QueryImplementor;
import org.hibernate.query.named.NamedSqmQueryMemento;
import org.hibernate.query.spi.SelectionQueryImplementor;
import org.hibernate.query.sqm.tree.SqmStatement;
import org.hibernate.query.sqm.tree.select.SqmSelectStatement;
import org.hibernate.reactive.query.internal.ReactiveQueryImpl;
import org.hibernate.reactive.query.internal.ReactiveSelectionQueryImpl;

/**
 * @see NamedNativeQueryMemento
 */
public class ReactiveNamedSqmQueryMemento<E> implements NamedSqmQueryMemento<E> {

	private final NamedSqmQueryMemento<E> delegate;

	public ReactiveNamedSqmQueryMemento(NamedSqmQueryMemento<E> delegate) {
		Objects.requireNonNull( delegate );
		this.delegate = delegate;
	}


	@Override
	public QueryImplementor<E> toQuery(SharedSessionContractImplementor session) {
		return toQuery( session, null );
	}

	@Override
	public <T> QueryImplementor<T> toQuery(SharedSessionContractImplementor session, Class<T> resultType) {
		// A bit of a hack, I'm sure that if we have a better look at this we can avoid the instanceof
		if ( delegate instanceof NamedHqlQueryMementoImpl ) {
			return new ReactiveQueryImpl<>( (NamedHqlQueryMementoImpl) delegate, resultType, session );
		}
		if ( delegate instanceof NamedCriteriaQueryMementoImpl ) {
			return new ReactiveQueryImpl<>( (NamedCriteriaQueryMementoImpl) delegate, resultType, session );
		}
		else {
			throw new UnsupportedOperationException( "NamedSqmQueryMemento not recognized: " + delegate.getClass() );
		}
	}

	@Override
	public <T> SqmSelectionQuery<T> toSelectionQuery(Class<T> resultType, SharedSessionContractImplementor session) {
		final SqmSelectionQuery<T> selectionQuery = delegate.toSelectionQuery( resultType, session );
		if ( selectionQuery == null ) {
			return null;
		}
		else {
			@SuppressWarnings("unchecked")
			final SqmSelectStatement<T> statement = (SqmSelectStatement<T>) selectionQuery.getSqmStatement();
			return new ReactiveSelectionQueryImpl<>( statement, resultType, session );
		}
	}

	@Override
	public String getHqlString() {
		return delegate.getHqlString();
	}

	@Override
	public SqmStatement<E> getSqmStatement() {
		return delegate.getSqmStatement();
	}

	@Override
	public Map<String, String> getAnticipatedParameterTypes() {
		return delegate.getAnticipatedParameterTypes();
	}

	@Override
	public NamedSqmQueryMemento<E> makeCopy(String name) {
		return new ReactiveNamedSqmQueryMemento<>( delegate.makeCopy( name ) );
	}

	@Override
	public SelectionQueryImplementor<E> toSelectionQuery(SharedSessionContractImplementor session) {
		return null;
	}

	@Override
	public <X> SelectionQueryImplementor<X> toSelectionQuery(
			SharedSessionContractImplementor session,
			Class<X> javaType) {
		return null;
	}

	@Override
	public MutationQueryImplementor<E> toMutationQuery(SharedSessionContractImplementor session) {
		return null;
	}

	@Override
	public <X> MutationQueryImplementor<X> toMutationQuery(
			SharedSessionContractImplementor session,
			Class<X> targetType) {
		return null;
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
}
