package com.depthglass.studio.wallpaper

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import com.depthglass.studio.data.model.DeviceSpec

/**
 * Live wallpaper service with gyroscope-driven parallax.
 * Optimized for Motorola Edge 60 Pro (1220 × 2712).
 *
 * In production this would:
 * - Load the saved WallpaperProject layers
 * - Render each layer with depth-based offset from gyroscope
 * - Draw clock + widgets on top
 * - Use efficient Canvas or OpenGL ES rendering
 */
class DepthLiveWallpaperService : WallpaperService() {

    override fun onCreateEngine(): Engine = DepthEngine()

    inner class DepthEngine : Engine(), SensorEventListener {
        private var sensorManager: SensorManager? = null
        private var gyroscope: Sensor? = null
        private var offsetX = 0f
        private var offsetY = 0f
        private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        private var visible = false

        override fun onCreate(surfaceHolder: SurfaceHolder) {
            super.onCreate(surfaceHolder)
            sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
            gyroscope = sensorManager?.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        }

        override fun onVisibilityChanged(visible: Boolean) {
            this.visible = visible
            if (visible) {
                gyroscope?.let {
                    sensorManager?.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
                }
                drawFrame()
            } else {
                sensorManager?.unregisterListener(this)
            }
        }

        override fun onSurfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            drawFrame()
        }

        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
                // Simple integration for parallax offset
                offsetX += event.values[1] * 4f
                offsetY += event.values[0] * 4f
                offsetX = offsetX.coerceIn(-40f, 40f)
                offsetY = offsetY.coerceIn(-40f, 40f)
                if (visible) drawFrame()
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

        private fun drawFrame() {
            val holder = surfaceHolder
            var canvas: Canvas? = null
            try {
                canvas = holder.lockCanvas()
                if (canvas != null) {
                    // Clear
                    canvas.drawColor(Color.parseColor("#0A0A0F"))

                    // Placeholder: in production render layered bitmaps with parallax
                    paint.color = Color.parseColor("#4FC3F7")
                    paint.textSize = 48f
                    paint.textAlign = Paint.Align.CENTER
                    canvas.drawText(
                        "DepthGlass 3D",
                        canvas.width / 2f + offsetX,
                        canvas.height / 2f + offsetY,
                        paint
                    )
                    paint.textSize = 28f
                    paint.color = Color.parseColor("#B0B0C0")
                    canvas.drawText(
                        "${DeviceSpec.WIDTH} × ${DeviceSpec.HEIGHT}",
                        canvas.width / 2f + offsetX * 0.5f,
                        canvas.height / 2f + 60f + offsetY * 0.5f,
                        paint
                    )
                }
            } finally {
                if (canvas != null) {
                    try {
                        holder.unlockCanvasAndPost(canvas)
                    } catch (_: Exception) {}
                }
            }
        }

        override fun onDestroy() {
            sensorManager?.unregisterListener(this)
            super.onDestroy()
        }
    }
}
