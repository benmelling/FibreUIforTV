package com.fibreuitv.player

import com.fibreuitv.model.Program

/**
 * Plug your real player backend here (ExoPlayer, IPTV parser, RD stream resolver, DRM policy).
 */
object PlayerFacade {
    fun play(program: Program): String {
        return "Playing ${program.title} from ${program.channelName}"
    }
}
