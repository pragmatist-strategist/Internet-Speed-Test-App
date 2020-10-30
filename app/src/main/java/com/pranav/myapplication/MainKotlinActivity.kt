package com.pranav.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.pranav.myapplication.InternetSpeedBuilder.OnEventInternetSpeedListener
import kotlinx.android.synthetic.main.activity_main.*

class MainKotlinActivity : AppCompatActivity() {

    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        result.visibility = View.GONE
        resultview.visibility = View.GONE
        retest.visibility = View.GONE

        adapter = Adapter()
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter

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
