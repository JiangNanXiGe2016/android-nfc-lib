package com.example.emulator

import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import com.example.nfc_support_lib.util.encodeToHexStr

class HostCardEmulatorService : HostApduService() {


    companion object {
        val TAG = "HostCardEmulatorService"
        val STATUS_SUCCESS = "9000"
        val STATUS_FAILED = "6F00"
        val CLA_NOT_SUPPORTED = "6E00"
        val INS_NOT_SUPPORTED = "6D00"
        val AID = "A0000002471001"
        val SELECT_INS = "A4"
        val DEFAULT_CLA = "00"
        val MIN_APDU_LENGTH = 12
        lateinit var activity: MainActivity
        fun act(activity: MainActivity) {
            this.activity = activity
        }
    }

    override fun onDeactivated(reason: Int) {
        activity.i(TAG, "onDeactivated :$reason")
    }

    override fun processCommandApdu(commandApdu: ByteArray?, extras: Bundle?): ByteArray {
        activity.i(TAG, "arrival:${commandApdu?.encodeToHexStr()}")
        var ret = Utils.hexStringToByteArray(STATUS_SUCCESS)
        activity.i(TAG, "send :ret=${ret?.encodeToHexStr()}")
        return ret
    }
}