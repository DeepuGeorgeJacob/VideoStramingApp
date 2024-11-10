package comn.app.data.provider

import javax.inject.Inject

interface VideoStreamDataProvider {
    fun getStreamDataLink(): List<StreamLink>
}

class VideoStreamDataProviderImpl @Inject constructor() : VideoStreamDataProvider {
    override fun getStreamDataLink(): List<StreamLink> {
        return listOf(
            StreamLink.VideoLink("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"),
            StreamLink.AdLink("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4")
        )
    }
}


sealed class StreamLink(open val url: String) {
    data class VideoLink(override val url: String) : StreamLink(url)
    data class AdLink(override val url: String) : StreamLink(url)
}