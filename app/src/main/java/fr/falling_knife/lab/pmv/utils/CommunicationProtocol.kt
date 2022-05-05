package fr.falling_knife.lab.pmv.utils

import org.json.JSONObject
import org.json.JSONTokener

enum class RequestType{
    BUTTON,
    LOGIN,
    USER_SELECT
}

class CommunicationProtocol {

    fun prepareRequest(type: RequestType, datas: ArrayList<String>): String{
        var request: String = "{"+
                """"command": """
        when(type){
            RequestType.LOGIN -> request += """"authCheck",""" +
                    """"data": {""" +
                    """"login": "${datas[0]}",""" +
                    """"pass": "${datas[1]}"""" +
                    "}"
            RequestType.BUTTON -> request += """"btnState",""" +
                    """"data": {""" +
                    """"btnSess": ${datas[0]},""" +
                    """"btnPrep": ${datas[1]},""" +
                    """"btnAVM:" ${datas[2]},""" +
                    """"btnReady": ${datas[3]},""" +
                    """"btnGo": ${datas[4]}""" +
                    "}"
            RequestType.USER_SELECT -> request += """"transfertRunner",""" +
                    """"data": {""" +
                    """"id": ${datas[0]},""" +
                    """"runners": [""" +
                    "${datas[1]}, ${datas[2]}" +
                    "]" +
                    "}"
        }

        request += "}"

        return request
    }

    fun decodeData(data: String): String{
        val jsonObject = JSONTokener(data).nextValue() as JSONObject
        val command = jsonObject.getString("command")
        var response: String = ""

        when(command){
            "authCheck" -> {
                val jsonData = jsonObject.getJSONObject("data")
                val authStatus = jsonData.getInt("success")
                response = if(authStatus == 0) "authTrue" else "authFalse"
            }
        }
        return response
    }

}