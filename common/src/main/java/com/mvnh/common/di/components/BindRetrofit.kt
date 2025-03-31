package com.mvnh.common.di.components

import retrofit2.Retrofit

inline fun <reified T> Retrofit.bind(): T = create(T::class.java)
