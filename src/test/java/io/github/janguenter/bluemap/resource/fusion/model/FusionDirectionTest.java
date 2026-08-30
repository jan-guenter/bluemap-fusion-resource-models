/*
 * SPDX-License-Identifier: MIT
 */
package io.github.janguenter.bluemap.resource.fusion.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FusionDirectionTest {

    private static final List<String> WIRE_NAMES = List.of(
            "top", "top_right", "right", "bottom_right",
            "bottom", "bottom_left", "left", "top_left"
    );

    @Test
    void preservesMaskOrderAndWireNames() {
        FusionDirection[] directions = FusionDirection.values();
        assertEquals(WIRE_NAMES.size(), directions.length);
        for (int index = 0; index < directions.length; index++) {
            FusionDirection direction = directions[index];
            assertEquals(index, direction.bit());
            assertEquals(WIRE_NAMES.get(index), direction.wireName());
            assertEquals(direction, FusionDirection.parse(direction.wireName()));
            assertEquals(direction, FusionDirection.parse(direction.wireName().toUpperCase()));
        }
    }

    @Test
    void preservesInvalidInputFailures() {
        assertThrows(IllegalArgumentException.class, () -> FusionDirection.parse("north"));
        assertThrows(NullPointerException.class, () -> FusionDirection.parse(null));
    }
}
