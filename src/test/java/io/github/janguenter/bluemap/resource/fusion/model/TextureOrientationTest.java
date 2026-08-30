/*
 * SPDX-License-Identifier: MIT
 */
package io.github.janguenter.bluemap.resource.fusion.model;

import de.bluecolored.bluemap.core.util.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TextureOrientationTest {

    @Test
    void classifiesAllEightFramesOnEveryFinalFace() {
        for (Direction face : Direction.values()) {
            AxisVector up = TextureOrientation.baseUp(face);
            AxisVector right = TextureOrientation.baseRight(face);
            assertFrame(face, up, right, TextureOrientation.N0);
            assertFrame(face, right, up.negate(), TextureOrientation.N90);
            assertFrame(face, up.negate(), right.negate(), TextureOrientation.N180);
            assertFrame(face, right.negate(), up, TextureOrientation.N270);
            assertFrame(face, right.negate(), up.negate(), TextureOrientation.F0);
            assertFrame(face, up.negate(), right, TextureOrientation.F90);
            assertFrame(face, right, up, TextureOrientation.F180);
            assertFrame(face, up, right.negate(), TextureOrientation.F270);
        }
    }

    @Test
    void matchesTheFrozenRechiseledOrientationForEveryFrameAndDirection() {
        for (Direction face : Direction.values()) {
            AxisVector baseUp = TextureOrientation.baseUp(face);
            AxisVector baseRight = TextureOrientation.baseRight(face);
            AxisVector[] upCandidates = {
                baseUp, baseRight, baseUp.negate(), baseRight.negate(),
                baseRight.negate(), baseUp.negate(), baseRight, baseUp
            };
            AxisVector[] rightCandidates = {
                baseRight, baseUp.negate(), baseRight.negate(), baseUp,
                baseUp.negate(), baseRight, baseUp, baseRight.negate()
            };
            for (int index = 0; index < TextureOrientation.values().length; index++) {
                TextureOrientation.Frame actual = TextureOrientation.classify(
                        face, upCandidates[index], rightCandidates[index]
                );
                io.github.janguenter.bluemap.rechiseled.model.TextureOrientation.Frame expected =
                        io.github.janguenter.bluemap.rechiseled.model.TextureOrientation.classify(
                                face,
                                origin(upCandidates[index]),
                                origin(rightCandidates[index])
                        );
                assertEquals(expected.orientation().name(), actual.orientation().name());
                for (FusionDirection direction : FusionDirection.values()) {
                    assertEquals(
                            expected.offset(origin(direction)).toString(),
                            actual.offset(direction).toString()
                    );
                    assertEquals(
                            expected.predicateDirection(origin(direction)).name(),
                            actual.predicateDirection(direction).name()
                    );
                }
            }
        }
    }

    @Test
    void preservesInvalidFrameFailure() {
        assertThrows(
                IllegalArgumentException.class,
                () -> TextureOrientation.classify(
                        Direction.UP, new AxisVector(0, 1, 0), new AxisVector(0, -1, 0)
                )
        );
    }

    private static io.github.janguenter.bluemap.rechiseled.model.AxisVector origin(
            AxisVector vector
    ) {
        return new io.github.janguenter.bluemap.rechiseled.model.AxisVector(
                vector.x(), vector.y(), vector.z()
        );
    }

    private static io.github.janguenter.bluemap.rechiseled.model.FusionDirection origin(
            FusionDirection direction
    ) {
        return io.github.janguenter.bluemap.rechiseled.model.FusionDirection
                .valueOf(direction.name());
    }

    private static void assertFrame(
            Direction face,
            AxisVector up,
            AxisVector right,
            TextureOrientation expected
    ) {
        assertEquals(expected, TextureOrientation.classify(face, up, right).orientation());
    }
}
