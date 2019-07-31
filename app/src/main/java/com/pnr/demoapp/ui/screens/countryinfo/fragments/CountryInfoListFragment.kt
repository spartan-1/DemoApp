package com.pnr.demoapp.ui.screens.countryinfo.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
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
import com.pnr.demoapp.model.CountryInfoResponse
import com.pnr.demoapp.model.InfoEntry
import com.pnr.demoapp.ui.adapters.CountryInfoAdapter
import com.pnr.demoapp.ui.screens.countryinfo.viewmodels.CountryInfoViewModel
import com.pnr.demoapp.ui.screens.countryinfo.viewmodels.ViewModelFactory
import com.pnr.demoapp.ui.screens.imageviewer.activities.ImageViewerActivity
import com.pnr.demoapp.util.app.constants.AppConstants
import com.pnr.demoapp.util.app.constants.ErrorType
import com.pnr.demoapp.util.customviews.CustomRecyclerView
import com.pnr.demoapp.util.customviews.CustomScrollListener
import javax.inject.Inject

/**
 * CountryInfoListFragment
 */
class CountryInfoListFragment : BaseFragment() {

    @BindView(R.id.swipe_layout_information)
    lateinit var mRefreshFeed: SwipeRefreshLayout

    @BindView(R.id.rv_country_info)
    lateinit var recyclerView: CustomRecyclerView

    @BindView(R.id.data_loading_progress)
    lateinit var progressBar: ProgressBar

    @BindView(R.id.text_loading_error)
    lateinit var errorMessage: AppCompatTextView

    @BindView(R.id.loadmore_progress)
    lateinit var loadMoreProgress: ProgressBar

    @BindView(R.id.loadmore_errorlayout)
    lateinit var scrollLoadError: RelativeLayout


    private lateinit var countryInfoViewModel: CountryInfoViewModel

    private lateinit var countryInfoAdapter: CountryInfoAdapter

    private var refreshScreenRequired = false

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    /**
     * function onResume
     */
    override fun onResume() {
        super.onResume()
        mRefreshFeed.setOnRefreshListener {
            refreshScreenRequired = true
            loadDataFromVM(refreshRequired = true, infiniteScroll = false)
        }
    }

    /**
     * countryInfoObserver
     */
    private val countryInfoObserver = androidx.lifecycle.Observer<CountryInfoResponse> { t ->
        mRefreshFeed.isRefreshing = false
        t?.let {
            updateData(t)
        } ?: run {
            updateData(null)
        }
    }

    /**
     * function onCreateView
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_country_info, container, false)
        ButterKnife.bind(this, view)
        (activity?.application as DemoApp).getApplicationComponent().inject(this)
        countryInfoViewModel = ViewModelProviders.of(this, viewModelFactory).get(CountryInfoViewModel::class.java)
        initRecyclerView()
        showProgressView()
        loadDataFromVM(refreshRequired = false, infiniteScroll = false)
        // retry option to load when endless scroll failed
        scrollLoadError.setOnClickListener {
            loadMoreData()
        }
        return view
    }

    /**
     * function to process and update response
     */
    private fun updateData(values: CountryInfoResponse?) {
        values?.countryInfo?.let {
            clearEndlessScrollProgress()
            refreshScreenRequired = false
            //clearing the entries where all 3 properties are null
            val filteredData =
                values.countryInfo.rows.filter { !(it.title == null && it.description == null && it.imageHref == null) }
            countryInfoAdapter.updateData(filteredData)
            (activity as AppCompatActivity).supportActionBar?.title = values.countryInfo.title
            showDataView()
            if (values.errorStatus == ErrorType.API_FAILURE) {
                showEndlessScrollError()
            }
        } ?: run {
            handleErrorUIScenarios()
        }
    }

    private fun handleErrorUIScenarios() {
        when {
            refreshScreenRequired -> {
                refreshScreenRequired = false
                countryInfoAdapter.clearData()
                showErrorView()
            }
            //This is to keep screen/list state screen/rotation
            countryInfoAdapter.itemCount != 0 -> showEndlessScrollError()
            else -> showErrorView()
        }
    }

    /**
     * function to load data when the end of recycler view reached
     */
    fun loadMoreData() {
        showEndlessScrollProgress()
        loadDataFromVM(refreshRequired = false, infiniteScroll = true)
    }

    /**
     * utility function to load data from VM
     */
    private fun loadDataFromVM(refreshRequired: Boolean, infiniteScroll: Boolean) {
        countryInfoViewModel.loadData(refreshRequired = refreshRequired, infiniteScroll = infiniteScroll)
            .observe(this, countryInfoObserver)
    }

    /**
     * Listener to check if end of recycler view is reached
     */
    private fun mScrollListener(manager: LinearLayoutManager) = object : CustomScrollListener(manager) {
        override fun onLoadMoreData(page: Int, totalItemsCount: Int, view: RecyclerView) {
            loadMoreData()
        }
    }

    /**
     * function to init RecyclerView
     */
    private fun initRecyclerView() {
        countryInfoAdapter =
            CountryInfoAdapter(ArrayList()) { countryInfoEntry: InfoEntry -> viewImageFullScreen(countryInfoEntry) }
        recyclerView.setHasFixedSize(true)
        //to improve recyclerview scroll experience
        recyclerView.setItemViewCacheSize(20)
        val manager = LinearLayoutManager(context)
        recyclerView.layoutManager = manager
        recyclerView.adapter = countryInfoAdapter
        recyclerView.addOnScrollListener(mScrollListener(manager))
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

    /**
     * function to display scroll more data error
     */
    private fun showEndlessScrollError() {
        scrollLoadError.visibility = View.VISIBLE
        loadMoreProgress.visibility = View.GONE
    }

    /**
     * function to display scroll more progress
     */
    private fun showEndlessScrollProgress() {
        scrollLoadError.visibility = View.GONE
        loadMoreProgress.visibility = View.VISIBLE
    }

    /**
     * function to clear scroll more progress
     */
    private fun clearEndlessScrollProgress() {
        scrollLoadError.visibility = View.GONE
        loadMoreProgress.visibility = View.GONE
    }
}