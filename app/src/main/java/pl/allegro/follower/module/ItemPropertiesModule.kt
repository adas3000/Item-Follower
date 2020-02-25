package pl.allegro.follower.module

import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ItemPropertiesModule {

    private val titlePath="h1._1sjrk"
    private val divPath="div._9a071_1Q_68"
    private val imgPath="img._9a071_2_eNL"
    private val expiredInPath="div._9a071_Phfa8"


    @Provides
    @Named("title")
    fun provideTitlePath():String{
        return titlePath
    }

    @Provides
    @Named("div")
    fun provideItemDivPath():String{
        return divPath
    }

    @Provides
    @Named("img")
    fun provideItemImg():String{
        return imgPath
    }

    @Provides
    @Named("expiredIn")
    fun provideExpiredIn():String{
        return expiredInPath
    }

}