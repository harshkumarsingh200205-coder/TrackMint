package com.trackmint.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilTest {

    @Test
    @DisplayName("Hash password should generate non-empty salted hash")
    void testHashPasswordSuccess() {
        String raw = "mySecurePassword123";
        String hashed = PasswordUtil.hashPassword(raw);

        assertNotNull(hashed);
        assertTrue(hashed.contains(":"), "Hash must contain salt and hash separated by colon");
        assertEquals(2, hashed.split(":").length);
    }

    @Test
    @DisplayName("Two hashes of same password must produce different salts and hashes")
    void testSaltRandomness() {
        String raw = "commonPassword";
        String hash1 = PasswordUtil.hashPassword(raw);
        String hash2 = PasswordUtil.hashPassword(raw);

        assertNotEquals(hash1, hash2, "Salts must be randomly generated and unique");
    }

    @Test
    @DisplayName("Verify valid password matches its generated hash")
    void testVerifyPasswordSuccess() {
        String raw = "correctPassword";
        String hashed = PasswordUtil.hashPassword(raw);

        assertTrue(PasswordUtil.verifyPassword(raw, hashed));
    }

    @Test
    @DisplayName("Verify invalid password returns false")
    void testVerifyPasswordFailure() {
        String raw = "correctPassword";
        String hashed = PasswordUtil.hashPassword(raw);

        assertFalse(PasswordUtil.verifyPassword("wrongPassword", hashed));
    }

    @Test
    @DisplayName("Verify legacy plaintext password returns true (for transparent migration)")
    void testLegacyPlaintextVerification() {
        String legacyStored = "1234";
        assertTrue(PasswordUtil.verifyPassword("1234", legacyStored));
        assertFalse(PasswordUtil.verifyPassword("wrong", legacyStored));
    }

    @Test
    @DisplayName("isHashed should distinguish PBKDF2 hash from plaintext")
    void testIsHashed() {
        String hashed = PasswordUtil.hashPassword("secret");
        assertTrue(PasswordUtil.isHashed(hashed));
        assertFalse(PasswordUtil.isHashed("plaintextPassword"));
        assertFalse(PasswordUtil.isHashed(""));
        assertFalse(PasswordUtil.isHashed(null));
    }
}
