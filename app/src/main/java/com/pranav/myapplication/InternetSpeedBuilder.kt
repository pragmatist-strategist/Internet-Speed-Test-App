package com.pranav.myapplication

import android.app.Activity
import android.os.AsyncTask
import android.util.Log
import fr.bmartel.speedtest.SpeedTestReport
import fr.bmartel.speedtest.SpeedTestSocket
import fr.bmartel.speedtest.inter.ISpeedTestListener
import fr.bmartel.speedtest.model.SpeedTestError


class InternetSpeedBuilder(var activity: Activity) {

    private var countTestSpeed = 0
    private var LIMIT = 10
    lateinit var url: String
    lateinit var javaListener: OnEventInternetSpeedListener
    lateinit var onDownloadProgressListener: () -> Unit
    lateinit var onUploadProgressListener: () -> Unit
    lateinit var onTotalProgressListener: () -> Unit
    private lateinit var progressModel: ProgressionModel
/*
Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.*/

    fun start(url: String, limitCount: Int) {
        this.url = url
        this.LIMIT = limitCount
        startTestDownload()
    }

    fun setOnEventInternetSpeedListener(javaListener: OnEventInternetSpeedListener) {
        this.javaListener = javaListener
    }

    fun setOnEventInternetSpeedListener(onDownloadProgress: () -> Unit, onUploadProgress: () -> Unit, onTotalProgress: () -> Unit) {
        this.onDownloadProgressListener = onDownloadProgress
        this.onUploadProgressListener = onUploadProgress
        this.onTotalProgressListener = onTotalProgress
    }


    private fun startTestDownload() {
        progressModel = ProgressionModel()
        SpeedDownloadTestTask().execute()
    }

    private fun startTestUpload() {
        SpeedUploadTestTask().execute()

    }

    interface OnEventInternetSpeedListener {
        fun onDownloadProgress(count: Int, progressModel: ProgressionModel)
        fun onUploadProgress(count: Int, progressModel: ProgressionModel)
        fun onTotalProgress(count: Int, progressModel: ProgressionModel)
    }

    inner class SpeedDownloadTestTask : AsyncTask<Void, Void, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: Void): String? {

            val speedTestSocket = SpeedTestSocket()

            // add a listener to wait for speedtest completion and progress
            speedTestSocket.addSpeedTestListener(object : ISpeedTestListener {

                override fun onCompletion(report: SpeedTestReport) {
                    // called when download/upload is finished
                    Log.v("speedtest Download" + countTestSpeed, "[COMPLETED] rate in octet/s : " + report.transferRateOctet)
                    Log.v("speedtest Download" + countTestSpeed, "[COMPLETED] rate in bit/s   : " + report.transferRateBit)

                    startTestUpload()

                }

                override fun onError(speedTestError: SpeedTestError, errorMessage: String) {
                    // called when a download/upload error occur
                }

                override fun onProgress(percent: Float, report: SpeedTestReport) {
                    // called to notify download/upload progress
                    Log.v("speedtest Download" + countTestSpeed, "[PROGRESS] progress : $percent%")
                    Log.v("speedtest Download" + countTestSpeed, "[PROGRESS] rate in octet/s : " + report.transferRateOctet)
                    Log.v("speedtest Download" + countTestSpeed, "[PROGRESS] rate in bit/s   : " + report.transferRateBit)

                    progressModel.progressTotal = percent / 2
                    progressModel.progressDownload = percent
                    progressModel.downloadSpeed = report.transferRateBit
                    //Log.e("download",progressModel.downloadSpeed.toString());
                    //MainJavaActivity.down.add(progressModel.downloadSpeed)

                    activity.runOnUiThread {
                        javaListener.onDownloadProgress(countTestSpeed, progressModel)
                        javaListener.onTotalProgress(countTestSpeed, progressModel)
                    }

                }
            })

            speedTestSocket.startDownload(url)

            return null
        }
    }

    inner class SpeedUploadTestTask : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void): Void? {

            val speedTestSocket = SpeedTestSocket()

            // add a listener to wait for speedtest completion and progress
            speedTestSocket.addSpeedTestListener(object : ISpeedTestListener {

                override fun onCompletion(report: SpeedTestReport) {
                    // called when download/upload is finished
                    Log.v("speedtest Upload" + countTestSpeed, "[COMPLETED] rate in octet/s : " + report.transferRateOctet)
                    Log.v("speedtest Upload" + countTestSpeed, "[COMPLETED] rate in bit/s   : " + report.transferRateBit)

                    /*progressModel.progressTotal = 100f
                    progressModel.progressUpload= 100f
                    progressModel.uploadSpeed = report.transferRateBit

                    activity.runOnUiThread {
                        javaListener.onUploadProgress(countTestSpeed, progressModel)
                        javaListener.onTotalProgress(countTestSpeed, progressModel)
                    }*/


                    countTestSpeed++
                    if (countTestSpeed < LIMIT) {
                        startTestDownload()
                    }
                }

                override fun onError(speedTestError: SpeedTestError, errorMessage: String) {
                    // called when a download/upload error occur
                }

                override fun onProgress(percent: Float, report: SpeedTestReport) {
                    // called to notify download/upload progress
                    Log.v("speedtest Upload" + countTestSpeed, "[PROGRESS] progress : $percent%")
                    Log.v("speedtest Upload" + countTestSpeed, "[PROGRESS] rate in octet/s : " + report.transferRateOctet)
                    Log.v("speedtest Upload" + countTestSpeed, "[PROGRESS] rate in bit/s   : " + report.transferRateBit)

                    progressModel.progressTotal = percent / 2 + 50
                    progressModel.progressUpload = percent
                    progressModel.uploadSpeed = report.transferRateBit
                    //Log.e("upload",progressModel.uploadSpeed.toString());
                    //MainJavaActivity.up.add(progressModel.uploadSpeed)
                    //This is a list of test changes for pscse
                    //Hope this makes a great impact
                    activity.runOnUiThread {

                        if (countTestSpeed < LIMIT) {
                            javaListener.onUploadProgress(countTestSpeed, progressModel)
                            javaListener.onTotalProgress(countTestSpeed, progressModel)

                        }
                    }

                }
            })

            speedTestSocket.startDownload(url)

            return null
        }
    }
}
