package com.nothinglondon.sdkdemo.demos.twinpeaks

import android.content.Context
import com.nothing.ketchum.GlyphMatrixManager
import com.nothinglondon.sdkdemo.demos.GlyphMatrixService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * Twin Peaks Chevron - ПОЛНАЯ ЕЛОЧКА
 * Острые V-образные волны, бегущие вверх по матрице (25x25 LED)
 * Паттерн из Pattern Editor - повторяется и бежит вверх
 */
class TwinPeaksZigzagService : GlyphMatrixService("Twin-Peaks-Zigzag") {

    private val animationScope = CoroutineScope(Dispatchers.IO)
    private var animationFrame = 0

    override fun performOnServiceConnected(
        context: Context,
        glyphMatrixManager: GlyphMatrixManager
    ) {
        animationScope.launch {
            while (isActive) {
                val chevronArray = generateChevronFrame(animationFrame)
                glyphMatrixManager.setMatrixFrame(chevronArray)
                delay(100)  // 100ms между кадрами
                animationFrame++
                if (animationFrame >= PATTERN_HEIGHT) {
                    animationFrame = 0
                }
            }
        }
    }

    override fun performOnServiceDisconnected(context: Context) {
        animationScope.cancel()
    }

    private fun generateChevronFrame(shift: Int): IntArray {
        val grid = IntArray(MATRIX_SIZE) { 0 }

        for (y in 0..<MATRIX_HEIGHT) {
            for (x in 0..<MATRIX_WIDTH) {
                // ✅ ПРАВИЛЬНО: сдвигаем паттерн вверх
                val patternY = (y + shift) % PATTERN_HEIGHT
                
                if (isPixelLit(patternY, x)) {
                    grid[y * MATRIX_WIDTH + x] = 255
                }
            }
        }

        return grid
    }

    /**
     * Базовый паттерн из Pattern Editor (24 ряда - исправленный!)
     */
    private fun isPixelLit(row: Int, col: Int): Boolean {
        val pattern = arrayOf(
            booleanArrayOf(false, false, false, false, true, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, true, false, false, false, false),
            booleanArrayOf(false, false, false, true, true, true, false, false, false, false, false, true, true, true, false, false, false, false, false, true, true, true, false, false, false),
            booleanArrayOf(false, false, true, true, true, true, true, false, false, false, true, true, true, true, true, false, false, false, true, true, true, true, true, false, false),
            booleanArrayOf(false, true, true, true, false, true, true, true, false, true, true, true, false, true, true, true, false, true, true, true, false, true, true, true, false),
            booleanArrayOf(true, true, true, false, false, false, true, true, true, true, true, false, false, false, true, true, true, true, true, false, false, false, true, true, true),
            booleanArrayOf(true, true, false, false, false, false, false, true, true, true, false, false, false, false, false, true, true, true, false, false, false, false, false, true, true),
            booleanArrayOf(true, false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true),
            booleanArrayOf(false, false, false, true, true, true, false, false, false, false, false, true, true, true, false, false, false, false, false, true, true, true, false, false, false),
            booleanArrayOf(false, false, true, true, true, true, true, false, false, false, true, true, true, true, true, false, false, false, true, true, true, true, true, false, false),
            booleanArrayOf(false, true, true, true, false, true, true, true, false, true, true, true, false, true, true, true, false, true, true, true, false, true, true, true, false),
            booleanArrayOf(true, true, true, false, false, false, true, true, true, true, true, false, false, false, true, true, true, true, true, false, false, false, true, true, true),
            booleanArrayOf(true, true, false, false, false, false, false, true, true, true, false, false, false, false, false, true, true, true, false, false, false, false, false, true, true),
            booleanArrayOf(true, false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true),
            booleanArrayOf(false, false, false, true, true, true, false, false, false, false, false, true, true, true, false, false, false, false, false, true, true, true, false, false, false),
            booleanArrayOf(false, false, true, true, true, true, true, false, false, false, true, true, true, true, true, false, false, false, true, true, true, true, true, false, false),
            booleanArrayOf(false, true, true, true, false, true, true, true, false, true, true, true, false, true, true, true, false, true, true, true, false, true, true, true, false),
            booleanArrayOf(true, true, true, false, false, false, true, true, true, true, true, false, false, false, true, true, true, true, true, false, false, false, true, true, true),
            booleanArrayOf(true, true, false, false, false, false, false, true, true, true, false, false, false, false, false, true, true, true, false, false, false, false, false, true, true),
            booleanArrayOf(true, false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true),
            booleanArrayOf(false, false, false, true, true, true, false, false, false, false, false, true, true, true, false, false, false, false, false, true, true, true, false, false, false),
            booleanArrayOf(false, false, true, true, true, true, true, false, false, false, true, true, true, true, true, false, false, false, true, true, true, true, true, false, false),
            booleanArrayOf(false, true, true, true, false, true, true, true, false, true, true, true, false, true, true, true, false, true, true, true, false, true, true, true, false),
            booleanArrayOf(true, true, true, false, false, false, true, true, true, true, true, false, false, false, true, true, true, true, true, false, false, false, true, true, true),
            booleanArrayOf(true, true, false, false, false, false, false, true, true, true, false, false, false, false, false, true, true, true, false, false, false, false, false, true, true)
        )

        return if (row < pattern.size) pattern[row][col] else false
    }

    private companion object {
        private const val MATRIX_WIDTH = 25
        private const val MATRIX_HEIGHT = 25
        private const val MATRIX_SIZE = MATRIX_WIDTH * MATRIX_HEIGHT
        private const val PATTERN_HEIGHT = 24  // ✅ ИСПРАВЛЕННАЯ ВЫСОТА (было 19, теперь 24 - симметрично!)
    }

}
