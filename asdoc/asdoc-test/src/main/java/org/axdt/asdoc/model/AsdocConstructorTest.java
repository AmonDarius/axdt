/*******************************************************************************
 * Copyright (c) 2010 Martin Schnabel <mb0@mb0.org>.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.axdt.asdoc.model;

import junit.textui.TestRunner;

import org.axdt.asdoc.AsdocEFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Asdoc Constructor</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class AsdocConstructorTest extends AsdocExecutableTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(AsdocConstructorTest.class);
	}

	/**
	 * Constructs a new Asdoc Constructor test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AsdocConstructorTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Asdoc Constructor test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected AsdocConstructor getFixture() {
		return (AsdocConstructor)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(AsdocEFactory.eINSTANCE.createAsdocConstructor());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //AsdocConstructorTest
