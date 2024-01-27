package com.example.nfc_support_lib.rwsupport

import android.nfc.tech.NfcF
import com.example.nfc_support_lib.util.encodeToHexStr

class NfcFSupport(private val nfcF: NfcF): NfcInterfaceSupport {
    override fun connect() = nfcF.connect()

    override fun disConnect() = nfcF.close()

    override fun isConnect(): Boolean = nfcF.isConnected

    override fun getID(): String = nfcF.tag.id.encodeToHexStr()

    override fun getTechs(): Array<out String> = nfcF.tag.techList
}