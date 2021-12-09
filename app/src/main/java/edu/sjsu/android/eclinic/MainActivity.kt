package edu.sjsu.android.eclinic

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import edu.sjsu.android.eclinic.databinding.ActivityMainBinding
import np.com.susanthapa.curved_bottom_navigation.CbnMenuItem

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = navController()
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_appointment,
                R.id.nav_history,
                R.id.nav_profile,
                R.id.nav_settings,
            ), binding.drawerLayout
        )
        binding.navView.setupWithNavController(navController)
        setupBottomNavigation()

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.nav_doctor_details) {
                binding.bottomNavigation.visibility = View.GONE
            } else {
                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }
    }

    private fun setupBottomNavigation() {
        val menuItems = arrayOf(
            CbnMenuItem(
                R.drawable.ic_notification, // the icon
                R.drawable.avd_notification, // the AVD that will be shown in FAB
                R.id.nav_appointment // optional if you use Jetpack Navigation
            ),
            CbnMenuItem(
                R.drawable.ic_dashboard,
                R.drawable.avd_dashboard,
                R.id.nav_history
            ),
            CbnMenuItem(
                R.drawable.ic_home,
                R.drawable.avd_home,
                R.id.nav_home
            ),
            CbnMenuItem(
                R.drawable.ic_profile,
                R.drawable.avd_profile,
                R.id.nav_profile
            ),
            CbnMenuItem(
                R.drawable.ic_settings,
                R.drawable.avd_settings,
                R.id.nav_settings
            )
        )
        binding.bottomNavigation.setMenuItems(menuItems, 2)
        binding.bottomNavigation.setupWithNavController(navController())
    }

    private fun navController() =
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    fun setToolbar(toolbar: Toolbar) {
        toolbar.setupWithNavController(navController(), appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController().navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}