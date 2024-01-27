package com.example.nfc_support_lib.rwsupport

import android.nfc.tech.MifareUltralight
import com.example.nfc_support_lib.util.encodeToHexStr

class MifareUltralightSupport(val mu: MifareUltralight) : NfcInterfaceSupport {
    override fun connect() = mu.connect()

    override fun disConnect() = mu.close()

    override fun isConnect(): Boolean = mu.isConnected

    override fun getID(): String = mu.tag.id.encodeToHexStr()

    override fun getTechs(): Array<out String> = mu.tag.techList
}