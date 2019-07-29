package com.pnr.demoapp.ui.screens.countryinfo.activities

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.pnr.demoapp.R
import com.pnr.demoapp.base.BaseActivity
import com.pnr.demoapp.ui.screens.countryinfo.fragments.CountryInfoListFragment

/**
 * CountryInfoActivity
 */
class CountryInfoActivity : BaseActivity() {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_info)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)
        displayCountryInfoFeed(savedInstanceState)
    }

    /**
     * function to display country info feed data
     */
    private fun displayCountryInfoFeed(bundle: Bundle?) {
        val countryInfoListFragment: CountryInfoListFragment
        if (bundle == null) {
            countryInfoListFragment = CountryInfoListFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.content_layout, countryInfoListFragment, "CountryInfoListFragment").commit()
        }
    }
}