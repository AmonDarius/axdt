/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.axdt.as3.model.impl;

import org.axdt.as3.As3EPackage;
import org.axdt.as3.model.As3RelationalExpression;
import org.axdt.avm.AvmEFactory;
import org.axdt.avm.model.AvmType;
import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>As3 Relational Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class As3RelationalExpressionImpl extends As3BinaryExpressionImpl implements As3RelationalExpression {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected As3RelationalExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return As3EPackage.Literals.AS3_RELATIONAL_EXPRESSION;
	}
	
	@Override
	public AvmType resolveType() {
		// TODO resolve type from right class ref
		if ("as".equals(getOperation()))
			return AvmEFactory.eINSTANCE.createAvmGeneric();
		return getClassProxy("Boolean");
	}
} //As3RelationalExpressionImpl
