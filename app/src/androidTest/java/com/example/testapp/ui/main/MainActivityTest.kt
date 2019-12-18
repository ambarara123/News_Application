package com.example.testapp.ui.main

import android.view.Gravity
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.testapp.R
import com.example.testapp.ui.search.SearchActivity
import com.example.testapp.ui.search.SearchPagedAdapter
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest{

    @get:Rule
    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    //androidx.appcompat.R.id.search_src_text

    @Test
    fun checkSearchClick(){

        onView(withId(R.id.searchMenu)).check(matches(isClickable()))

        onView(withId(R.id.searchMenu)).perform(click())

        onView(withId(R.id.toolbarSearch)).check(matches(isDisplayed()))

        onView(withId(androidx.appcompat.R.id.search_button)).perform(click())

    }

    @Test
    fun clickingOutsideClosesNavigationDrawer() {

        onView(withId(R.id.drawerLayout))
            .perform(click())

        onView(withId(R.id.recyclerView))
            .perform(click())

        onView(withId(R.id.navView))
            .check(matches(not(isDisplayed())))

    }

    @Test
    fun checkBooksRecyclerAppearing() {

        onView(withId(R.id.drawerLayout))
            .check(matches(isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open())

            onView(withId(R.id.navView))
                .perform(NavigationViewActions.navigateTo(R.id.actionBooks))

        onView(withId(R.id.bookRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun testStoryRecycler(){
       // Thread.sleep(10000)

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))

        checkBooksRecyclerAppearing()

    }

    @Test
    fun testSearchRecycler(){
        checkSearchClick()

        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("News\n"))

        onView(withId(R.id.searchRecyclerView)).check(matches(isDisplayed()))

    }

    @Test
    fun testPagination(){
        testSearchRecycler()

        Thread.sleep(5000)

        onView(withId(R.id.searchRecyclerView)).perform(RecyclerViewActions.scrollToPosition<SearchPagedAdapter.ViewHolder>(10))

        Thread.sleep(5000)

        onView(withId(R.id.searchRecyclerView)).perform(RecyclerViewActions.scrollToPosition<SearchPagedAdapter.ViewHolder>(10))

    }

    @Test
    fun testOnCloseSearchView(){

        testSearchRecycler()

        onView(withId(androidx.appcompat.R.id.search_close_btn)).perform(doubleClick())

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }

}

class RecyclerIdlingResource(val nameIdle : String,val isIdle : Boolean) : IdlingResource{

    lateinit var resourceCallback: IdlingResource.ResourceCallback

    override fun getName(): String {
        return nameIdle
    }

    override fun isIdleNow(): Boolean {
        return isIdle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        resourceCallback  = callback!!
    }

}