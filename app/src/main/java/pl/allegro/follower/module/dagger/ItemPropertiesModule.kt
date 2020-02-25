package pl.allegro.follower.module.dagger

import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ItemPropertiesModule {

    private val titlePath="h1._1sjrk"
    private val divPath="div._9a071_1Q_68"
    private val imgPath="img._9a071_2_eNL"
    private val expiredInPath="div._9a071_Phfa8"


    @Provides
    @Named("title")
    @Singleton
    fun provideTitlePath():String{
        return titlePath
    }

    @Provides
    @Named("div")
    @Singleton
    fun provideItemDivPath():String{
        return divPath
    }

    @Provides
    @Named("img")
    @Singleton
    fun provideItemImg():String{
        return imgPath
    }

    @Provides
    @Named("expiredIn")
    @Singleton
    fun provideExpiredIn():String{
        return expiredInPath
    }

}