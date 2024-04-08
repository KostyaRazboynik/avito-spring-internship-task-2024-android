package com.kostyarazboynik.domain.data

interface MergeStrategy<E> {

    fun merge(left: E, right: E): E
}
