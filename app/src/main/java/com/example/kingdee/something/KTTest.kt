package com.example.kingdee.something

class KTTest(testParam:Int){

    var params:Int=0
    private val paramsFinal:Int

    init{
        params=testParam
        paramsFinal=testParam
    }


    fun inputIntAndReturn(input:Int):Int{

        return input
    }
}