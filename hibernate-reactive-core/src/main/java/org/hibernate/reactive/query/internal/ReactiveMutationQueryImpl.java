package org.hibernate.reactive.query.internal;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.query.internal.MutationQueryImpl;
import org.hibernate.query.named.internal.CriteriaMutationMementoImpl;
import org.hibernate.query.named.internal.HqlMutationMementoImpl;
import org.hibernate.query.spi.HqlInterpretation;
import org.hibernate.query.spi.MutationQueryImplementor;
import org.hibernate.query.sqm.tree.SqmDmlStatement;

public class ReactiveMutationQueryImpl extends MutationQueryImpl {

	public ReactiveMutationQueryImpl(
			String hql,
			HqlInterpretation hqlInterpretation,
			Class targetType,
			SharedSessionContractImplementor session) {
		super( hql, hqlInterpretation, targetType, session );
	}

	public ReactiveMutationQueryImpl(
			HqlMutationMementoImpl memento,
			HqlInterpretation interpretation,
			Class targetType,
			SharedSessionContractImplementor session) {
		super( memento, interpretation, targetType, session );
	}

	public ReactiveMutationQueryImpl(
			CriteriaMutationMementoImpl memento,
			SqmDmlStatement criteria,
			SharedSessionContractImplementor session) {
		super( memento, criteria, session );
	}

	public ReactiveMutationQueryImpl(SqmDmlStatement criteria, SharedSessionContractImplementor session) {
		super( criteria, session );
	}

	public ReactiveMutationQueryImpl(
			SqmDmlStatement criteria,
			boolean copyAst,
			SharedSessionContractImplementor session) {
		super( criteria, copyAst, session );
	}

	@Override
	public ReactiveMutationQueryImpl setConvertedParameter(int position, Object value, Class converter) {
		super.setConvertedParameter( position, value, converter );
		return this
	}

	@Override
	public ReactiveMutationQueryImpl setConvertedParameter(String name, Object value, Class converter) {
		return super.setConvertedParameter( name, value, converter );
	}
}
