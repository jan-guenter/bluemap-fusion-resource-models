/*
 * SPDX-License-Identifier: MIT
 */
package io.github.janguenter.bluemap.resource.fusion.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.function.IntSupplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FusionTextureSelectorDifferentialTest {

    private static final Map<String, List<String>> CONSUMER_LAYOUTS = Map.of(
            "connectedglass", List.of("PLAIN", "PIECED"),
            "glassential", List.of("PLAIN", "SIMPLE", "FULL", "PIECED"),
            "rechiseled", List.of(
                    "PLAIN", "PIECED", "FULL", "HORIZONTAL", "VERTICAL", "SIMPLE"
            ),
            "rechiseled-create", List.of(
                    "PLAIN", "PIECED", "FULL", "HORIZONTAL", "VERTICAL", "SIMPLE"
            )
    );

    @Test
    void matchesEveryConsumerLayoutForEveryByteMask() {
        CONSUMER_LAYOUTS.forEach((consumer, layouts) -> {
            for (String layout : layouts) {
                for (int mask = 0; mask <= 0xff; mask++) {
                    int expected = consumerTile(consumer, layout, mask);
                    int actual = FusionTextureSelector.tile(
                            FusionTextureLayout.valueOf(layout), mask
                    );
                    assertEquals(expected, actual, consumer + " " + layout + " " + mask);
                }
            }
        });
    }

    @Test
    void matchesEveryConsumerPiecedCornerForEveryByteMask() {
        CONSUMER_LAYOUTS.keySet().forEach(consumer -> {
            for (FusionDirection direction : FusionDirection.values()) {
                for (int mask = 0; mask <= 0xff; mask++) {
                    String label = consumer + " " + direction + " " + mask;
                    if (isCorner(direction)) {
                        assertEquals(
                                consumerCorner(consumer, mask, direction),
                                FusionTextureSelector.piecedCorner(mask, direction),
                                label
                        );
                    } else {
                        int selectedMask = mask;
                        assertThrows(
                                IllegalArgumentException.class,
                                () -> consumerCorner(consumer, selectedMask, direction),
                                label
                        );
                        assertThrows(
                                IllegalArgumentException.class,
                                () -> FusionTextureSelector.piecedCorner(selectedMask, direction),
                                label
                        );
                    }
                }
            }
        });
    }

    @Test
    void preservesInvalidMaskAndNullFailures() {
        for (FusionTextureLayout layout : FusionTextureLayout.values()) {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> FusionTextureSelector.tile(layout, -1)
            );
            assertThrows(
                    IllegalArgumentException.class,
                    () -> FusionTextureSelector.tile(layout, 0x100)
            );
        }
        assertThrows(NullPointerException.class, () -> FusionTextureSelector.tile(null, 0));
        assertThrows(
                NullPointerException.class,
                () -> FusionTextureSelector.piecedCorner(0, null)
        );
    }

    private static int consumerTile(String consumer, String layout, int mask) {
        return switch (consumer) {
            case "connectedglass" -> io.github.janguenter.bluemap.connectedglass.model
                    .FusionTextureSelector.tile(
                            io.github.janguenter.bluemap.connectedglass.profile.TextureLayout
                                    .valueOf(layout),
                            mask
                    );
            case "glassential" -> io.github.janguenter.bluemap.glassential.model
                    .FusionTextureSelector.tile(
                            io.github.janguenter.bluemap.glassential.profile.TextureLayout
                                    .valueOf(layout),
                            mask
                    );
            case "rechiseled" -> io.github.janguenter.bluemap.rechiseled.model
                    .FusionTextureSelector.tile(
                            io.github.janguenter.bluemap.rechiseled.profile.TextureLayout
                                    .valueOf(layout),
                            mask
                    );
            case "rechiseled-create" -> io.github.janguenter.bluemap.rechiseledcreate.model
                    .FusionTextureSelector.tile(
                            io.github.janguenter.bluemap.rechiseledcreate.profile.TextureLayout
                                    .valueOf(layout),
                            mask
                    );
            default -> throw new IllegalArgumentException("unknown consumer");
        };
    }

    private static int consumerCorner(
            String consumer,
            int mask,
            FusionDirection direction
    ) {
        IntSupplier selector = switch (consumer) {
            case "connectedglass" -> () -> io.github.janguenter.bluemap.connectedglass.model
                    .FusionTextureSelector.piecedCorner(
                            mask,
                            io.github.janguenter.bluemap.connectedglass.model.FusionDirection
                                    .valueOf(direction.name())
                    );
            case "glassential" -> () -> io.github.janguenter.bluemap.glassential.model
                    .FusionTextureSelector.piecedCorner(
                            mask,
                            io.github.janguenter.bluemap.glassential.model.FusionDirection
                                    .valueOf(direction.name())
                    );
            case "rechiseled" -> () -> io.github.janguenter.bluemap.rechiseled.model
                    .FusionTextureSelector.piecedCorner(
                            mask,
                            io.github.janguenter.bluemap.rechiseled.model.FusionDirection
                                    .valueOf(direction.name())
                    );
            case "rechiseled-create" -> () -> io.github.janguenter.bluemap.rechiseledcreate.model
                    .FusionTextureSelector.piecedCorner(
                            mask,
                            io.github.janguenter.bluemap.rechiseledcreate.model.FusionDirection
                                    .valueOf(direction.name())
                    );
            default -> throw new IllegalArgumentException("unknown consumer");
        };
        return selector.getAsInt();
    }

    private static boolean isCorner(FusionDirection direction) {
        return switch (direction) {
            case TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT -> true;
            default -> false;
        };
    }
}
