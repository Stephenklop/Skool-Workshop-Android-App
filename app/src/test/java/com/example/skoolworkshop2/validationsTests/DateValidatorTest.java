package com.example.skoolworkshop2.validationsTests;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.skoolworkshop2.logic.validation.DateValidation;
import com.example.skoolworkshop2.logic.validation.EmailValidator;
import com.example.skoolworkshop2.logic.validation.LearningLevelValidator;

public class DateValidatorTest {

    @Test
    public void dateValidator_CorrectDate_ReturnsTrue() throws Exception {
        assertTrue(DateValidation.isValidDate("13/12/2020"));
    }

    @Test
    public void dateValidator_CorrectLeapYear_ReturnsTrue() throws Exception {
        assertTrue(DateValidation.isValidDate("29/02/2020"));
    }

    @Test
    public void dateValidator_InvalidLeapYear_ReturnsFalse() throws Exception {
        assertFalse(DateValidation.isValidDate("29/2/2021"));
    }

    @Test
    public void dateValidator_InvalidMonth_ReturnsFalse() throws Exception {
        assertFalse(DateValidation.isValidDate("29/13/2021"));
    }

    @Test
    public void dateValidator_InvalidDay_ReturnsFalse() throws Exception {
        assertFalse(DateValidation.isValidDate("32/12/2021"));
    }

    @Test
    public void dateValidator_Null_ReturnsFalse() throws Exception {
        assertFalse(DateValidation.isValidDate(null));
    }

    @Test
    public void dateValidator_InvalidDayAtSpecificMonth_ReturnsFalse() throws Exception {
        assertFalse(DateValidation.isValidDate("31/04/2021"));
    }

}
