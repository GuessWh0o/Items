package com.guesswho.items.screens.first_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.guesswho.items.R
import com.guesswho.items.adapter.CarListAdapter
import com.guesswho.items.adapter.IOnOptionClicked
import com.guesswho.items.model.Car
import com.guesswho.items.model.Event
import com.guesswho.items.model.ViewState
import com.guesswho.items.view.LoadingButtonView
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_first.view.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

class FirstFragment : Fragment(), IOnOptionClicked {
    private val viewModel: FirstViewModel by viewModels()

    private lateinit var loadingButton: LoadingButtonView
    private val adapter = CarListAdapter(arrayListOf(), this)

    private val observer = Observer<List<Car>> {
        adapter.updateList(it)
    }

    private val viewStateObserver = Observer<ViewState> { state ->
        when (state) {
            ViewState.LOADING -> loadingButton.animateButton()
            ViewState.LOADED -> {
                swipe_refresh.isRefreshing = false
                loadingButton.loadingCompleted()
                loadingButton.setOnClickListener {
                    viewModel.deleteAll()
                }
            }
            ViewState.EMPTY -> {
                swipe_refresh.isRefreshing = false
                loadingButton.loadingCompletedNoData()
            }
            else -> {
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_first, container, false)

        rootView.rv_rate_list.layoutManager = LinearLayoutManager(activity)
        rootView.rv_rate_list.adapter = adapter
        loadingButton = rootView.findViewById(R.id.btn_loading)
        rootView.fab_add.setOnClickListener {
            viewModel.addCar(Car(brand = "NEW CAR", horsePower = 1321312))
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipe_refresh.setOnRefreshListener {
            viewModel.fetch()
        }

        viewModel.states.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.data.observe(viewLifecycleOwner, observer)
        viewModel.fetch()
    }

    override fun onClick(event: Event, car: Car) {
        when (event) {
            Event.DELETE -> viewModel.deleteCar(car)
            Event.EDIT -> Toast.makeText(activity, "EDIT", Toast.LENGTH_SHORT).show()//viewModel
        }
    }
}
