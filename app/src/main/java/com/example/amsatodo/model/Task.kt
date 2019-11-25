package com.example.amsatodo.model

data class Task(var title:String,var priority:Int,var status:String,val date:String){

     var id:Int = 0
     var description:String = ""
}