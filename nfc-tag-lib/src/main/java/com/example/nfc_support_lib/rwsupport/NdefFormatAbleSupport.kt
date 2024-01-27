package com.example.nfc_support_lib.rwsupport

import android.nfc.tech.NdefFormatable
import android.util.Log
import com.example.nfc_support_lib.util.encodeToHexStr

class NdefFormatAbleSupport(private val deformable: NdefFormatable) : NfcInterfaceSupport {
    var id: String? = null
    var atqa1: String? = null
    var sak: Short? = null
    override fun connect() = deformable.connect()

    override fun disConnect() = deformable.close()

    override fun isConnect(): Boolean = deformable.isConnected

    override fun getID(): String = deformable.tag.id.encodeToHexStr()
    override fun getTechs(): Array<out String> = deformable.tag.techList


    fun parseCard() {
        connect()
        //section block
        if (!isConnect()) {
            Log.i(NfcInterfaceSupport.tag, "NdefFormatAbleSupport  not connect")
            return
        }
        //id
        id = getID()
        Log.i(NfcInterfaceSupport.tag, "NdefFormatAbleSupport  id:$id")
    }
}