package com.example.nfc_support_lib.rwsupport

import android.nfc.tech.NfcA
import android.util.Log
import com.example.nfc_support_lib.rwsupport.NfcInterfaceSupport.Companion.tag
import com.example.nfc_support_lib.util.checkResult
import com.example.nfc_support_lib.util.decodeHexStrToArray
import com.example.nfc_support_lib.util.encodeToHexStr
import java.util.Locale

class NfcASupport(private val nfcA: NfcA) : NfcInterfaceSupport {
    var id: String? = null
    var atqa1: String? = null
    var sak: Short? = null
    var data: ByteArray? = null
    fun parseCard() {
        connect()
        //section block
        if (!isConnect()) {
            Log.i(tag, "NfcACard  not connect")
            return
        }

        //id
        id = getID()
        Log.i(tag, "NfcACard  id:$id")
        //atqa1
        atqa1 = getAtqa()
        Log.i(tag, "NfcACard  atqa1:$atqa1")
        //sak
        sak = getSka()
        Log.i(tag, "NfcACard  sak:$sak")
//        reQaCmd()
//        wakUpCmd()
//        selectApp()
//        selectCmd()
//        ratsCmd()
//        readCmd()
//        writeCmd()
    }

    private fun selectApp() {
        val command = byteArrayOf(0x5A.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte())
        Log.i(tag, "cmd =${command.encodeToHexStr()}")
        if (!isConnect()) {
            Log.i(tag, "connect fail")
            return
        }
        var ret: ByteArray = trans(command)
        var retStr = ret.encodeToHexStr().uppercase(Locale.ROOT)
        var error: String? = null
        ret?.let {
            if (it.size >= 2) {
                error = checkResult(byteArrayOf(it[0]).encodeToHexStr())
            }
        }
        Log.i(
            tag,
            "cmd=${command.encodeToHexStr()} ret=${ret.contentToString()}  retStr=${retStr}  $error"
        )
    }


    private fun reQaCmd() {
        Log.d(tag, "NfcACard reQaCmd")
        val cmd = byteArrayOf(0x26.toByte())
        val ret = trans(cmd)
        var error: String? = null
        ret?.let {
            if (it.size >= 2) {
                error = checkResult(byteArrayOf(it[0]).encodeToHexStr())
            }
        }
        Log.d(
            tag,
            "NfcACard  cmd:  ${cmd.encodeToHexStr()} retArray:${ret.contentToString()} ret: ${ret.encodeToHexStr()}  error=$error"
        )
    }

    private fun wakUpCmd() {
        Log.d(tag, "NfcACard wakUpCmd")
        val cmd = byteArrayOf(0x52.toByte())
        val ret = trans(cmd)
        var error: String? = null
        ret?.let {
            if (it.size >= 2) {
                error = checkResult(byteArrayOf(it[0]).encodeToHexStr())
            }
        }
        Log.d(
            tag,
            "NfcACard  cmd:  ${cmd.encodeToHexStr()} retArray:${ret.contentToString()} ret: ${ret.encodeToHexStr()}  error=$error"
        )

    }

    private fun selectCmd() {
        Log.d(tag, "NfcACard selectCmd")
        val cmd = byteArrayOf(
            0x93.toByte(),  // SELECT Command
            0x04.toByte(),  // Length of UID
            0x01.toByte(),  // Cascade Level 1
            0x08.toByte(),  // Cascade Level 2
            0x37.toByte()   // Cascade Level 3
        )
        val ret = trans(cmd)
        var error: String? = null
        ret?.let {
            if (it.size >= 2) {
                error = checkResult(byteArrayOf(it[0]).encodeToHexStr())
            }
        }
        Log.d(
            tag,
            "NfcACard  cmd:  ${cmd.encodeToHexStr()} retArray:${ret.contentToString()} ret: ${ret.encodeToHexStr()}  error=$error"
        )
    }

    private fun ratsCmd() {
        Log.d(tag, "NfcACard ratsCmd")
        val cmd = byteArrayOf(0xE0.toByte(), 0x80.toByte())
        val ret = trans(cmd)
        var error: String? = null
        ret?.let {
            if (it.size >= 2) {
                error = checkResult(byteArrayOf(it[0]).encodeToHexStr())
            }
        }
        Log.d(
            tag,
            "NfcACard  cmd:  ${cmd.encodeToHexStr()} retArray:${ret.contentToString()} ret: ${ret.encodeToHexStr()}  error=$error"
        )
    }

    private fun haltCmd() {

        Log.d(tag, "NfcACard haltCmd")
        val cmd = byteArrayOf(0x50.toByte(), 0x00.toByte())
        val ret = trans(cmd)
        var error: String? = null
        ret?.let {
            if (it.size >= 2) {
                error = checkResult(byteArrayOf(it[0]).encodeToHexStr())
            }
        }
        Log.d(
            tag,
            "NfcACard  cmd:  ${cmd.encodeToHexStr()} retArray:${ret.contentToString()} ret: ${ret.encodeToHexStr()}  error=$error"
        )
    }

    private fun readCmd() {
        Log.d(tag, "NfcACard readCmd")
        val cmd = byteArrayOf(
            0x30.toByte(), 0x05.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x08.toByte()
        )
        val ret = trans(cmd)
        var error: String? = null
        ret?.let {
            if (it.size >= 2) {
                error = checkResult(byteArrayOf(it[0]).encodeToHexStr())
            }
        }
        Log.d(
            tag,
            "NfcACard  cmd:  ${cmd.encodeToHexStr()} retArray:${ret.contentToString()} ret: ${ret.encodeToHexStr()}  error=$error"
        )
    }

    private fun writeCmd() {
        Log.d(tag, "NfcACard writeCmd")
        val cmd =
            byteArrayOf(0xA2.toByte(), 0x04.toByte(), 0x01.toByte(), 0x08.toByte(), 0x37.toByte())
        val ret = trans(cmd)
        var error: String? = null
        ret?.let {
            if (it.size >= 2) {
                error = checkResult(byteArrayOf(it[0]).encodeToHexStr())
            }
        }
        Log.d(
            tag,
            "NfcACard  cmd:  ${cmd.encodeToHexStr()} retArray:${ret.contentToString()} ret: ${ret.encodeToHexStr()}  error=$error"
        )
    }

    override fun connect() = nfcA.connect()

    override fun disConnect() = nfcA.close()

    override fun isConnect() = nfcA.isConnected

    override fun getID() = nfcA.tag.id.encodeToHexStr()

    private fun getSka() = nfcA.sak

    private fun getAtqa() = nfcA.atqa.encodeToHexStr()

    override fun getTechs(): Array<out String> = nfcA.tag.techList
    private fun trans(cmd: ByteArray): ByteArray = nfcA.transceive(cmd)

}