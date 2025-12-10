package com.nothinglondon.sdkdemo.demos.twinpeaks_combined

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
 * Twin Peaks Combined Service
 * Два паттерна в одном toy:
 * 1. Zigzag Pattern - елочка бежит вверх
 * 2. Owl Symbol - глитчевое мигание как прерывистый электрический контакт
 * Long Press переключает между ними
 */
class TwinPeaksCombinedService : GlyphMatrixService("Twin-Peaks-Combined") {

    private val animationScope = CoroutineScope(Dispatchers.IO)
    private var animationFrame = 0
    private var currentPattern = Pattern.ZIGZAG
    private var owlFlickerIndex = 0

    enum class Pattern {
        ZIGZAG,
        OWL_SYMBOL
    }

    override fun performOnServiceConnected(
        context: Context,
        glyphMatrixManager: GlyphMatrixManager
    ) {
        animationScope.launch {
            while (isActive) {
                val frameArray = when (currentPattern) {
                    Pattern.ZIGZAG -> generateZigzagFrame(animationFrame)
                    Pattern.OWL_SYMBOL -> generateOwlFlickerFrame()
                }
                glyphMatrixManager.setMatrixFrame(frameArray)
                
                when (currentPattern) {
                    Pattern.ZIGZAG -> {
                        delay(100L)
                        animationFrame++
                        if (animationFrame >= ZIGZAG_PATTERN_HEIGHT) {
                            animationFrame = 0
                        }
                    }
                    Pattern.OWL_SYMBOL -> {
                        // Glitch flicker timing
                        val flickerStep = owlFlickerSequence[owlFlickerIndex]
                        delay(flickerStep.durationMs)
                        owlFlickerIndex = (owlFlickerIndex + 1) % owlFlickerSequence.size
                    }
                }
            }
        }
    }

    override fun performOnServiceDisconnected(context: Context) {
        animationScope.cancel()
    }

    override fun onTouchPointLongPress() {
        // Переключаем паттерн по Long Press
        currentPattern = when (currentPattern) {
            Pattern.ZIGZAG -> Pattern.OWL_SYMBOL
            Pattern.OWL_SYMBOL -> Pattern.ZIGZAG
        }
        animationFrame = 0
        owlFlickerIndex = 0
    }

    // ==================== ZIGZAG PATTERN ====================
    
    private fun generateZigzagFrame(shift: Int): IntArray {
        val grid = IntArray(MATRIX_SIZE) { 0 }

        for (y in 0..<MATRIX_HEIGHT) {
            for (x in 0..<MATRIX_WIDTH) {
                val patternY = (y + shift) % ZIGZAG_PATTERN_HEIGHT
                
                if (isZigzagPixelLit(patternY, x)) {
                    grid[y * MATRIX_WIDTH + x] = 2047  // Полная яркость (как у Owl)
                }
            }
        }

        return grid
    }

