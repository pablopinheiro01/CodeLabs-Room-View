package br.com.part.codelabs.extensions

import br.com.part.codelabs.feature.data.entity.TaskDateDto
import org.threeten.bp.OffsetDateTime

fun OffsetDateTime.convertToTaskDateDto(): TaskDateDto{
    return TaskDateDto(
        day = dayOfMonth,
        month = monthValue,
        year = year,
        hour = hour,
        minute =  minute
    )
}