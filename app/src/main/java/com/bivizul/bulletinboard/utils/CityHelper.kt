package com.bivizul.bulletinboard.utils

import android.content.Context
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

// выбор стран и городов
object CityHelper {
    fun getAllCountries(context: Context): ArrayList<String> {
        var tempArray = ArrayList<String>()
        try {

            // создаем поток байтов из файла
            val inputStream: InputStream = context.assets.open("countriesToCities.json")
            // узнаем размер потока в байтах
            val size: Int = inputStream.available()
            // создаем массив считанных байтов
            val bytesArray = ByteArray(size)
            // считаем из файла inputStream и запишем в bytesArray
            inputStream.read(bytesArray)
            // преобразуем массив в String
            val jsonFiles = String(bytesArray)
            // пробразуем в json объект
            val jsonObject = JSONObject(jsonFiles)
            // получаем название стран
            val countriesNames = jsonObject.names()
            // проверяем на null и добавляем странны в tempArray
            if(countriesNames != null){
                for(n in 0 until countriesNames.length()){
                    tempArray.add(countriesNames.getString(n))
                }
            }




        } catch (e: IOException) {

        }
        return tempArray
    }
}