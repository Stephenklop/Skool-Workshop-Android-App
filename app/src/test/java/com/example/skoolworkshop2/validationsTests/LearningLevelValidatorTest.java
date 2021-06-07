package com.example.skoolworkshop2.validationsTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.skoolworkshop2.logic.validation.LearningLevelValidator;

import org.junit.Test;

public class LearningLevelValidatorTest {

    @Test
    public void learningLevelValidator_Correct_ReturnsTrue() {
        assertTrue(LearningLevelValidator.isValidLearningLevels("Kader"));
    }

    @Test
    public void learningLevelValidator_EmptyString_ReturnsFalse() {
        assertTrue(LearningLevelValidator.isValidLearningLevels(""));
    }

    @Test
    public void learningLevelValidator_NullEmail_ReturnsFalse() {
        assertFalse(LearningLevelValidator.isValidLearningLevels(null));
    }
}
