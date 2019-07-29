package com.pnr.demoapp.ui.screens.countryinfo.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.pnr.demoapp.R
import com.pnr.demoapp.application.DemoApp
import com.pnr.demoapp.base.BaseFragment
import com.pnr.demoapp.model.InfoEntry
import com.pnr.demoapp.ui.adapters.CountryInfoAdapter
import com.pnr.demoapp.ui.screens.countryinfo.viewmodels.CountryInfoViewModel
import com.pnr.demoapp.ui.screens.countryinfo.viewmodels.ViewModelFactory
import com.pnr.demoapp.ui.screens.imageviewer.activities.ImageViewerActivity
import com.pnr.demoapp.util.app.constants.AppConstants
import javax.inject.Inject

/**
 * CountryInfoListFragment
 */
class CountryInfoListFragment : BaseFragment() {

    @BindView(R.id.swipe_layout_information)
    lateinit var mRefreshFeed: SwipeRefreshLayout

    @BindView(R.id.rv_country_info)
    lateinit var recyclerView: RecyclerView

    @BindView(R.id.data_loading_progress)
    lateinit var progressBar: ProgressBar

    @BindView(R.id.text_loading_error)
    lateinit var errorMessage: AppCompatTextView

    private lateinit var countryInfoViewModel: CountryInfoViewModel

    private lateinit var countryInfoAdapter: CountryInfoAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onResume() {
        super.onResume()
        mRefreshFeed.setOnRefreshListener { countryInfoViewModel.loadData(true) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_country_info, container, false)
        ButterKnife.bind(this, view)
        (activity?.application as DemoApp).getApplicationComponent().inject(this)
        countryInfoViewModel = ViewModelProviders.of(this, viewModelFactory).get(CountryInfoViewModel::class.java)
        initRecyclerView()
        showProgressView()
        countryInfoViewModel.loadData(false).observe(this, androidx.lifecycle.Observer { t ->
            mRefreshFeed.isRefreshing = false
            t?.let {
                if (t.rows.isNotEmpty()) {
                    //removing entries where all 3 properties are null
                    val filteredData = t.rows.filter { !(it.title == null && it.description == null && it.imageHref == null) }
                    countryInfoAdapter.updateData(filteredData)
                    (activity as AppCompatActivity).supportActionBar?.title = t.title
                    showDataView()
                } else {
                    showErrorView()
                }
            } ?: run {
                showErrorView()
            }
        })
        return view
    }

    /**
     * function to init RecyclerView
     */
    private fun initRecyclerView() {
        countryInfoAdapter =
            CountryInfoAdapter(ArrayList(), { countryInfoEntry: InfoEntry -> viewImageFullScreen(countryInfoEntry) })
        recyclerView.setHasFixedSize(true)
        //to improve recyclerview scroll experience
        recyclerView.setItemViewCacheSize(20)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = countryInfoAdapter
    }

    /**
     * function to launch full screen image
     */
    private fun viewImageFullScreen(countryInfoEntry: InfoEntry) {
        val intent = Intent(context, ImageViewerActivity::class.java)
        intent.putExtra(AppConstants.IMAGE_URL_KEY, countryInfoEntry.imageHref)
        startActivity(intent)
    }

    /**
     * function to display progress dialog
     */
    private fun showProgressView() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        errorMessage.visibility = View.GONE
    }

    /**
     * function to display error view
     */
    private fun showErrorView() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        errorMessage.visibility = View.VISIBLE
    }

    /**
     * function to display data view
     */
    private fun showDataView() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        errorMessage.visibility = View.GONE
    }
}