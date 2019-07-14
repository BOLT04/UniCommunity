package pt.isel.g20.unicommunity.common

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

fun <T> Page<T>.getFirstPageable() : Pageable =
    if (isFirst)
        pageable
    else
        pageable.first()

fun <T> Page<T>.getLastPage() : Pageable =
    if (isLast)
        pageable
    else
        findLastPageable(pageable)

private fun findLastPageable(pageable: Pageable) : Pageable =
    if (pageable.isUnpaged) pageable
    else findLastPageable(pageable.next())

