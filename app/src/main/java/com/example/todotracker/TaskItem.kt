package com.example.todotracker

import java.util.UUID

data class TaskItem(
    private val id: UUID = UUID.randomUUID(),
    var name: String,
    var stepsCompleted: Int = 0,
    var stepsTotal: Int,
    var isComplete: Boolean = stepsCompleted >= stepsTotal
) {
}