package com.nothinglondon.sdkdemo.demos.judy

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
 * Owl Symbol Rotation - 3D Y-AXIS ROTATION
 * Twin Peaks символ совы (три ромба) вращается вокруг вертикальной оси (центральный столбец)
 * Столбцы последовательно гаснут с краёв к центру, потом загораются обратно
 */
class JudyRotationService : GlyphMatrixService("Owl-Symbol-Rotation") {

    private val animationScope = CoroutineScope(Dispatchers.IO)
    private var animationFrame = 0

    override fun performOnServiceConnected(
        context: Context,
        glyphMatrixManager: GlyphMatrixManager
    ) {
        animationScope.launch {
            while (isActive) {
                val rotatedArray = generateRotationFrame(animationFrame)
                glyphMatrixManager.setMatrixFrame(rotatedArray)
                delay(80)  // 80ms между кадрами для плавности
                animationFrame++
                if (animationFrame >= TOTAL_FRAMES) {
                    animationFrame = 0
                }
            }
        }
    }

    override fun performOnServiceDisconnected(context: Context) {
        animationScope.cancel()
    }

    private fun generateRotationFrame(frame: Int): IntArray {
        val grid = IntArray(MATRIX_SIZE) { 0 }
        
        // Вычисляем ширину видимой части для текущего кадра
        val width = calculateVisibleWidth(frame)
        
        // Центральный столбец - ось вращения
        val centerCol = 12
        
        for (y in 0..<MATRIX_HEIGHT) {
            for (x in 0..<MATRIX_WIDTH) {
                // Показываем только столбцы в пределах width от центра
                if (x >= centerCol - width && x <= centerCol + width) {
                    if (isPixelLitInPattern(y, x)) {
                        grid[y * MATRIX_WIDTH + x] = 255
                    }
                }
            }
        }
        
        return grid
    }

    /**
     * Вычисляет ширину видимой части для текущего кадра
     * 0-11: сжатие (width уменьшается от 12 до 0)
     * 12-23: расширение (width увеличивается от 0 до 12)
     */
    private fun calculateVisibleWidth(frame: Int): Int {
        return if (frame < HALF_FRAMES) {
            // Сжатие: 12 -> 0
            HALF_FRAMES - frame
        } else {
            // Расширение: 0 -> 12
            frame - HALF_FRAMES
        }
    }

    /**
     * Базовый паттерн Owl Symbol из Pattern Editor
     * Извлечён из Kotlin code (25x25 grid, значения 0 или 255)
     */
    private fun isPixelLitInPattern(row: Int, col: Int): Boolean {
        // Проверяем границы
        if (row < 0 || row >= MATRIX_HEIGHT || col < 0 || col >= MATRIX_WIDTH) {
            return false
        }
        
        val index = row * MATRIX_WIDTH + col
        return OWL_PATTERN[index] == 255
    }

    private companion object {
        private const val MATRIX_WIDTH = 25
        private const val MATRIX_HEIGHT = 25
        private const val MATRIX_SIZE = MATRIX_WIDTH * MATRIX_HEIGHT
        private const val TOTAL_FRAMES = 24  // 12 сжатие + 12 расширение
        private const val HALF_FRAMES = 12
        
        /**
         * Owl Symbol паттерн (Twin Peaks)
         * Три ромба соединённые вместе - символ из Red Room
         * 625 значений: 0 или 255
         */
        private val OWL_PATTERN = run {
            val grid = IntArray(625) { 0 }
            grid[6 * 25 + 6] = 255
            grid[6 * 25 + 12] = 255
            grid[6 * 25 + 18] = 255
            grid[7 * 25 + 5] = 255
            grid[7 * 25 + 6] = 255
            grid[7 * 25 + 7] = 255
            grid[7 * 25 + 11] = 255
            grid[7 * 25 + 12] = 255
            grid[7 * 25 + 13] = 255
            grid[7 * 25 + 17] = 255
            grid[7 * 25 + 18] = 255
            grid[7 * 25 + 19] = 255
            grid[8 * 25 + 4] = 255
            grid[8 * 25 + 5] = 255
            grid[8 * 25 + 7] = 255
            grid[8 * 25 + 8] = 255
            grid[8 * 25 + 10] = 255
            grid[8 * 25 + 11] = 255
            grid[8 * 25 + 13] = 255
            grid[8 * 25 + 14] = 255
            grid[8 * 25 + 16] = 255
            grid[8 * 25 + 17] = 255
            grid[8 * 25 + 19] = 255
            grid[8 * 25 + 20] = 255
            grid[9 * 25 + 3] = 255
            grid[9 * 25 + 4] = 255
            grid[9 * 25 + 8] = 255
            grid[9 * 25 + 9] = 255
            grid[9 * 25 + 10] = 255
            grid[9 * 25 + 14] = 255
            grid[9 * 25 + 15] = 255
            grid[9 * 25 + 16] = 255
            grid[9 * 25 + 20] = 255
            grid[9 * 25 + 21] = 255
            grid[10 * 25 + 2] = 255
            grid[10 * 25 + 3] = 255
            grid[10 * 25 + 8] = 255
            grid[10 * 25 + 9] = 255
            grid[10 * 25 + 15] = 255
            grid[10 * 25 + 16] = 255
            grid[10 * 25 + 21] = 255
            grid[10 * 25 + 22] = 255
            grid[11 * 25 + 7] = 255
            grid[11 * 25 + 8] = 255
            grid[11 * 25 + 16] = 255
            grid[11 * 25 + 17] = 255
            grid[12 * 25 + 6] = 255
            grid[12 * 25 + 7] = 255
            grid[12 * 25 + 17] = 255
            grid[12 * 25 + 18] = 255
            grid[13 * 25 + 7] = 255
            grid[13 * 25 + 8] = 255
            grid[13 * 25 + 16] = 255
            grid[13 * 25 + 17] = 255
            grid[14 * 25 + 8] = 255
            grid[14 * 25 + 9] = 255
            grid[14 * 25 + 15] = 255
            grid[14 * 25 + 16] = 255
            grid[15 * 25 + 9] = 255
            grid[15 * 25 + 10] = 255
            grid[15 * 25 + 14] = 255
            grid[15 * 25 + 15] = 255
            grid[16 * 25 + 10] = 255
            grid[16 * 25 + 11] = 255
            grid[16 * 25 + 13] = 255
            grid[16 * 25 + 14] = 255
            grid[17 * 25 + 11] = 255
            grid[17 * 25 + 12] = 255
            grid[17 * 25 + 13] = 255
            grid[18 * 25 + 12] = 255
            grid
        }
    }

}
