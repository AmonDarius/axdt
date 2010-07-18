/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.axdt.as3.model;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>As3 Unary Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link org.axdt.as3.model.IExpression#resolveType() <em>Resolve Type</em>}</li>
 * </ul>
 * </p>
 */
public abstract class As3UnaryExpressionTest extends IExpressionTest {

	/**
	 * The fixture for this As3 Unary Expression test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected As3UnaryExpression fixture = null;

	/**
	 * Constructs a new As3 Unary Expression test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public As3UnaryExpressionTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this As3 Unary Expression test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(As3UnaryExpression fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this As3 Unary Expression test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected As3UnaryExpression getFixture() {
		return fixture;
	}

	/**
	 * Tests the '{@link org.axdt.as3.model.IExpression#resolveType() <em>Resolve Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.axdt.as3.model.IExpression#resolveType()
	 */
	public abstract void testResolveType();

} //As3UnaryExpressionTest
