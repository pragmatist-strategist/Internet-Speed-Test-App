package com.pranav.myapplication

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.pranav.myapplication.Adapter.MyViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_item.view.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class Adapter(private val activity: MainKotlinActivity) : RecyclerView.Adapter<MyViewHolder>() {

    var limit = 8

    private val dataList: MutableList<ProgressionModel>? = ArrayList()

    fun setDataList(position: Int, data: ProgressionModel) {
        if (dataList!!.size <= position) {
            dataList.add(data)
        } else {
            dataList[position] = data
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList!![position])
    }

    override fun getItemCount(): Int {
        if (dataList == null) {
            activity.result.isAllCaps = true
            activity.result.visibility = View.GONE
            activity.resultview.visibility = View.GONE
            activity.retest.visibility = View.GONE
            return 0
        } else if (dataList.size - 1 < limit) {
            activity.result.visibility = View.GONE
            activity.resultview.visibility = View.GONE
            activity.retest.visibility = View.GONE
            return dataList.size
        }
        activity.result.visibility = View.VISIBLE
        activity.resultview.visibility = View.VISIBLE
        activity.retest.visibility = View.VISIBLE
        activity.dspeed.text = avgd(dataList)
        activity.uspeed.text = avgu(dataList)
        return limit
    }

    inner class MyViewHolder(itemView: View) : ViewHolder(itemView) {
        fun bind(progressionModel: ProgressionModel) {
            itemView.tvProgress.text = "" + progressionModel.progressTotal
            itemView.tvDownload.text = "" + progressionModel.downloadSpeed
            itemView.tvUpload.text = "" + progressionModel.uploadSpeed
        }
    }

    fun avgd(a: List<ProgressionModel>): String {
        var sum = BigDecimal.valueOf(0.0)
        for (i in 0 until limit) {
            Log.e("Speed", a[i].toString())
            sum = sum.add(a[i].downloadSpeed.divide(BigDecimal.valueOf(10000.0)))
        }
        return sum.divide(BigDecimal.valueOf(limit.toLong())).setScale(2, RoundingMode.CEILING).toString() + " Mbps"
    }

    fun avgu(a: List<ProgressionModel>): String {
        var sum = BigDecimal.valueOf(0.0)
        for (i in 0 until limit) {
            Log.e("Speed", a[i].toString())
            sum = sum.add(a[i].uploadSpeed.divide(BigDecimal.valueOf(10000.0)))
        }
        return sum.divide(BigDecimal.valueOf(limit.toLong())).setScale(2, RoundingMode.CEILING).toString() + " Mbps"
    }
}