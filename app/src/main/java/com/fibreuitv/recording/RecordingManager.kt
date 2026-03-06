package com.fibreuitv.recording

import com.fibreuitv.model.Program

data class RecordingJob(
    val programId: String,
    val title: String,
    val startTime: String,
    val endTime: String,
    val status: String
)

object RecordingManager {
    private val jobs = mutableListOf<RecordingJob>()

    fun schedule(program: Program) {
        jobs += RecordingJob(
            programId = program.id,
            title = program.title,
            startTime = program.startTime,
            endTime = program.endTime,
            status = "Scheduled"
        )
    }

    fun all(): List<RecordingJob> = jobs.toList()
}
