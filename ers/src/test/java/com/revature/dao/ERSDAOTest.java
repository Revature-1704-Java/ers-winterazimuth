package com.revature.dao;

import static org.junit.Assert.*;

import org.junit.Test;

public class ERSDAOTest {
	
	ERSDAO LabRat = new ERSDAO();

	@Test
	public void testGetNameFromID() {
		assertEquals("Montgomery Scott", LabRat.getNameFromID(4));
	}

	@Test
	public void testEmployeeLogIn() {
		assertTrue(LabRat.employeeLogIn(2, "spockpassword"));
	}

	@Test
	public void testEmployeeViewAll() {
		assertNotNull(LabRat.employeeViewAll(1));
	}

	@Test
	public void testViewAllOfOneStatus() {
		assertNotNull(LabRat.viewAllOfOneStatus(2));
	}

}
