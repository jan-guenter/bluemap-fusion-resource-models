/*
 * SPDX-License-Identifier: MIT
 */
package io.github.janguenter.bluemap.resource.fusion.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AxisVectorTest {

    @Test
    void preservesExactVectorArithmetic() {
        AxisVector up = new AxisVector(0, 1, 0);
        AxisVector right = new AxisVector(1, 0, 0);
        assertEquals(new AxisVector(1, 1, 0), up.add(right));
        assertEquals(new AxisVector(0, -1, 0), up.negate());
        assertEquals(new AxisVector(-1, 1, 0), up.subtract(right));
    }

    @Test
    void rejectsComponentsOutsideTheOriginRange() {
        assertThrows(IllegalArgumentException.class, () -> new AxisVector(3, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new AxisVector(1, 1, 1));
    }
}
