package com.fangelo.libraries.ashley.systems.renderers

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.fangelo.libraries.ashley.components.Camera
import com.fangelo.libraries.ashley.components.Tilemap
import com.fangelo.libraries.ashley.components.Transform
import com.fangelo.libraries.ashley.components.VisualTileset
import com.fangelo.libraries.ashley.systems.utils.VisualTilemapRenderBounds
import com.fangelo.libraries.ashley.systems.utils.VisualTilemapRenderBoundsCalculator
import ktx.ashley.allOf
import ktx.ashley.mapperFor

class VisualTilemapRenderSystem : VisualCameraRenderer() {

    private lateinit var entities: ImmutableArray<Entity>

    private val transform = mapperFor<Transform>()
    private val visualTileset = mapperFor<VisualTileset>()
    private val tilemap = mapperFor<Tilemap>()

    private lateinit var batch: SpriteBatch
    private val renderBoundsCalculator = VisualTilemapRenderBoundsCalculator()

    override fun addedToEngine(engine: Engine) {
        batch = SpriteBatch()
        entities = engine.getEntitiesFor(allOf(Transform::class, VisualTileset::class, Tilemap::class).get())
    }

    override fun removedFromEngine(engine: Engine) {
        batch.dispose()
    }

    override fun render(camera: Camera) {
        beginRender(camera)

        drawTilemaps(camera)

        endRender()
    }

    private val tmpBounds = VisualTilemapRenderBounds()

    private fun drawTilemaps(camera: Camera) {
        var tilemap: Tilemap
        var tilemapTransform: Transform
        var visualTileset: VisualTileset

        for (e in entities) {

            tilemap = this.tilemap.get(e)
            tilemapTransform = this.transform.get(e)
            visualTileset = this.visualTileset.get(e)

            val bounds = renderBoundsCalculator.calculate(camera, tilemap, tilemapTransform, tmpBounds)

            drawFloor(bounds, tilemap, visualTileset)
        }
    }

    private fun drawFloor(bounds: VisualTilemapRenderBounds, tilemap: Tilemap, visualTileset: VisualTileset) {
        val renderOffsetX = bounds.renderOffsetX.toFloat()

        var drawY = bounds.renderOffsetY.toFloat()

        batch.disableBlending()
        for (mapX in bounds.fromY until bounds.toY) {
            var drawX = renderOffsetX
            for (mapY in bounds.fromX until bounds.toX) {
                val tile = tilemap.getTile(mapX, mapY)
                val tileTexture = visualTileset.getTileTexture(tile)
                batch.draw(tileTexture, drawX, drawY, 1f, 1f)
                drawX++
            }
            drawY++
        }
    }

    private fun beginRender(camera: Camera) {
        batch.projectionMatrix = camera.combined
        batch.begin()
    }

    private fun endRender() {
        batch.end()
    }
}