    private fun isZigzagPixelLit(row: Int, col: Int): Boolean {
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

    // ==================== OWL SYMBOL PATTERN ====================
    
    /**
     * Glitch flicker step - яркость и длительность
     * brightness: 0-2047 (0 = выкл, 2047 = макс яркость)
     */
    private data class FlickerStep(val brightness: Int, val durationMs: Long)
    
    /**
     * Последовательность мигания с градациями яркости - как прерывистый электрический контакт
     * Яркость: 0 (выкл) до 2047 (макс)
     * Паттерн: глитчевое мигание → стабилизация → снова глитч → repeat
     */
    private val owlFlickerSequence = listOf(
        // Быстрое глитчевое мигание (контакт замыкается/размыкается)
        FlickerStep(2047, 100),  // Полная яркость
        FlickerStep(600, 80),    // Тусклая вспышка
        FlickerStep(0, 70),      // Выкл
        FlickerStep(2047, 100),  // Полная
        FlickerStep(400, 90),    // Очень тусклая
        FlickerStep(0, 60),      // Выкл
        FlickerStep(2047, 110),  // Полная
        FlickerStep(700, 85),    // Тусклая
        FlickerStep(0, 75),      // Выкл
        
        // Стабилизация - контакт нормализовался
        FlickerStep(2047, 1000), // Держится на полной яркости
        
        // Среднее мигание (контакт опять глючит)
        FlickerStep(1200, 150),  // Средняя яркость
        FlickerStep(2047, 150),  // Полная
        FlickerStep(0, 100),     // Выкл
        FlickerStep(900, 140),   // Слабая яркость
        FlickerStep(2047, 160),  // Полная
        
        // Держится со слабой яркостью (контакт не идеален)
        FlickerStep(0, 100),     // Выкл
        FlickerStep(1600, 800),  // Держится приглушённо
        
        // Финальное быстрое мигание
        FlickerStep(0, 80),      // Выкл
        FlickerStep(2047, 90),   // Полная
        FlickerStep(500, 70),    // Тусклая
        FlickerStep(0, 90),      // Выкл
        FlickerStep(2047, 100),  // Полная
        FlickerStep(300, 80),    // Очень тусклая
        FlickerStep(0, 70),      // Выкл
        FlickerStep(2047, 110),  // Полная
        
        // Финальная стабилизация
        FlickerStep(0, 150),     // Выкл
        FlickerStep(2047, 700)   // Держится на полной
    )
    
    private fun generateOwlFlickerFrame(): IntArray {
        val flickerStep = owlFlickerSequence[owlFlickerIndex]
        val brightness = flickerStep.brightness
        
        val grid = IntArray(MATRIX_SIZE) { 0 }
        
        if (brightness > 0) {
            // Применяем текущий уровень яркости ко всем пикселям паттерна
            for (y in 0..<MATRIX_HEIGHT) {
                for (x in 0..<MATRIX_WIDTH) {
                    val index = y * MATRIX_WIDTH + x
                    if (OWL_PATTERN[index] == 255) {
                        grid[index] = brightness
                    }
                }
            }
        }
        
        return grid
    }

    private companion object {
        private const val MATRIX_WIDTH = 25
        private const val MATRIX_HEIGHT = 25
        private const val MATRIX_SIZE = MATRIX_WIDTH * MATRIX_HEIGHT
        
        // Zigzag constants
        private const val ZIGZAG_PATTERN_HEIGHT = 24
        
        /**
         * Owl Symbol паттерн (Twin Peaks) - WIDE VERSION
         * Три ромба соединённые вместе (пошире чем первая версия)
         * Убраны крайние одиночные точки слева и справа
         */
        private val OWL_PATTERN = run {
            val grid = IntArray(625) { 0 }
            grid[5 * 25 + 6] = 255
            grid[5 * 25 + 12] = 255
            grid[5 * 25 + 18] = 255
            grid[6 * 25 + 5] = 255
            grid[6 * 25 + 6] = 255
            grid[6 * 25 + 7] = 255
            grid[6 * 25 + 11] = 255
            grid[6 * 25 + 12] = 255
            grid[6 * 25 + 13] = 255
            grid[6 * 25 + 17] = 255
            grid[6 * 25 + 18] = 255
            grid[6 * 25 + 19] = 255
            grid[7 * 25 + 4] = 255
            grid[7 * 25 + 5] = 255
            grid[7 * 25 + 6] = 255
            grid[7 * 25 + 7] = 255
            grid[7 * 25 + 8] = 255
            grid[7 * 25 + 10] = 255
            grid[7 * 25 + 11] = 255
            grid[7 * 25 + 12] = 255
            grid[7 * 25 + 13] = 255
            grid[7 * 25 + 14] = 255
            grid[7 * 25 + 16] = 255
            grid[7 * 25 + 17] = 255
            grid[7 * 25 + 18] = 255
            grid[7 * 25 + 19] = 255
            grid[7 * 25 + 20] = 255
            grid[8 * 25 + 3] = 255
            grid[8 * 25 + 4] = 255
            grid[8 * 25 + 5] = 255
            grid[8 * 25 + 7] = 255
            grid[8 * 25 + 8] = 255
            grid[8 * 25 + 9] = 255
            grid[8 * 25 + 10] = 255
            grid[8 * 25 + 11] = 255
            grid[8 * 25 + 13] = 255
            grid[8 * 25 + 14] = 255
            grid[8 * 25 + 15] = 255
            grid[8 * 25 + 16] = 255
            grid[8 * 25 + 17] = 255
            grid[8 * 25 + 19] = 255
            grid[8 * 25 + 20] = 255
            grid[8 * 25 + 21] = 255
            grid[9 * 25 + 2] = 255
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
            grid[9 * 25 + 22] = 255
            grid[10 * 25 + 1] = 255
            grid[10 * 25 + 2] = 255
            grid[10 * 25 + 3] = 255
            grid[10 * 25 + 7] = 255
            grid[10 * 25 + 8] = 255
            grid[10 * 25 + 9] = 255
            grid[10 * 25 + 15] = 255
            grid[10 * 25 + 16] = 255
            grid[10 * 25 + 17] = 255
            grid[10 * 25 + 21] = 255
            grid[10 * 25 + 22] = 255
            grid[10 * 25 + 23] = 255
            grid[11 * 25 + 1] = 255
            grid[11 * 25 + 2] = 255
            grid[11 * 25 + 6] = 255
            grid[11 * 25 + 7] = 255
            grid[11 * 25 + 8] = 255
            grid[11 * 25 + 16] = 255
            grid[11 * 25 + 17] = 255
            grid[11 * 25 + 18] = 255
            grid[11 * 25 + 22] = 255
            grid[11 * 25 + 23] = 255
            // grid[12 * 25 + 0] = 255  // REMOVED - левая крайняя точка
            grid[12 * 25 + 5] = 255
            grid[12 * 25 + 6] = 255
            grid[12 * 25 + 7] = 255
            grid[12 * 25 + 17] = 255
            grid[12 * 25 + 18] = 255
            grid[12 * 25 + 19] = 255
            // grid[12 * 25 + 24] = 255  // REMOVED - правая крайняя точка
            grid[13 * 25 + 6] = 255
            grid[13 * 25 + 7] = 255
            grid[13 * 25 + 8] = 255
            grid[13 * 25 + 16] = 255
            grid[13 * 25 + 17] = 255
            grid[13 * 25 + 18] = 255
            grid[14 * 25 + 7] = 255
            grid[14 * 25 + 8] = 255
            grid[14 * 25 + 9] = 255
            grid[14 * 25 + 15] = 255
            grid[14 * 25 + 16] = 255
            grid[14 * 25 + 17] = 255
            grid[15 * 25 + 8] = 255
            grid[15 * 25 + 9] = 255
            grid[15 * 25 + 10] = 255
            grid[15 * 25 + 14] = 255
            grid[15 * 25 + 15] = 255
            grid[15 * 25 + 16] = 255
            grid[16 * 25 + 9] = 255
            grid[16 * 25 + 10] = 255
            grid[16 * 25 + 11] = 255
            grid[16 * 25 + 13] = 255
            grid[16 * 25 + 14] = 255
            grid[16 * 25 + 15] = 255
            grid[17 * 25 + 10] = 255
            grid[17 * 25 + 11] = 255
            grid[17 * 25 + 12] = 255
            grid[17 * 25 + 13] = 255
            grid[17 * 25 + 14] = 255
            grid[18 * 25 + 11] = 255
            grid[18 * 25 + 12] = 255
            grid[18 * 25 + 13] = 255
            grid[19 * 25 + 12] = 255
            grid
        }
    }

}
