package technited.minds.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.test.databinding.ActivityMainBinding
import technited.minds.test.db.HealthDatabase
import technited.minds.test.repository.HealthRepo
import technited.minds.test.utils.factory.viewModelFactory

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val healthRepository by lazy { HealthRepo(HealthDatabase(this)) }
    private val viewModel: ActivitiesViewModel by viewModels {
        viewModelFactory { ActivitiesViewModel(this.application, healthRepository) }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_HealthWellnessGamification)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbar)

        /**
         * Just so the viewModel doesn't get removed by the compiler, as it isn't used
         * anywhere here for now
         */
        viewModel

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment?
                ?: return

        with(navHostFragment.navController) {
            appBarConfiguration = AppBarConfiguration(graph)
            setupActionBarWithNavController(this, appBarConfiguration)
        }


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_activities, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        navHostFragment.navController.navigateUp()
        return super.onSupportNavigateUp()
    }
}