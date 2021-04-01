package com.hellow.essential_android

import java.io.Serializable

class NetworkPerson(
    var id: Int? = null,
    var name : String? = null,
    var age : Int? = null,
    var intro : String? = null
) : Serializable