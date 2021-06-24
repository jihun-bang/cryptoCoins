package jihun.bang.cryptocoins

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import jihun.bang.cryptocoins.databinding.ActivityMainBinding
import jihun.bang.cryptocoins.views.*

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater).apply { setContentView(this.root) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(ExchangeFragment())

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_menu_exchange -> {
                    replaceFragment(ExchangeFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.item_menu_service -> {
                    replaceFragment(ServiceFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.item_menu_asset -> {
                    replaceFragment(AssetFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.item_menu_deposit_withdraw -> {
                    replaceFragment(DepositWithdrawFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.item_menu_more -> {
                    replaceFragment(MoreFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().let {
            it.replace(binding.fragmentLayout.id, fragment)
            it.commit()
        }
    }
}