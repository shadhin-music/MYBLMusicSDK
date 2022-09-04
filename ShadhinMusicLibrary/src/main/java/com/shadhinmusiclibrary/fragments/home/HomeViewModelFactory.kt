package com.shadhinmusiclibrary.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shadhinmusiclibrary.data.repository.AlbumContentRepository
import com.shadhinmusiclibrary.data.repository.HomeContentRepository
import com.shadhinmusiclibrary.fragments.album.AlbumViewModel

class HomeViewModelFactory(private val homeContentRepository: HomeContentRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(homeContentRepository) as T
    }
}

class AlbumViewModelFactory(private val albumRepository: AlbumContentRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AlbumViewModel(albumRepository) as T
    }
}

class Doggy(val name:String){
    override fun toString(): String {
        return name
    }
}
fun < T> test(classType:Class<T>, string: String): T {
    return classType.getConstructor(String::class.java).newInstance(string) as T
}

fun main(){
    val doggy = test(Doggy::class.java,"Doo")
    println(doggy.toString())
}
