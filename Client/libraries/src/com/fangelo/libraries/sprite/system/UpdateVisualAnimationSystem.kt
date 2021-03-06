package com.fangelo.libraries.sprite.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.fangelo.libraries.sprite.component.VisualAnimation
import com.fangelo.libraries.sprite.component.VisualSprite
import ktx.ashley.allOf
import ktx.ashley.mapperFor

class UpdateVisualAnimationSystem : IteratingSystem(allOf(VisualSprite::class, VisualAnimation::class).get()) {
    private val visual = mapperFor<VisualSprite>()
    private val animation = mapperFor<VisualAnimation>()

    public override fun processEntity(entity: Entity, deltaTime: Float) {
        val visual = visual.get(entity)
        val animation = animation.get(entity)

        if (animation.playing)
            animation.animationTime += deltaTime
        visual.mainSprite?.texture = animation.activeAnimation.getKeyFrame(animation.animationTime)
    }
}