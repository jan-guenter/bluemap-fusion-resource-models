/*
 * SPDX-License-Identifier: MIT
 */
package io.github.janguenter.bluemap.resource.fusion.model;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FusionTextureLayoutTest {

    @Test
    void exposesOnlyTheRechiseledSupersetNames() {
        assertEquals(
                List.of("PLAIN", "PIECED", "FULL", "HORIZONTAL", "VERTICAL", "SIMPLE"),
                Arrays.stream(FusionTextureLayout.values()).map(Enum::name).toList()
        );
        Set<String> publicMethods = Arrays.stream(FusionTextureLayout.class.getDeclaredMethods())
                .filter(method -> Modifier.isPublic(method.getModifiers()))
                .map(method -> method.getName())
                .collect(Collectors.toSet());
        assertEquals(Set.of("values", "valueOf"), publicMethods);
    }
}
