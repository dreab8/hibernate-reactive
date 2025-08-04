/* Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.reactive.query.sqm.mutation.internal.cte;

import java.util.List;
import java.util.Map;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.util.MutableObject;
import org.hibernate.query.spi.DomainQueryExecutionContext;
import org.hibernate.query.sqm.internal.DomainParameterXref;
import org.hibernate.query.sqm.mutation.internal.MultiTableSqmMutationConverter;
import org.hibernate.query.sqm.mutation.internal.cte.CteDeleteHandler;
import org.hibernate.query.sqm.mutation.internal.cte.CteMutationStrategy;
import org.hibernate.query.sqm.tree.delete.SqmDeleteStatement;
import org.hibernate.query.sqm.tree.expression.SqmParameter;
import org.hibernate.sql.ast.tree.cte.CteContainer;
import org.hibernate.sql.ast.tree.cte.CteStatement;
import org.hibernate.sql.ast.tree.cte.CteTable;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.hibernate.sql.exec.spi.JdbcOperationQuerySelect;
import org.hibernate.sql.exec.spi.JdbcParameterBindings;

/**
 * @see org.hibernate.query.sqm.mutation.internal.cte.CteDeleteHandler
 */
public class ReactiveCteDeleteHandler extends CteDeleteHandler implements ReactiveAbstractCteMutationHandler {

	protected ReactiveCteDeleteHandler(
			CteTable cteTable,
			SqmDeleteStatement<?> sqmDeleteStatement,
			DomainParameterXref domainParameterXref,
			CteMutationStrategy strategy,
			SessionFactoryImplementor sessionFactory,
			DomainQueryExecutionContext context,
			MutableObject<JdbcParameterBindings> firstJdbcParameterBindingsConsumer) {
		super( cteTable, sqmDeleteStatement, domainParameterXref, strategy, sessionFactory, context, firstJdbcParameterBindingsConsumer );
	}

	@Override
	public void addDmlCtes(
			CteContainer statement,
			CteStatement idSelectCte,
			MultiTableSqmMutationConverter sqmConverter,
			Map<SqmParameter<?>, List<JdbcParameter>> parameterResolutions,
			SessionFactoryImplementor factory) {
		super.addDmlCtes( statement, idSelectCte, sqmConverter, parameterResolutions, factory );
	}

	@Override
	public Expression createCountStar(SessionFactoryImplementor factory, MultiTableSqmMutationConverter sqmConverter) {
		return super.createCountStar( factory, sqmConverter );
	}

	@Override
	public JdbcOperationQuerySelect getSelect() {
		return super.getSelect();
	}

}
