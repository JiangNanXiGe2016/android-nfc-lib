package com.example.reader

import android.nfc.NfcAdapter
import android.nfc.NfcAdapter.ReaderCallback
import android.nfc.Tag
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nfc_support_lib.nfc.NfcRAWController
import com.example.reader.databinding.ActivityNfcRwBinding


class NfcCardActivity : AppCompatActivity(), ReaderCallback {
    private lateinit var nfcAdapter: NfcAdapter
    lateinit var bind: ActivityNfcRwBinding

    companion object {
        const val TAG: String = "nfc-test"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityNfcRwBinding.inflate(layoutInflater)
        setContentView(bind.root)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter.enableReaderMode(
            this, this, NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK, null
        )
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter.disableReaderMode(this)
    }

    override fun onTagDiscovered(tag: Tag?) {
        //TAG: Tech [android.nfc.tech.IsoDep, android.nfc.tech.NfcA]
        var controller: NfcRAWController = NfcRAWController()
        tag?.let {
            controller.readCardInfo(it)
        }

    }
}