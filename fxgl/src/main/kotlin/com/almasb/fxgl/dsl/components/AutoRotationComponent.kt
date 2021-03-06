/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package com.almasb.fxgl.dsl.components

import com.almasb.fxgl.core.math.FXGLMath
import com.almasb.fxgl.entity.component.Component
import kotlin.math.abs

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
class AutoRotationComponent : Component() {

    private var isSmoothing = false
    private var prevX = 0.0
    private var prevY = 0.0

    override fun onAdded() {
        prevX = entity.x
        prevY = entity.y
    }

    override fun onUpdate(tpf: Double) {
        val angle = FXGLMath.toDegrees(FXGLMath.atan2(entity.y - prevY, entity.x - prevX))

        val angles = doubleArrayOf(
                angle,
                angle + 360,
                angle - 360
        )

        val nextAngle = angles.minBy { abs(it - entity.rotation) }!!

        if (!isSmoothing) {
            entity.rotation = nextAngle
        } else {
            entity.rotation = entity.rotation * 0.9 + nextAngle * 0.1
        }

        prevX = entity.x
        prevY = entity.y
    }

    fun withSmoothing(): AutoRotationComponent {
        isSmoothing = true
        return this
    }

    override fun isComponentInjectionRequired(): Boolean = false
}