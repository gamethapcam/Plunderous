package com.fangelo.plunderous.client

open class PlatformAdapter {

    var version = ""
        protected set

    init {
        instance = this
    }

    open fun dispose() {

    }

    companion object {

        lateinit var instance: PlatformAdapter
            private set

        fun dispose() {
            instance.dispose()
        }
    }
}
