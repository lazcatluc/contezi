package ro.contezi.novote.validation;

import static org.junit.Assert.*;

import org.junit.Test;

import ro.contezi.novote.validator.CnpValidator;

public class CnpChecksumTest {
	@Test
	public void getsChecksum() throws Exception {
		assertEquals(2, CnpValidator.getChecksum(181_022_642_009L));
		assertEquals(8, CnpValidator.getChecksum(293_011_503_334L));
	}
	
	@Test
	public void validatesChecksum() throws Exception {
		assertTrue(CnpValidator.validateCnp("1810226420092"));
		assertTrue(CnpValidator.validateCnp("2930115033348"));
	}
}
