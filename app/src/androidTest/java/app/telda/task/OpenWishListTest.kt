package app.telda.task


import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import app.telda.task.ui.main.MainActivity
import app.telda.task.ui.main.list.MoviesListFragment
import app.telda.task.ui.main.wishlist.WishlistFragment
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class OpenWishListTest : TestCase(){

    @get:Rule
    var rule = ActivityTestRule(MainActivity::class.java)


    @Test
    fun testSuccessNavigationToWishListScreen() {
        val navController = TestNavHostController(getApplicationContext())
        launchFragment(navController,R.id.moviesListFragment)

        launchFragmentInHiltContainer<MoviesListFragment>()

        val activity = rule.activity

        Log.i("test test",activity.toString())

        activity.runOnUiThread{
            activity!!.findViewById<AppCompatImageView>(R.id.img_wishlist)!!.performClick()
            Log.i("test test",navController.currentDestination?.id.toString() +" " + R.id.wishlistFragment+" " + R.id.moviesListFragment)
            object: CountDownTimer(100, 100) {
                override fun onTick(millisUntilFinished: Long) {}
                override fun onFinish() {
                    assert(navController.currentDestination?.id == R.id.wishlistFragment)

                }
            }.start()
        }


    }

    private fun launchFragment(navController: TestNavHostController,nav:Int) {
        launchFragmentInHiltContainer<WishlistFragment>(null, R.style.Theme_TeldaTask) {
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(nav)
            Navigation.setViewNavController(requireView(), navController)
        }
    }




}