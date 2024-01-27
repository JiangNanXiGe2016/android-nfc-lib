package com.example.nfc_support_lib

import android.nfc.NfcAdapter
import android.nfc.Tag
import com.example.nfc_support_lib.rwsupport.NfcInterfaceSupport

class NfcTechAdapter {
    private constructor()

    fun NfcAdapter.adapter(): NfcTechAdapter = NfcTechAdapter()
    fun rwModeAdapter(tag: Tag): NfcInterfaceSupport? {
        return null
    }

    fun p2pMode() {}
    fun emulatorMoe() {}

}