package com.pranav.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pranav.myapplication.InternetSpeedBuilder.OnEventInternetSpeedListener

class MainKotlinActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter

    companion object {
        lateinit var resultlayout: LinearLayout
        lateinit var retest: Button

        lateinit var dspeed: TextView
        lateinit var uspeed: TextView
        lateinit var results: TextView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        recyclerView = findViewById(R.id.recyclerview)
        dspeed = findViewById(R.id.dspeed)
        uspeed = findViewById(R.id.uspeed)
        results = findViewById(R.id.result)
        resultlayout = findViewById(R.id.resultview)
        retest = findViewById(R.id.retest)

        results.visibility = View.GONE
        resultlayout.visibility = View.GONE
        retest.visibility = View.GONE

        adapter = Adapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        val builder = InternetSpeedBuilder(this)

        builder.setOnEventInternetSpeedListener(object : OnEventInternetSpeedListener {
            override fun onDownloadProgress(count: Int, progressModel: ProgressionModel) {}
            override fun onUploadProgress(count: Int, progressModel: ProgressionModel) {}
            override fun onTotalProgress(count: Int, progressModel: ProgressionModel) {
                adapter.setDataList(count, progressModel)
            }
        })

        builder.start("http://www.speedtest.net/mini.php", 20)

        retest.setOnClickListener(View.OnClickListener {
            finish()
            startActivity(Intent(applicationContext, MainKotlinActivity::class.java))
        })
    }
}
