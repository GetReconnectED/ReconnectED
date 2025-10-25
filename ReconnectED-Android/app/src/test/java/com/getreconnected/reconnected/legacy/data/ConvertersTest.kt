package com.getreconnected.reconnected.legacy.data

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for Converters class (Room database type converters)
 */
class ConvertersTest {

    private lateinit var converters: Converters

    @Before
    fun setup() {
        converters = Converters()
    }

    @Test
    fun fromList_withMultipleItems_returnsCommaSeparatedString() {
        val list = listOf("item1", "item2", "item3")
        val result = converters.fromList(list)
        assertEquals("item1,item2,item3", result)
    }

    @Test
    fun fromList_withSingleItem_returnsSingleItem() {
        val list = listOf("singleItem")
        val result = converters.fromList(list)
        assertEquals("singleItem", result)
    }

    @Test
    fun fromList_withEmptyList_returnsEmptyString() {
        val list = emptyList<String>()
        val result = converters.fromList(list)
        assertEquals("", result)
    }

    @Test
    fun toList_withCommaSeparatedString_returnsListOfItems() {
        val string = "item1,item2,item3"
        val result = converters.toList(string)
        assertEquals(listOf("item1", "item2", "item3"), result)
    }

    @Test
    fun toList_withSingleItem_returnsListWithOneItem() {
        val string = "singleItem"
        val result = converters.toList(string)
        assertEquals(listOf("singleItem"), result)
    }

    @Test
    fun toList_withEmptyString_returnsListWithEmptyString() {
        val string = ""
        val result = converters.toList(string)
        assertEquals(listOf(""), result)
    }

    @Test
    fun roundTrip_convertsListToStringAndBack() {
        val originalList = listOf("apple", "banana", "cherry")
        val string = converters.fromList(originalList)
        val resultList = converters.toList(string)
        assertEquals(originalList, resultList)
    }

    @Test
    fun fromList_withItemsContainingSpaces_preservesSpaces() {
        val list = listOf("item one", "item two", "item three")
        val result = converters.fromList(list)
        assertEquals("item one,item two,item three", result)
    }

    @Test
    fun toList_withItemsContainingSpaces_preservesSpaces() {
        val string = "item one,item two,item three"
        val result = converters.toList(string)
        assertEquals(listOf("item one", "item two", "item three"), result)
    }
}
