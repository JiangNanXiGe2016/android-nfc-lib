package com.example.nfc_support_lib.util

import android.nfc.NfcAdapter
import com.example.nfc_support_lib.NfcTechAdapter
import com.example.nfc_support_lib.rwsupport.IsoDepSupport
import com.example.nfc_support_lib.rwsupport.NfcASupport
import com.example.nfc_support_lib.constant.NFC_A_CMD_FAIL
import com.example.nfc_support_lib.constant.NFC_A_CMD_NOT_SUPPORT
import com.example.nfc_support_lib.constant.NFC_A_COMMON_ERROR
import com.example.nfc_support_lib.constant.NFC_A_PARAMS_WRONG
import com.example.nfc_support_lib.constant.NFC_A_SUCCESS
import com.example.nfc_support_lib.constant.NFC_A_SUCCESS_PRAMS
import com.example.nfc_support_lib.constant.NFC_A_TAG_NO_RES
import com.example.nfc_support_lib.constant.SW_APPLET_SELECT_FAILED
import com.example.nfc_support_lib.constant.SW_BYTES_REMAINING_00
import com.example.nfc_support_lib.constant.SW_CLA_NOT_SUPPORTED
import com.example.nfc_support_lib.constant.SW_COMMAND_NOT_ALLOWED
import com.example.nfc_support_lib.constant.SW_CONDITIONS_NOT_SATISFIED
import com.example.nfc_support_lib.constant.SW_CORRECT_LENGTH_00
import com.example.nfc_support_lib.constant.SW_DATA_INVALID
import com.example.nfc_support_lib.constant.SW_FILE_FULL
import com.example.nfc_support_lib.constant.SW_FILE_INVALID
import com.example.nfc_support_lib.constant.SW_FILE_NOT_FOUND
import com.example.nfc_support_lib.constant.SW_FUNC_NOT_SUPPORTED
import com.example.nfc_support_lib.constant.SW_INCORRECT_P1P2
import com.example.nfc_support_lib.constant.SW_INS_NOT_SUPPORTED
import com.example.nfc_support_lib.constant.SW_NO_ERROR
import com.example.nfc_support_lib.constant.SW_RECORD_NOT_FOUND
import com.example.nfc_support_lib.constant.SW_SECURITY_STATUS_NOT_SATISFIED
import com.example.nfc_support_lib.constant.SW_UNKNOWN
import com.example.nfc_support_lib.constant.SW_WRONG_DATA
import com.example.nfc_support_lib.constant.SW_WRONG_LENGTH
import com.example.nfc_support_lib.constant.SW_WRONG_P1P2


fun ByteArray.encodeToHexStr(): String {
    return joinToString("") { "%02x".format(it) }
}

fun String.decodeHexStrToArray(): ByteArray {
    val len = length / 2 // 每两位表示一个字节
    val byteArray = ByteArray(len)
    for (i in 0 until len) {
        val index = i * 2
        val highNibble = Character.digit(this[index], 16).toInt() shl 4
        val lowNibble = Character.digit(this[index + 1], 16).toInt()
        byteArray[i] = (highNibble or lowNibble).toByte()
    }
    return byteArray
}


/**
 * 处理isoDep结果
 *
 * **/
fun IsoDepSupport.checkResult(ret: String): String = when (ret) {
    SW_NO_ERROR -> "success"
    SW_BYTES_REMAINING_00 -> "bytes remaining"
    SW_WRONG_LENGTH -> "wrong size"
    SW_SECURITY_STATUS_NOT_SATISFIED -> "security status not satisfied"
    SW_FILE_INVALID -> "file invalid"
    SW_DATA_INVALID -> "data invalid"
    SW_CONDITIONS_NOT_SATISFIED -> "condition not satisfied"
    SW_COMMAND_NOT_ALLOWED -> "cmd not allowed"
    SW_APPLET_SELECT_FAILED -> "app select fail"
    SW_WRONG_DATA -> "wrong data"
    SW_FUNC_NOT_SUPPORTED -> "function  not support"
    SW_FILE_NOT_FOUND -> "file not found"
    SW_RECORD_NOT_FOUND -> "record not found"
    SW_INCORRECT_P1P2 -> "incorrect p1p2"
    SW_WRONG_P1P2 -> "wrong p1p2"
    SW_CORRECT_LENGTH_00 -> "correct size 00"
    SW_INS_NOT_SUPPORTED -> "ins not support"
    SW_CLA_NOT_SUPPORTED -> "cla not support"
    SW_UNKNOWN -> "unknown error"
    SW_FILE_FULL -> "file full"
    else -> "other error"
}

/**
 * 处理nfcA结果
 *
 * **/
fun NfcASupport.checkResult(ret: String): String = when (ret) {
    NFC_A_SUCCESS -> "success"
    NFC_A_SUCCESS_PRAMS -> "success"
    NFC_A_COMMON_ERROR -> "cmd error"
    NFC_A_CMD_NOT_SUPPORT -> "cmd not support"
    NFC_A_PARAMS_WRONG -> "param wrong"
    NFC_A_TAG_NO_RES -> "no response"
    NFC_A_CMD_FAIL -> "fail"
    else -> " not define"


}



