package com.gregorymarkthomas.backstack

import com.gregorymarkthomas.backstack.interfaces.BackStackViewCallback
import com.gregorymarkthomas.backstack.view.AView
import com.gregorymarkthomas.backstack.view.BView
import com.gregorymarkthomas.backstack.view.CView
import io.mockk.clearMocks
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

/**
 * Look here: https://blog.philipphauer.de/best-practices-unit-testing-kotlin/
 *
 * https://blog.kotlin-academy.com/mocking-is-not-rocket-science-expected-behavior-and-behavior-verification-3862dd0e0f03
 * every { mock.call(more(5)) } returns 1
 * every { mock.call(or(less(5), eq(5))) } returns -1
 */
class BackStackTest {

    /**
     * 'relaxUnitFun = true' will stop 'io.mockk.MockKException: no answer found for...' error for our simple use of a mocked BackStackCallback
     */
    private val callback: BackStackViewCallback = mockk(relaxUnitFun = true)
    private lateinit var backstack: BackStack

    @Before
    fun init() {
        clearMocks(callback)
        backstack = BackStack(callback)
    }

    @Test
    fun `has default view`() {
        backstack.goTo(AView())

        /** then **/
        assertNotNull(backstack.getMostRecentView())
        assertEquals(AView::class.java, backstack.getMostRecentView()::class.java)
    }

    @Test
    fun `views are added to the stack`() {
        /** Instantiate with default view **/
        backstack.goTo(AView())

        /** Add more views **/
        backstack.goTo(BView())
        backstack.goTo(CView())

        /** Check stack has the 3 views **/
        val stack = backstack.getCurrentViewClasses()
        assertEquals(3, stack.size)
        assertEquals("AView", stack[0])
        assertEquals("BView", stack[1])
        assertEquals("CView", stack[2])
    }


    /**
     * If the stack has x different views in the stack, and we want to show the FIRST view again (which is at the bottom of the stack),
     * then if we return the stack we should only see the chosen item.
     */
    @Test
    fun `re-uses first stack item and clears rest of stack`() {
        /** Instantiate with default view **/
        backstack.goTo(AView())

        /** Add more views **/
        backstack.goTo(BView())
        backstack.goTo(CView())

        /** Re-use FIRST view **/
        backstack.goTo(AView())

        /** Check stack has only the 1 view **/
        val stack = backstack.getCurrentViewClasses()
        assertEquals(1, stack.size)
        assertEquals("AView", stack[0])
    }

    /**
     * If the stack has x different views in the stack, and we want to show the SECOND view again (which is at the bottom of the stack + 1),
     * then if we return the stack we should only see the chosen item and only one before it.
     */
    @Test
    fun `re-uses second stack item and clears rest of stack`() {
        /** Instantiate with default view **/
        backstack.goTo(AView())

        /** Add more views **/
        backstack.goTo(BView())
        backstack.goTo(CView())

        /** Re-use the SECOND view **/
        backstack.goTo(BView())

        /** Check stack has only the 2 views **/
        val stack = backstack.getCurrentViewClasses()
        assertEquals(2, stack.size)
        assertEquals("AView", stack[0])
        assertEquals("BView", stack[1])
    }

    /**
     * If the stack has x different views in the stack, and we want to show the LATEST view again (which is at the top of the stack),
     * then if we return the stack we should see the stack unchanged.
     */
    @Test
    fun `re-uses last stack item and maintains stack`() {
        /** Instantiate with default view **/
        backstack.goTo(AView())

        /** Add more views **/
        backstack.goTo(BView())
        backstack.goTo(CView())

        /** Re-use the LAST view **/
        backstack.goTo(CView())

        /** Check stack still has 3 views **/
        val stack = backstack.getCurrentViewClasses()
        assertEquals(3, stack.size)
        assertEquals("AView", stack[0])
        assertEquals("BView", stack[1])
        assertEquals("CView", stack[2])
    }


    @Test
    fun `getMostRecentView() keeps working correctly after adding new views`() {
        /** Instantiate with default view **/
        backstack.goTo(AView())

        /** Check 'getMostRecentView()' works **/
        assertEquals(AView::class.java, backstack.getMostRecentView()::class.java)


        /** Add another view **/
        backstack.goTo(BView())

        /** Check 'getMostRecentView()' works **/
        assertEquals(BView::class.java, backstack.getMostRecentView()::class.java)

        /** Add another view **/
        backstack.goTo(CView())

        /** Check 'getMostRecentView()' works **/
        assertEquals(CView::class.java, backstack.getMostRecentView()::class.java)
    }

    @Test
    fun `using goBack() on a full stack removes items until one stack item left`() {
        /** Instantiate with default view **/
        backstack.goTo(AView())

        /** Add more views **/
        backstack.goTo(BView())
        backstack.goTo(CView())

        /** 'goBack()' should remove the latest item from stack **/
        backstack.goBack()
        var stack = backstack.getCurrentViewClasses()
        assertEquals(2, stack.size)
        assertEquals("AView", stack[0])
        assertEquals("BView", stack[1])

        /** 'goBack()' should remove the latest item from stack **/
        backstack.goBack()
        stack = backstack.getCurrentViewClasses()
        assertEquals(1, stack.size)
        assertEquals("AView", stack[0])

        /** 'goBack()' should MAINTAIN the latest item from stack, as it's the only item left **/
        backstack.goBack()
        stack = backstack.getCurrentViewClasses()
        assertEquals(1, stack.size)
        assertEquals("AView", stack[0])
    }

    @Test
    fun `all views apart from new are removed with clearTo()`() {
        /** Instantiate with default view **/
        backstack.goTo(AView())

        /** Add more views **/
        backstack.goTo(BView())
        backstack.goTo(CView())

        /** Clear all views apart from new AView **/
        backstack.clearTo(AView())

        /** Check stack has only AView **/
        val stack = backstack.getCurrentViewClasses()
        assertEquals(1, stack.size)
        assertEquals("AView", stack[0])
    }
}