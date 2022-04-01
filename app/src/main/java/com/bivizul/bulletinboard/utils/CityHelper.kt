package com.bivizul.bulletinboard.utils

import android.content.Context
import android.provider.Settings.Global.getString
import com.bivizul.bulletinboard.R
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

// выбор стран и городов
object CityHelper {
    // Получение списка стран
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
            if (countriesNames != null) {
                for (n in 0 until countriesNames.length()) {
                    tempArray.add(countriesNames.getString(n))
                }
            }
        } catch (e: IOException) {
        }

        return tempArray
    }

    // Получение списка городов
    fun getAllCities(country: String, context: Context): ArrayList<String> {
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
            // получаем название городов
            val cityNames = jsonObject.getJSONArray(country)
            // добавляем города в tempArray
            for (n in 0 until cityNames.length()) {
                tempArray.add(cityNames.getString(n))
            }

        } catch (e: IOException) {
        }

        return tempArray
    }

    // фильтр
    fun filterListData(list: ArrayList<String>, searchText: String?): ArrayList<String> {
        val tempList = ArrayList<String>()
        tempList.clear()
        if (searchText == null) {
            tempList.add("No result")
            return tempList
        }
        // фильтруем по символам
        for (selection: String in list) {
            // сравниваем с началом ввода(все маленькими буквами)
            if (selection.lowercase().startsWith(searchText.lowercase())) {
                tempList.add(selection)
            }
        }
        if (tempList.size == 0) {
            tempList.add("No result")
        }

        // вернем отфильтрованный список
        return tempList
    }
}